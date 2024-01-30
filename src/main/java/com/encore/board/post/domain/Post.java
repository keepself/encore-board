package com.encore.board.post.domain;

import com.encore.board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20, nullable = false)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String contents;

    private String appointment;
    private LocalDateTime appointmentTime;

//    author_id는 db의 컬럼명이다, 별다른 옵션없을시 author의 pk에 fk가 설정
//    post객체 입장에서는 한사람이 여러개 글을 쓸수 있으므로 N:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
//    @JoinColumn(nullable=false, name="author_email", referenceColumnName="email")
    private Author author;




    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;




//        author객체의 posts를 초기화시켜준 후
//        this.author.getPosts().add(this);


    public void updatePost(String title, String contents) {

        this.title = title;
        this.contents = contents;

    }
    public void updateAppointment(String appointment){
        this.appointment = appointment;

    }
}
