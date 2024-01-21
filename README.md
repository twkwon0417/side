
시연영상
=======
[![Video Label](http://img.youtube.com/vi/glTZ7Ewnavc/0.jpg)](https://youtu.be/glTZ7Ewnavc=0s)

Client Request
======
![request_structure_final2](https://github.com/twkwon0417/side/assets/91003152/a1675f1e-1945-459e-9ec5-f7511db0b412)
1. CA certified SSL 인증서를 사용하여 https 연결을 가능하게 하기 위해 Amazon Certificate Manager에서 SSL 인증서를 발급 받고, 
application load balancer를 reverse proxy로써 SSL Handshake를 수행하게 하였습니다. application load balancer을 사용하기 위해 
amazon의 DNS 서비스, Route 53을 사용하게 됬습니다.

2. web application을 구성하기 위해서 SpringBoot를 사용하였고 view를 위해 ssr을 지원하는 thymeleaf template engine, database는 
Maria db를 JDBC template을 사용하여 접속하게 하였습니다.

답변이 달리지 않은 경우 answer이 null이 되는 문제 
=========
<img width="263" alt="before_final" src="https://github.com/twkwon0417/side/assets/91003152/2ae5a144-7ffb-4e3a-917d-9f05631d1cb1">

데이터 정규화를 해서 해겷
-----
<img width="750" alt="after_final" src="https://github.com/twkwon0417/side/assets/91003152/ced68563-1877-424f-958f-8e3fc43d053f">

XSS 공격 대비
=========

- Thymeleaf의 th:text의 escaping 기능을 활용해서 HTML을 입력 받아도 HTML이 처리되지 않게 했습니다.

~~~html
// showingpage.html
<div class="questions-container">
  <a class="ask-question-button" th:href="@{'/post/' + ${userId} + '/ask'}">질문하기</a>
  <div class="question" th:each="post : ${posts}">
    <div class="question-title" th:text="${post.title}">Title 1</div>
    <div class="question-body" th:text="${post.getQuestion}">This is the body of question 1.</div>
    <div class="additional-info" th:text="${post.getAnswer}">Additional info for question 1.</div>
  </div>
</div>
~~~

SQL Injectoin 대비
=========

- prepared statement를 이용해서 SQL Injection에 대비했습니다. 

~~~java:MariaMemberRepository.java
    // MariaMemberRepository.java
    public Member save(Member member) {
        String sql = "insert into portfolio.member(login_id, password, name, e_mail, phone_number)"
                + "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getLoginId());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getEMail());
            ps.setString(5, member.getPhoneNumber());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        member.setId(key);
        return member;
    }
~~~

Sha-256 암호화
===========
- Sha-256 Hash algorithm을 이용해서 보안을 한 층 강화했습니다. <br>

~~~java:MemberService.java
    // MemberService.java
    public void join(Member member) {
        String hashed = new DigestUtils(SHA_256).digestAsHex(member.getPassword());
        member.setPassword(hashed);
        memberRepository.save(member);
    }
~~~
<img width="1218" alt="hsah" src="https://github.com/twkwon0417/side/assets/91003152/37e086a7-bfeb-4081-bfe5-bc05f7e02dd2">
<br>

이에 따라 비밀번호를 찾는건 불가해져 비밀번호를 바꿀 수 있도록 이메일 검증 시스템을 smtp를 활용하여 구현 하였습니다.
------

~~~java:AuthService.java
  // AuthService.java
  package tony.side.service;
  
  import jakarta.mail.MessagingException;
  import jakarta.mail.internet.MimeMessage;
  import lombok.RequiredArgsConstructor;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.mail.javamail.JavaMailSender;
  import org.springframework.stereotype.Service;
  
  @Service
  @RequiredArgsConstructor
  @Slf4j
  public class AuthService {
      private final JavaMailSender javaMailSender;
      private static final String senderEmail = "twkwon0417@gmail.com";
      private static int number;
  
      private static void createNumber() {
          number = (int)(Math.random() * (90000)) + 100000;
      }
  
      private MimeMessage CreateMail(String mail){
          createNumber();
          MimeMessage message = javaMailSender.createMimeMessage();
  
          try {
              message.setFrom(senderEmail);
              message.setRecipients(MimeMessage.RecipientType.TO, mail);
              message.setSubject("이메일 인증");
              String body = "";
              body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
              body += "<h1>" + number + "</h1>";
              body += "<h3>" + "감사합니다." + "</h3>";
              message.setText(body,"UTF-8", "html");
          } catch (MessagingException e) {
              log.error("email send error ={}", e.toString());
          }
  
          return message;
      }
  
      public synchronized int sendMail(String mail) {
  
          MimeMessage message = CreateMail(mail);
  
          javaMailSender.send(message);
          log.info("{}", number);
          return number;
      }
  }

~~~

동시성 문제
=========
- Java Message Digest는 Thread-Safe하지 않기 때문에 Apache common codec라이브러리를 사용하였습니다. <br>
- 이메일의 인증번호를 검증할때에도 동시성 문제가 발생할수 있어 이에 따른 조치도 하였습니다. <br>

~~~
implementation group: 'commons-codec', name: 'commons-codec', version: '1.16.0'
~~~


~~~ java:AuthService.java
    // AuthService.java
    public synchronized int sendMail(String mail) {

        MimeMessage message = CreateMail(mail);

        javaMailSender.send(message);
        log.info("{}", number);
        return number;
    }
~~~

PRG Pattern
=======

- 새로고침시 요청을 계속 보내는 문제를 해결하기 위해 사용했습니다.

~~~java:PostController.java
    // PostController.java
    @PostMapping("/{userId}/ask")
    public String addPost(@Validated QuestionPostDto questionPostDto, BindingResult bindingResult, @PathVariable Long userId) {
        if (bindingResult.hasErrors()) {
            return "questions/questionask";
        }
        Question question = questionPostDto.toQuestion(userId);
        postService.write(question);
        return "redirect:/post/{userId}";
    }
~~~


