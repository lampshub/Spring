package com.beyond.basic.b2_board.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//logback객체를 만드는 방법 2.
@Slf4j
@RestController
public class LogController {
////        logback객체를 만드는 방법1.
    private static Logger   logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log/test")
    public String logTest(){

////      system println의 문제점
////      1)출력의 성능이 떨어짐 2)로그분류작업 불가
//        System.out.println("hello world");

////        가장 많이 사용되는 로그라이브러리 : logback
////        yml설정에 info라면 그 이하는 실행하지 않음. info랑 error만 실행
//        logger.trace("trace로그 입니다.");
//        logger.debug("debug로그 입니다.");
//        logger.info("info로그 입니다");
//        logger.error("error로그 입니다");

////        Slf4j어노테이션을 선언시, log라는 변수로 객체사용가능
//        log.debug("Slf4j 테스트");
//        log.info("Slf4j 테스트");
        try {
            log.info("에러 테스트 시작");
            throw new IllegalArgumentException("에러테스트");
        } catch (IllegalArgumentException e){
//            e.printStackTrace(); //성능문제로 logback을 쓰는게 더 좋음
            log.error("에러메세지 : ", e);
        }

        return "ok";
    }
}
