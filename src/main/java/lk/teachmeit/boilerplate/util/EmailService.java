package lk.teachmeit.boilerplate.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    @Qualifier("email")
    private JavaMailSender javaMailSender;

    @Value("${email.username}")
    private String emailFrom;

    public void sendHtmlEmailSmtp(String subject, String body, String... to){
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(to[0]);
            message.setFrom(emailFrom, emailFrom);
            message.setSubject(subject);
            message.setText(body, true);
        };
        javaMailSender.send(preparator);
    }

}
