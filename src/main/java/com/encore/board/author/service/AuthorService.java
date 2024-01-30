package com.encore.board.author.service;

import com.encore.board.author.DTO.AuthorDetailResDTO;
import com.encore.board.author.DTO.AuthorListResDTO;
import com.encore.board.author.DTO.AuthorSaveReqDTO;
import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(AuthorSaveReqDTO authorSaveReqDTO) throws IllegalArgumentException {
        Optional<Author> author1 = authorRepository.findByEmail(authorSaveReqDTO.getEmail());
        if (author1.isPresent()) throw new IllegalArgumentException("중복이메일");
        Role role = null;
        if (authorSaveReqDTO.getRole() == null || authorSaveReqDTO.getRole().equals("user")) {
            role = Role.USER;
        } else {
            role = Role.ADMIN;
        }

//생성자방식은 순서가 얽히면 구동이안된다.
//하지만 빌더패턴은 순서상관이 없다.
//
//        빌더패턴
        Author author = Author.builder()
                .name(authorSaveReqDTO.getName())
                .email(authorSaveReqDTO.getEmail())
                .role(role)
                .password(passwordEncoder.encode(authorSaveReqDTO.getPassword()))
                .build();
        //        cascade.persist 테스트
//        부모테이블을 통해 자식테이블에 객체를 동시에 생성
        List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .title("안녕하세요. " + author.getName() + "입니다.")
                .contents("반갑습니다. Cascade테스트중 입니다.")
                .author(author)
                .build();
        posts.add(post);
        author.setPost(posts);
        authorRepository.save(author);

//        Author author = Author.builder()
//                .email(authorSaveReqDTO.getEmail())
//                .name(authorSaveReqDTO.getName())
//                .password(authorSaveReqDTO.getPassword())
//                .build();


    }

    public List<AuthorListResDTO> findAll() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorListResDTO> authorListResDTOS = new ArrayList<>();
        for (Author author : authors) {
            AuthorListResDTO authorListResDTO = new AuthorListResDTO(author.getId(), author.getName(), author.getEmail());
            authorListResDTOS.add(authorListResDTO);
        }
        return authorListResDTOS;
    }

    public Author findById(Long id) throws EntityNotFoundException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다."));
        return author;

    }

    public AuthorDetailResDTO findByIdDetail(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다."));

        String role = null;
        if (author.getRole() == null || author.getRole().equals("user")) {
            role = "일반유저";
        } else {
            role = "관리자";
        }


        AuthorDetailResDTO authorDetailResDTO = new AuthorDetailResDTO(author.getId(),
                author.getName(),
                author.getEmail(),
                author.getName(),
                author.getCreatedTime(),
                role,
                author.getPosts().size()
        );


        return authorDetailResDTO;

    }

    public void delete(Long id) {
        System.out.println("delete id " + id);
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        System.out.println("author " + author.getName());
        authorRepository.delete(author);

    }

    public void memberUpdate(Long id, AuthorSaveReqDTO authorSaveReqDTO) {
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        author.updateMember(author, authorSaveReqDTO);
        authorRepository.save(author);
//        명시적으로 save를 하지 않더라도, jpa의 영속성컨텍스트를 통해
//        객체에 변경이 감지(dirtychecking)되면, 트랜잭션이 완료되는시점에 save동작.
//        authorRepository.save(author
    }
}
