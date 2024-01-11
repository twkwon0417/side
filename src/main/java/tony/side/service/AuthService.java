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
        number = (int)(Math.random() * (90000)) + 100000; //6자리
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
