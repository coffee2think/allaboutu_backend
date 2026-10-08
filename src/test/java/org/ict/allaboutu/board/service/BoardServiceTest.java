package org.ict.allaboutu.board.service;

import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.ict.allaboutu.member.service.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    private final String testUserId = "test01";
    private final Long testCategoryNum = 1L;

    @Test
    @DisplayName("게시글을 동시에 등록해도 게시글 번호가 중복되지 않는다.")
    void shouldAvoidDuplicateIds() throws Exception {

        // given
        int requestCount = 10;

        ExecutorService executor = Executors.newFixedThreadPool(requestCount);

        CountDownLatch ready = new CountDownLatch(requestCount);
        CountDownLatch start = new CountDownLatch(1);

        String titlePrefix = "concurrent-" + UUID.randomUUID();

        List<Future<BoardDto>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < requestCount; i++) {

                final int index = i; // effectively final이여서 final로 선언하지 않아도 됨. 구조 공부용으로 명시

                futures.add(executor.submit(() -> {

                    BoardDto request = BoardDto.builder()
                            .writer(MemberDto.builder()
                                    .userId(testUserId)
                                    .build())
                            .categoryNum(testCategoryNum)
                            .boardTitle(titlePrefix + "-" + index)
                            .boardContent("동시 등록 테스트")
                            .build();

                    ready.countDown();

                    if (!start.await(10, TimeUnit.SECONDS)) {
                        throw new IllegalStateException("시작 신호 대기 시간 초과");
                    }

                    return boardService.createBoard(request, null, null);
                }));
            }

            // 모든 스레드가 시작 준비를 마칠 때까지 대기
            assertTrue(
                    ready.await(10, TimeUnit.SECONDS),
                    "모든 작업이 제한 시간 내 준비되어야 한다."
            );

            // when
            start.countDown();

            List<Long> boardNums = new ArrayList<>();

            for (int i = 0; i < requestCount; i++) {

                BoardDto response = futures.get(i).get(30, TimeUnit.SECONDS);

                Long boardNum = response.getBoardNum();

                Board saved = boardRepository.findById(boardNum).orElseThrow();

                int index = i;

                // then
                assertAll(
                        "저장된 게시글 검증",
                        () -> assertNotNull(boardNum),
                        () -> assertEquals(boardNum, saved.getBoardNum()),
                        () -> assertEquals(titlePrefix + "-" + index, saved.getBoardTitle())
                );

                boardNums.add(boardNum);
            }

            assertAll(
                    "게시글 번호 중복 검증",
                    () -> assertEquals(requestCount, boardNums.size(), "생성된 게시글 수가 요청 수와 같아야 한다."),
                    () -> assertEquals(requestCount, new HashSet<>(boardNums).size(), "모든 게시글 번호가 서로 달라야 한다.")
            );

        } finally {
            start.countDown();

            executor.shutdownNow();

            assertTrue(
                    executor.awaitTermination(10, TimeUnit.SECONDS),
                    "ExecutorService가 제한 시간 내 종료되어야 한다."
            );
        }
    }
}