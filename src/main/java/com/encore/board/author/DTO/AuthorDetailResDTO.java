package com.encore.board.author.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AuthorDetailResDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdTime;

    public AuthorDetailResDTO(Long id, String name, String email, String password, LocalDateTime createdTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
    }
}
