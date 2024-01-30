package com.encore.board.post.controller;


import com.encore.board.post.DTO.PostDetailResDTO;
import com.encore.board.post.DTO.PostListResDTO;
import com.encore.board.post.DTO.PostSaveReqDTO;
import com.encore.board.post.DTO.PostUpdateReqDTO;
import com.encore.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/create")
    public String postCreate() {
        return "post/post-create";
    }

    @PostMapping("/post/create")
    public String postSave(Model model, PostSaveReqDTO postSaveReqDto, HttpSession httpSession) {

        try {
//            HttpServletRequest reqreq를 매개변수에 주입한뒤에
//            HttpSession session = req.getSession(); 세션값을 꺼내어 getAttribute("email)
            postService.save(postSaveReqDto, httpSession.getAttribute("email").toString());
            return "redirect:/post/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "post/post-create";
        }

//        return "ok";
    }

    @PostMapping("/post/{id}/update")
    public String postUpdate(@PathVariable Long id, PostUpdateReqDTO postUpdateReqDto) {
        postService.update(id, postUpdateReqDto);
        return "redirect:/post/detail/" + id;

    }

    @GetMapping("/post/list")
//    localhost:8080/post/list?size=xx&page=xx&sort=xx.desc
    public String postList(Model model, @PageableDefault(size = 5, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostListResDTO> postListResDtos = postService.findByappointment(pageable);
        model.addAttribute("postList", postListResDtos);
        return "post/post-list";
    }

    //    @GetMapping("/post/list/pageJson")
//    @ResponseBody
//    //localhost:8080/post/list/pageJson?size=10&sort=createdTime&page=1
//    public  Page<PostListResDTO> postList(Pageable pageable){
//        Page<PostListResDTO> postListResDtos = postService.findAllJson(pageable);
//        return postListResDtos;
//    }
    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        PostDetailResDTO postDetailResDto = postService.findByIdDetail(id);
        model.addAttribute("post", postDetailResDto);
        return "post/post-detail";
//        return postDetailResDto;
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/post/list";
//        return "ok";
    }
}