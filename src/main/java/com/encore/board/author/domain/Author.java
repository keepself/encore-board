package com.encore.board.author.domain;

import com.encore.board.author.DTO.AuthorSaveReqDTO;
import com.encore.board.post.domain.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
//위와같이 모든 매개변수가 있는 생성자를 생성하는 어노테이션과 Builder를 클래스에 붙여
//모든 매개변수가 있는 생성자 위에 Builder어노테이션을 붙인것과 같은 효과가 있음.
@NoArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본키 Primary 지정
    @Column(length = 20, nullable = false)
    //테이블에서 길이가 20인 문자열 컬럼이며, null 값이 허용되지 않는(not nullable) 컬럼입니다.
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 20, unique = true, nullable = false)
    //unique 고유값 중복이 허용되지않는다
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

//    직렬화 : json생성할때
//    직렬화할떄 순환참조->무한루프 되는문제가생김 ->Dto 이용해서 해결

//    Author객체를 json으로 직렬화
//    {
//         "id" : 1
//          "name" : awf
//    author를 조회할때 post객체가 필요할시에 선언
//    mapedBy에 연관관계의 주인을 명시하고 , fk를 관리하는  변수명을 명시
//    1:1 관계일경우 @OneToOne도 존재
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @Setter//test cascade
    private List<Post> posts;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;


    public Author(Long id, String name, String email, String password, Author author) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public Author updateMember(Author author, AuthorSaveReqDTO authorSaveReqDTO) {

        author.email = authorSaveReqDTO.getEmail();
        author.name = authorSaveReqDTO.getName();
        author.password = authorSaveReqDTO.getPassword();
        author.role = authorSaveReqDTO.getRole() == "관리자" ? Role.ADMIN : Role.USER;
        return author;
    }

public void updateAuthor(String name, String password){

        this.name = name;
        this.password = password;

}
    public void setPost(List<Post> posts) {
        this.posts = posts;
    }
}