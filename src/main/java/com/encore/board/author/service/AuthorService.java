package com.encore.board.author.service;

import com.encore.board.author.DTO.AuthorDetailResDTO;
import com.encore.board.author.DTO.AuthorListResDTO;
import com.encore.board.author.DTO.AuthorSaveReqDTO;
import com.encore.board.author.domain.Author;
import com.encore.board.author.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;


    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void save(AuthorSaveReqDTO authorSaveReqDTO) {
        Author author = new Author(authorSaveReqDTO.getName(),
                authorSaveReqDTO.getEmail(),
                authorSaveReqDTO.getPassword());

        authorRepository.save(author);

    }

    public List<AuthorListResDTO> findAll() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorListResDTO> authorListResDTOS = new ArrayList<>();
        for (Author author : authors) {
            AuthorListResDTO authorListResDTO = new AuthorListResDTO(author.getId(), author.getEmail(), author.getName());
            authorListResDTOS.add(authorListResDTO);

        }
        return authorListResDTOS;
    }

    public AuthorDetailResDTO findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다."));
        AuthorDetailResDTO authorDetailResDTO = new AuthorDetailResDTO(author.getId(),
                author.getName(),
                author.getEmail(),
                author.getName(),
                author.getCreatedTime());


        return authorDetailResDTO;

    }
}