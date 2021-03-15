package ca.bc.gov.open.jag.jagmailit.mail;

import ca.bc.gov.open.jag.jagmailit.api.MailApiDelegate;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailObject;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailRequest;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailResponse;
import ca.bc.gov.open.jag.jagmailit.mail.mappers.SimpleMessageMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MailApiDelegateImpl implements MailApiDelegate {

    private final JavaMailSender emailSender;

    private final SimpleMessageMapper simpleMessageMapper;

    public MailApiDelegateImpl(JavaMailSender emailSender, SimpleMessageMapper simpleMessageMapper) {
        this.emailSender = emailSender;
        this.simpleMessageMapper = simpleMessageMapper;
    }

    @Override
    public ResponseEntity<EmailResponse> mailSend(EmailRequest emailRequest) {

        Optional<EmailObject> emailObject = emailRequest.getTo().stream().findFirst();

        if (!emailObject.isPresent()) {
            return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
        }

        SimpleMailMessage simpleMailMessage = simpleMessageMapper.toSimpleMailMessage(emailRequest);

        emailSender.send(simpleMailMessage);

        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setAcknowledge(true);

        return ResponseEntity.accepted().body(emailResponse);

    }
}
