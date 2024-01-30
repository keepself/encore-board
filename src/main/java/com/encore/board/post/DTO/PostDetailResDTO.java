package com.encore.board.post.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailResDTO {

    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdTime;

}
