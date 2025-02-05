package hello.biblecashe;

import jakarta.annotation.PreDestroy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class BiblecasheApplication {
    private final JavaMailSender mailSender;

    public static void main(String[] args) {
        SpringApplication.run(BiblecasheApplication.class, args);
    }

    @PreDestroy
    public void onShutdown(){
        log.info("🚀 서버 종료 중... 관리자에게 이메일 전송");

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            String subject = "🔴 서버 종료 알림";
            String body = "Spring Boot 서버가 종료되었습니다.";

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo("wnss1575@icloud.com");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);

            // Email 전송
            mailSender.send(mimeMessage);
            log.info("✅ 서버 종료 이메일 전송 완료");
        } catch (MessagingException e){
            log.error("❌ 서버 종료 이메일 전송 실패", e);
        }
    }

}
