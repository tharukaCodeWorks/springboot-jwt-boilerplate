package lk.teachmeit.auth.util;

import com.sendgrid.*;
import lk.teachmeit.auth.enums.MailMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {
    SendGrid sg = new SendGrid("SENDGRID_KEY");
    Request request = new Request();
    @Autowired
    @Qualifier("email")
    private JavaMailSender javaMailSender;

    public void sendHtml(String subject, String body, MailMode mailMode, String... to) throws  IOException {
        switch (mailMode){
            case SMPT:
                sendHtmlEmailSmtp(subject, body, mailMode, to);
                break;
            case SENDGRID:
                sendHtmlEmailSendgrid(subject, body, mailMode, to);
                break;
            default:
        }

    }

    private void sendHtmlEmailSendgrid(String subject, String body, MailMode mailMode, String... to) throws IOException{
        Mail mail = new Mail(new Email("info@mexxar.com"), subject, new Email(to[0]), new Content("text/html", body));
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (Exception ex) {
            throw  ex;
        }
    }

    private void sendHtmlEmailSmtp(String subject, String body, MailMode mailMode, String... to){
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(to[0]);
            message.setFrom("info@mexxar.com", "Mexxar Sample");
            message.setSubject(subject);
//            if (StringUtils.isNotBlank(ccAddresses))
//                message.setCc(ccAddresses.split("[;,]"));
//            if (StringUtils.isNotBlank(bccAddresses))
//                message.setBcc(bccAddresses.split("[;,]"));
            message.setText(body, true);
        };
        javaMailSender.send(preparator);
    }

    public void sendEmail(String subject, String body, MailMode mailMode, String... to) throws  IOException {
        Mail mail = new Mail(new Email("info@mexxar.com"), subject, new Email(to[0]), new Content("text/text", body));
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (Exception ex) {
            throw  ex;
        }

    }
}
