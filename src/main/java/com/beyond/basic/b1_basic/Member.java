package com.beyond.basic.b1_basic;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data // : @Gatter + @Setter + @ToString 내장되어있음
//@Setter
//@Getter
//@ToString
@AllArgsConstructor     //모든생성자
@NoArgsConstructor      //기본생성자
public class Member {
    private String name;
    private String email;
////    MultipartFile : java/spring에서 파일처리 편의를 제공해주는 클래스
//    private MultipartFile profileImage; //바이너리데이터로 받음 (String은 문자열로담겨서 X)

}


