package org.ict.allaboutu.board.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_sequence_generator")
    @SequenceGenerator(name = "board_sequence_generator", sequenceName = "BOARD_SEQ", allocationSize = 1)
    @Column(name = "BOARD_NUM")
    private Long boardNum;

    @Column(name = "USER_NUM")
    private Long userNum;

    @Column(name = "CATEGORY_NUM")
    private Long categoryNum;

    @Column(name = "BOARD_TITLE")
    private String boardTitle;

    @Column(name = "BOARD_CONTENT")
    private String boardContent;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    @Column(name = "DELETE_DATE")
    private LocalDateTime deleteDate;

    @Column(name = "READ_COUNT")
    private Long readCount;

}
