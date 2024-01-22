package com.encore.board.author.controller;

import com.encore.board.author.DTO.AuthorDetailResDTO;
import com.encore.board.author.DTO.AuthorListResDTO;
import com.encore.board.author.DTO.AuthorSaveReqDTO;
import com.encore.board.author.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;


    public AuthorController(AuthorService service) {
        this.authorService = service;
    }

    @PostMapping("/author/save")
    @ResponseBody
    public String authorSave(AuthorSaveReqDTO authorSaveReqDTO) {

        authorService.save(authorSaveReqDTO);

        return "OK";

    }



    @GetMapping("/author/list")
    public String authorList(Model model) {
        List<AuthorListResDTO> authorListResDTOS = authorService.findAll();
        model.addAttribute("authorList",authorListResDTOS);
        return "author/author-list";

    }


    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable(value = "id") Long inputid, Model model){
        AuthorDetailResDTO authorListResDTO = authorService.findById(inputid);
        model.addAttribute("author",authorListResDTO);
        return "author/author-detail";

    }
}
