package com.beyond.basic.b2_board.common.service;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//Aspect : aop코드임을 명시
@Aspect
@Component
@Slf4j
public class AopLogService {

//    AOP의 대상이 되는 controller, service 등을 어노테이션 기준으로 명시
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")  // RestController가 붙어있는 모든 곳 앞에 위치
    public void controllerPointCut() {}
//    AOP의 대상이 되는 controller, service 등을 패키지구조 기준으로 명시
//    @Pointcut("within(com.beyond.basic.b2_board.author.controller.AuthorController)")
//    public void controllerPointCut() {}

//    공통성 코드
////    aop활용방법1 : around를 통해 joinpoint(before, joinpoint, after) 에 걸쳐져있는 코드 작성
//    @Around("controllerPointCut()")
////    joinpoint는 사용자가 실행하고자 하는 코드를 의미하고, 위에서 정의한 pointcut을 의미
//    public Object controllerLogger(ProceedingJoinPoint joinPoint){
//
////        joinpoint 이전
//        log.info("aop start");
//        log.info("요청자 : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());  //filter계층 거쳐서 접근 가능
//        log.info("요청 메서드명 : " + joinPoint.getSignature().getName());
//
////        servlet객체에서 http요청을 꺼내는 법
//        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        log.info("http url : "+ request.getRequestURI());   //author/list
//        log.info("http 메서드 : "+ request.getMethod());   // Get
//        log.info("http 헤더 - 토큰 : "+ request.getHeader("Authorization") );      //토큰값
//        log.info("http 헤더 - contentType : "+ request.getHeader("Content-Type") );   //get시 null, post 면 타입이 나옴
//
////        joinpoint 실행
//        Object object = null;
//        try {
//            object = joinPoint.proceed();
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
//
////        joinpoint 이후
//        log.info("aop end");
//
//        return object;

////    aop활용방법2 : before, after 어노테이션 사용
//        @Before("controllerPointCut()")
//        public void beforeController(JoinPoint joinPoint) { //before쓸때는 JoinPoint
//            log.info("aop start");
//            log.info("요청자 : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//            log.info("요청 메서드명 : " + joinPoint.getSignature().getName());
//        }
//
//        @After("controllerPointCut()")
//        public void afterController() {
//            log.info("aop end");
//        }

}
