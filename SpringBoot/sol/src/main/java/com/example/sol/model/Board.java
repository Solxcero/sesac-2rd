package com.example.sol.model;

import lombok.Data;

@Data
public class Board {
    private int boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    // private String boardDate;
    // private int boardReview; //조회수 기능
    
}
