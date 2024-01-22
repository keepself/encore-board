package com.encore.board.author.DTO;

import lombok.Data;

@Data
public class AuthorListResDTO {
    private Long id;
    private String name;
    private String email;

    public AuthorListResDTO(Long id, String email, String name) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
