package com.beyond.basic.b1_basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//Controller 어노테이션을 통해 스프링에 의해 객체가 생성되고, 관리되어 개발자가 직접 객체를 생성할 필요 없음
//Controller 어노테이션과 url매핑을 통해 사용자의 요청이 메서드로 분기처리
@Controller
@RequestMapping("/member")
public class MemberController {

//    get요청 리턴의 case : text, json, html(mvc)

//    case1. 서버가 사용자에게 text데이터 return
    @GetMapping("")
//    data(text, json)를 리턴할때에는 responseBody 사용. 화면(html)을 리턴할때는 responseBody 생략.
//    controller+responseBody = restController
    @ResponseBody
    public String textDataReturn(){

        return "honggildong1";
    }

//    case2. 서버가 사용자에게 json형식의 문자데이터 return
    @GetMapping("/json")
    @ResponseBody
    public Member jsonDataReturn() throws JsonProcessingException {
        Member m1 = new Member("h1", "h1@naver.com");
//        직접 json을 직렬화 할 필요없이, return타입에 객체가 있다면 자동으로 직렬화
//        ObjectMapper o1 = new ObjectMapper();
//        String data = o1.writeValueAsString(m1);  //직렬화를 하려면 기본생성자가 필요        //readValue 파싱
//        return data;  //return값 String
        return m1;
    }

//    case3. 서버가 사용자에게 html return  => mvc패턴(화면+데이터 둘다 줌)
//    case3-1) 정적인 html return
    @GetMapping("/html")
//    ResponseBody가 없고, return타입이 String인 경우 스프링은 templates폴더 밑에 simple_html.html을 찾아서 리턴.
//    타임리프 의존성이 필요
    public String htmlReturn(){
        return "simple_html";
    }

//    case3. 서버가 사용자에게 html return  => mvc패턴(화면+데이터 둘다 줌) (MVC : modetl + view + controller 애플리케이션 구조를 나누는 설계패턴)
//    case3-2) 서버에서 화면+데이터를 함께 주는 동적인 화면
//    현재 이 방식은 ssr(서버사이드렌더링)방식이고, csr방식은 화면은 별도제공(js)하고, 서버는 데이터만 제공.
//    화면렌더링 속도는 ssr이 더 빠름
    @GetMapping("/html/dynamic")
//    ResponseBody가 없고, return타입이 String인 경우 스프링은 templates폴더 밑에 simple_html.html을 찾아서 리턴.
//    타임리프 의존성이 필요
    public String dynamicHtmlReturn(Model model){
//        model객체는 데이터를 화면에 전달해주는 역할
//        name=honggildong 형태로 화면에 전달
        model.addAttribute("name","honggildong");
        model.addAttribute("email","honggildong@naver.com");

        return "dynamic_html";
    }

// 25/1/15 수업 오전10시
//    get요청의 url의 데이터 추출방식 : pathvariable, 쿼리파라미터
//    case1. pathvariable 방식을 통해 사용자로부터 url에서 데이터 추출
//    데이터의 형식 : /member/path/1
    @GetMapping("/path/{inputId}")
    @ResponseBody
    public String path(@PathVariable Long inputId){
//        별도의 형변환 없이도, 원하는 자료형으로 형변환되어 매개변수로 주입.(매개변수의 변수명이 url의 변수명과 일치해야함)
        System.out.println(inputId);
        return "OK";
    }

//    case2. parameter 방식을 통한 url에서의 데이터 추출(주로, 검색 or 정렬 요청등의 상황에서 사용)
//    case2-1) 1개의 파라미터에서 데이터 추출
//    데이터 형식 : /member/param1?name=honggildong
    @GetMapping("/param1")
    @ResponseBody
    public String param1(@RequestParam(value = "name") String inputName){    //value에 key값이 들어옴(일치), 변수명과 url명은 일치하지 않아도 됨
        System.out.println(inputName);
        return "ok";
    }

//    case2-2) 2개의 파라미터에서 데이터 추출
//    데이터 형식 : /member/param2?name=honggildong&email=hong@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public String param2(@RequestParam(value = "name") String inputName,
                         @RequestParam(value = "email") String inputEmail){
        System.out.println(inputName);
        System.out.println(inputEmail);
        return "ok";
    }

//    case2-3) 파라미터의 개수가 많아질경우, ModelAttribute를 통한 데이터바인딩
//    데이터바인딩은 param의 데이터를 모아 객체로 자동 매핑 및 생성
//    데이터 형식 : /member/param3?name=honggildong&email=hong@naver.com
    @GetMapping("/param3")
    @ResponseBody
//    ModelAttribute는 생략가능.
    public String param3(@ModelAttribute Member member){    //변수명하고 파라미터의 변수명하고 동일해야함 //Member객체를 자동으로 만들어줌으로 Setter, Getter와 NoArgsConstruct(기본생성자) 파라미터(어노테이션)가 설정되어있어야함
        System.out.println(member);
        return "ok";
    }

//    post요청의 처리 case : urlencoded, multipart-formdata, json
//    case1. body의 content-type이 urlencoded형식

//    형식 : body부에 name=honggildong&email=hong@naver.com
    @PostMapping("/url-encoded")
    @ResponseBody
//    형식이 url의 param방식와 동일하므로, RequestParam 또는 데이터바인딩(ModelAttribute) 가능
    public String urlEncoded(@ModelAttribute Member member){
        System.out.println(member);
        return "ok";
    }

//    case2. body의 content-type이 multipart-formdata형식
//    case2-1) 1개의 이미지만 있는 경우
//    형식 : body부에 name=honggildong&email=hong@naver.com&profileImage=xxxx(바이너리데이터)
    @PostMapping("/multipart-formdata")
    @ResponseBody
//    RequestParam 또는 데이터바인딩 가능
    public String multipartFormdata(@ModelAttribute Member member, @RequestParam(value = "profileImage") MultipartFile profileImage){
        System.out.println(member);
        System.out.println(profileImage.getOriginalFilename());
        return "ok";
    }

//    case2-2) 여러개의 이미지가 있는 경우
    @PostMapping("/multipart-formdata-images")
    @ResponseBody
//    RequestParam 또는 데이터바인딩 가능
    public String multipartFormdataImages(@ModelAttribute Member member, @RequestParam(value = "profileImages") List<MultipartFile> profileImage){
        System.out.println(member);
        System.out.println(profileImage.size());
        return "ok";
    }

//    case3. body의 content-type이 json ***젤 중요
//    case3-1) 일반적인 json데이터 처리
//    형식 : {"name":"honggildong","email":"hong@naver.com"}
    @PostMapping("/json")
    @ResponseBody
//    @RequestBody : json데이터를 객체로 파싱하는 어노테이션
    public String json(@RequestBody Member member){
        System.out.println(member);
        return "ok";
    }

//    case3-2) 배열형식의 json데이터 처리
//    형식 : [{"name":"honggildong","email1":"hong@naver.com"}, {"name":"honggildong","email2":"hong@naver.com"}, {"name":"honggildong","email3":"hong@naver.com"}]
    @PostMapping("/json-list")
    @ResponseBody
//    @RequestBody : json데이터를 객체로 파싱
    public String jsonList(@RequestBody List<Member> memberList){
        System.out.println(memberList);
        return "ok";
    }

//    case3-3) 중첩된 json 데이터 처리
//    데이터형식 : {"name":"honggildong", "email1":"hong@naver.com", "scorse": [{"subject":"math","point":100},{"subject":"english","point":90},{"subject":"korean","point":100}]}
    @PostMapping("/json-nested")
    @ResponseBody
    public String jsonNested(@RequestBody Student student){
        System.out.println(student);
        return "ok";
    }

//    case3-4) json + file 이 함께 있는 데이터 처리
//    데이터형식 : member={"name":"xx","email":"yy"}&profileImage=xxx(바이너리)
//    결론은 multipart-formdata구조안에 json을 넣는 방식
    @PostMapping("/json-file")
    @ResponseBody
//    json과 file을 함께 처리해야할떄는 일반적으로 RequestPart 사용
    public String jsonFile(@RequestPart("member") Member member, @RequestPart("profileImage") MultipartFile profileImage){
        System.out.println(member);
        System.out.println(profileImage.getOriginalFilename());
        return "ok";
    }
}
