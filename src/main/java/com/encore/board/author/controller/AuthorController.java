package com.encore.board.author.controller;

import com.encore.board.author.DTO.AuthorDetailResDTO;
import com.encore.board.author.DTO.AuthorListResDTO;
import com.encore.board.author.DTO.AuthorSaveReqDTO;
import com.encore.board.author.domain.Author;
import com.encore.board.author.service.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller //컨트롤러인것을 명시해준다
public class AuthorController {
    private final AuthorService authorService; // 사용할 서비스선언


    public AuthorController(AuthorService service) {
        this.authorService = service;
    }

    @GetMapping("/author/create")
    public String authorCreate() {
        return "author/author-create";
    }

    @GetMapping("/author/login-page")
    public String authorLoGIN() {
        return "author/login-page";
    }


    @PostMapping("/author/create")
    public String authorSave(AuthorSaveReqDTO authorSaveReqDTO, Model model) {

        try {
            authorService.save(authorSaveReqDTO);
            return "redirect:/author/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "author/author-create";
        }

    }

@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/author/list")
    public String authorList(Model model) {
        List<AuthorListResDTO> authorListResDTOS = authorService.findAll();
        model.addAttribute("authorList", authorListResDTOS);
        return "author/author-list";

    }


    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable(value = "id") Long inputid, Model model) {
        AuthorDetailResDTO authorListResDTO = authorService.findByIdDetail(inputid);
        model.addAttribute("author", authorListResDTO);
        return "author/author-detail";

    }

    @GetMapping("/author/delete/{id}")
    public String deleteMember(@PathVariable(value = "id") Long inputid, Model model) {
        System.out.println("start delete");
        authorService.delete(inputid);
        List<AuthorListResDTO> authorListResDTOS = authorService.findAll();
        model.addAttribute("authorList", authorListResDTOS);
        return "redirect:/author/list";
    }

    @PostMapping("/author/update/{id}")
    public String memberUpdate(@PathVariable(value = "id") Long inputid, AuthorSaveReqDTO authorSaveReqDTO, Model model) {

        authorService.memberUpdate(inputid, authorSaveReqDTO);
        AuthorDetailResDTO authorListResDTO = authorService.findByIdDetail(inputid);
        model.addAttribute("author", authorListResDTO);
        return "redirect:/author/detail/" + inputid;
    }

    @GetMapping("/author/{id}/circle/entity")
    @ResponseBody
//    연관관계가 있는 Author엔티티를 json으로 직렬화를 하게 될경우
//    순환참조 이슈 발생하므로, dto 사용필요
    public Author circleIssueTest1(@PathVariable Long id) {
        return authorService.findById(id);

    }

    @GetMapping("/author/{id}/circle/dto")
    @ResponseBody
    public AuthorDetailResDTO circleIssueTest2(@PathVariable Long id) {
        return authorService.findByIdDetail(id);
    }

}
