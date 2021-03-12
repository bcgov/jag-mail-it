package ca.bc.gov.open.jag.jagmailit.mail;

import ca.bc.gov.open.jag.jagmailit.api.MailApiDelegate;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailObject;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailRequest;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MailApiDelegateImpl implements MailApiDelegate {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public ResponseEntity<EmailResponse> mailSend(EmailRequest emailRequest) {

        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setAcknowledge(true);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailRequest.getFrom().getEmail());
        Optional<EmailObject> emailObject = emailRequest.getTo().stream().findFirst();

        if (!emailObject.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, null);
        }

        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getContent().getValue());
        message.setTo(emailObject.get().getEmail());

        emailSender.send(message);

        return ResponseEntity.accepted().body(emailResponse);
    }
}
