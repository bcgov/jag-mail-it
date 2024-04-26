package ca.bc.gov.open.jag.jagmailit.mail;

import ca.bc.gov.open.jag.jagmailit.api.MailApi;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailObject;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailRequest;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailResponse;
import ca.bc.gov.open.jag.jagmailit.mail.mappers.SimpleMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MailApiDelegateImpl implements MailApi {

    private final JavaMailSender emailSender;

    private final SimpleMessageMapper simpleMessageMapper;

    Logger logger = LoggerFactory.getLogger(MailApiDelegateImpl.class);

    public MailApiDelegateImpl(JavaMailSender emailSender, SimpleMessageMapper simpleMessageMapper) {
        this.emailSender = emailSender;
        this.simpleMessageMapper = simpleMessageMapper;
    }

    @Override
    public ResponseEntity<EmailResponse> mailSend(EmailRequest emailRequest) {
        logger.info("Beginning mail send");
        Optional<EmailObject> emailObject = emailRequest.getTo().stream().findFirst();

        if (!emailObject.isPresent()) {
            logger.error("No value present in email object");
            return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
        }

        logger.info("Mapping message");
        SimpleMailMessage simpleMailMessage = simpleMessageMapper.toSimpleMailMessage(emailRequest);

        logger.info("Sending message");
        emailSender.send(simpleMailMessage);

        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setAcknowledge(true);

        logger.info("Message sent successfully");
        return ResponseEntity.accepted().body(emailResponse);

    }
}
