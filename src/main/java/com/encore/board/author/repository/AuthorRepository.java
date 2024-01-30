package com.encore.board.author.repository;

import com.encore.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
//    findBy컬럼명의 규칙으로 자동으로 where조건문을 사용한 메서드 생성
    Optional<Author> findByEmail(String email);
}
    //AuthorRepository2는 JpaRepository를 확장한 사용자 정의 리포지토리 인터페이스로,
// Author 엔터티 및 기본 키 유형이 Long인 경우에 사용됩니다. 이 인터페이스에는 기본 CRUD 작업을 위한
// 메서드뿐만 아니라 필요한 경우 사용자 정의 쿼리 메서드를 정의할 수 있습니다.