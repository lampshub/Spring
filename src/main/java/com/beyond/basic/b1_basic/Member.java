package com.beyond.basic.b1_basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor     //모든생성자
@NoArgsConstructor      //기본생성자
public class Member {
    private String name;
    private String email;
}
