Client Request
-------
![request_structure_final2](https://github.com/twkwon0417/side/assets/91003152/a1675f1e-1945-459e-9ec5-f7511db0b412)
1. CA certified SSL 인증서를 사용하여 https 연결을 가능하게 하기 위해 Amazon Certificate Manager에서 SSL 인증서를 발급 받고, 
application load balancer를 reverse proxy로써 SSL Handshake를 수행하게 하였습니다. application load balancer을 사용하기 위해 
amazon의 DNS 서비스, Route 53을 사용하게 됬습니다.

2. web application을 구성하기 위해서 SpringBoot를 사용하였고 view를 위해 ssr을 지원하는 thymeleaf template engine, database는 
Maria db를 JDBC template을 사용하여 접속하게 하였습니다.

답변이 달리지 않은 경우 answer이 null이 되는 문제 
-------
<img width="263" alt="before_final" src="https://github.com/twkwon0417/side/assets/91003152/2ae5a144-7ffb-4e3a-917d-9f05631d1cb1">

데이터 정규화를 해서 해겷
-----
<img width="750" alt="after_final" src="https://github.com/twkwon0417/side/assets/91003152/ced68563-1877-424f-958f-8e3fc43d053f">

XSS 공격 대비
------
<img width="798" alt="escaping" src="https://github.com/twkwon0417/side/assets/91003152/750f50dd-f854-45e2-8f1c-8cf55677ee30">
<br>
- Thymeleaf의 th:text의 escaping 기능을 활용해서 HTML을 입력 받아도 HTML이 처리되지 않게 했습니다.

SQL Injectoin 대비
-----
<img width="778" alt="prepared statement" src="https://github.com/twkwon0417/side/assets/91003152/7d51003e-67c3-4223-bebe-7702ffcc9c23">
<br>
- prepared statement를 이용해서 SQL Injection에 대비했습니다. 

Hash 및 동시성 문제
------
<img width="1218" alt="hsah" src="https://github.com/twkwon0417/side/assets/91003152/37e086a7-bfeb-4081-bfe5-bc05f7e02dd2">
<br>
- Sha-256 Hash algorithm을 이용해서 보안을 한 층 강화했습니다. <br>
- 이에 따라 비밀번호를 찾는건 불가해져 비밀번호를 바꿀 수 있도록 이메일 검증 시스템을 smtp를 활용하여 구현 하였습니다.<br>
- Java Message Digest는 Thread-Safe하지 않기 때문에 Apache common codec라이브러리를 사용하였습니다. <br>
- 이메일의 인증번호를 검증할때에도 동시성 문제가 발생할수 있어 이에 따른 조치도 하였습니다. <br>




<img width="664" alt="common_codec_" src="https://github.com/twkwon0417/side/assets/91003152/bd4ec6b7-3ab9-414c-a929-dc98b075d418">



<img width="387" alt="sync" src="https://github.com/twkwon0417/side/assets/91003152/0506adfe-c036-4b5c-afe4-8f5def90ead9">

