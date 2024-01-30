package com.encore.board.author.service;

import com.encore.board.author.DTO.AuthorListResDTO;
import com.encore.board.author.DTO.AuthorSaveReqDTO;
import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import com.encore.board.author.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService2 {
    private final AuthorRepository authorRepository;


    public AuthorService2(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void save(AuthorSaveReqDTO authorSaveReqDTO) {
        Role role = null;
        if (authorSaveReqDTO.getRole() == null || authorSaveReqDTO.getRole().equals("user")) {
            role = Role.USER;
        } else {
            role = Role.ADMIN;
        }
    }
    public List<AuthorListResDTO> findAll(){
        List<Author> authors = authorRepository.findAll();
        List<AuthorListResDTO> authorListResDTOS = new ArrayList<>();
        for (Author author : authors) {
            AuthorListResDTO authorListResDTO =  new AuthorListResDTO(author.getId(),author.getName(),author.getEmail());
            authorListResDTOS.add(authorListResDTO);
        }
        return authorListResDTOS;
    }
}