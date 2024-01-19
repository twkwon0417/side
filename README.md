**User Request 표** 
1. CA certified SSL 인증서를 사용하여 https 연결을 가능하게 하기 위해 Amazon Certificate Manager에서 SSL 인증서를 발급 받고, 
application load balancer를 reverse proxy로써 SSL Handshake를 수행하게 하였습니다. application load balancer을 사용하기 위해 
amazon의 DNS 서비스, Route 53을 사용하게 됬습니다.

[//]: # (> Application load balancer가 load balancer의 역할은 하지 않지만 이를 사용함으로써 웹서버의 부하를 줄일수 있게 되었고, )

2. web application을 구성하기 위해서 SpringBoot를 사용하였고 view를 위해 ssr을 지원하는 thymeleaf template engine, database는 
Maria db를 JDBC template을 사용하여 접속하게 하였습니다. 

   
<img width="265" alt="Before" src="https:/github.com/twkwon0417/side/assets/91003152/f83d663d-cd27-4b7d-97dc-5c6ce50fbdc1">
<br>
<img width="836" alt="After Normalization" src="https://github.com/twkwon0417/side/assets/91003152/678021b6-37e4-44c7-a7ee-792b5764de4f">
