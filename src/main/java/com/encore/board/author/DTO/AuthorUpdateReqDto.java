package com.encore.board.author.DTO;

import lombok.Data;

@Data
public class AuthorUpdateReqDto {
    private String password;
    private String name;
    private String email;


}
