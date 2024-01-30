package com.encore.board.author.repository;

//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

//DATAJpaTest 어노테이션을 사용하면 매 테스트가 종료되면 자동으로 db원상복구
//모든 스프링빈을 생성하지 않고, DB테스트 특화 어노테이션
//@DataJpaTest
//SpringBootTest어노테이션은 롤백기능은 지원하지 않고, 별도로 롤백 코드 또는 어노테이션필요
//SpringBootTest어노테이션은 실제 스프링 실행과 동일하게 스프링생성 및 주입
//@SpringBootTest
@DataJpaTest
//replace = AutoConfigureTestDatabase.Replace.ANY : H2DB(spring 내장 인메모리)가 기본설정
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional//자동롤백
@ActiveProfiles("test")//application-test-yml 파일을 찾아 설정값 세팅
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void  authorSaveTest(){
//        객체를 만들고 save하고 -> 재조회해서 ->만든 객체와 비교
        Author author = Author.builder()
                .email("C@3lfk5")
                .name("testman")
                .password("34334")
                .role(Role.ADMIN)
                .build();
//        실행(excute, when)
        authorRepository.save(author);
        Author authorDb = authorRepository.findByEmail("C@3lfk5").orElse(null);

//        검증(then)
//        Assertions클래스의 기능을 통해 오류의 원인파악. null
        Assertions.assertEquals(author.getEmail(), authorDb.getEmail());

    }
}
