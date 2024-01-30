package com.encore.board.post.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.DTO.PostDetailResDTO;
import com.encore.board.post.DTO.PostListResDTO;
import com.encore.board.post.DTO.PostSaveReqDTO;
import com.encore.board.post.DTO.PostUpdateReqDTO;
import com.encore.board.post.domain.Post;
import com.encore.board.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;


    public PostService(PostRepository postRepository,AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostSaveReqDTO postSaveReqDTO, String email) throws IllegalArgumentException{
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
        Author author = authorRepository.findByEmail(email).orElse(null);
        LocalDateTime localDateTime =null;
        String appointment =null;
        if (postSaveReqDTO.getAppointment().equals("Y")
                && !postSaveReqDTO.getAppointmentTime().isEmpty()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            localDateTime  = LocalDateTime.parse(postSaveReqDTO.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if (localDateTime.isBefore(now)) {
                throw new IllegalArgumentException("시간정보 잘못입력");
            }
            appointment = "Y";
        }

            Post post = Post.builder()
                    .title(postSaveReqDTO.getTitle())
                    .contents(postSaveReqDTO.getContents())
                    .author(author)
                    .appointment(appointment)
                    .appointmentTime(localDateTime)
                    .build();
//          더티체킹 테스트
//            author.updateAuthor("dirtycheking", "124");
            postRepository.save(post);
        }


    public void update(Long id, PostUpdateReqDTO postUpdateReqDTO) {

        Post post = postRepository.findById(id).orElseThrow((() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다.")));
        post.updatePost(postUpdateReqDTO.getTitle(), postUpdateReqDTO.getContents());

        postRepository.save(post);

    }

    public List<PostListResDTO> findAll(Pageable pageable) {
        List<Post> posts = postRepository.findAlljoin();
        List<PostListResDTO> postListResDTOS = new ArrayList<>();
        for (Post post : posts) {
            PostListResDTO postListResDTO = new PostListResDTO();
            postListResDTO.setId(post.getId());
            postListResDTO.setTitle(post.getTitle());
            postListResDTO.setEmail(post.getAuthor()==null?"익명유저":post.getAuthor().getEmail());
            postListResDTOS.add(postListResDTO);
        }
        return postListResDTOS;


    }

    public Page<PostListResDTO> findByappointment(Pageable pageable) {
        Page<Post> posts = postRepository.findByAppointment(null,pageable);
        Page<PostListResDTO> postListResDTOS
                = posts.map(p-> new PostListResDTO(p.getId(),p.getTitle(),p.getContents(),p.getAuthor()==null ? "익명객체" : p.getAuthor().getEmail()));
        return postListResDTOS;


    }
    public Page<PostListResDTO> findAllpaging(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostListResDTO> postListResDTOS
                = posts.map(p-> new PostListResDTO(p.getId(),p.getTitle(),p.getContents(),p.getAuthor()==null ? "익명객체" : p.getAuthor().getEmail()));
        return postListResDTOS;


    }

    public PostDetailResDTO findByIdDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다."));

        PostDetailResDTO postDetailResDTO = new PostDetailResDTO();
        postDetailResDTO.setId(post.getId());
        postDetailResDTO.setTitle(post.getTitle());
        postDetailResDTO.setContents(post.getContents());
        postDetailResDTO.setCreatedTime(post.getCreatedTime());


        return postDetailResDTO;

    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        postRepository.delete(post);

    }

    public void memberUpdate(Long id, PostSaveReqDTO postSaveReqDTO) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        post.updatePost(post.getTitle(), post.getContents());
        postRepository.save(post);
    }
}
