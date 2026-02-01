package com.beyond.basic.b2_board.common.init;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.domain.Role;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//CommandLineRunner를 구현함으로서 아래 run메서드가 스프링빈으로 등록되는 시점에 자동실행    //서버 키자마자 admin 계정 생성됨
@Component
@Transactional
public class InitialDataLoad implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialDataLoad(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(authorRepository.findByEmail("admin@naver.com").isPresent()){
            return;
        }
        authorRepository.save(Author.builder()
                        .name("admin").email("admin@naver.com").role(Role.ADMIN).password(passwordEncoder.encode("12341234"))
                        .build());
    }
}

//403인가에러를 처리하기위해서 ExceptionHandler(AuthorizationDeniedException) 처리.
//admin계정을 서버가 켜질떄부터 등록되도록. initialDataLoad에 등록해둠 (사실 중요한 비밀번호는 yml에 넣어두고 사용하는게 좋음)