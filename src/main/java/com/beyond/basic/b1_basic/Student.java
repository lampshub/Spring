package com.beyond.basic.b1_basic;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private String email;
    private List<Score> scores;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
//    내부에 static 반드시 필요
//    객체마다 class가 붙는게 말이 안된다
//    만약에 static을 빼고싶다면 Student밖에 선언해야한다
    public static class Score {        //Student안에 종속
        private String subject;
        private int point;
    }
}

