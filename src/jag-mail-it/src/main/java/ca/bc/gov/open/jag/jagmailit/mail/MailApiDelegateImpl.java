package ca.bc.gov.open.jag.jagmailit.mail;

import ca.bc.gov.open.jag.jagmailit.api.MailApiDelegate;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailRequest;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MailApiDelegateImpl implements MailApiDelegate {

    @Override
    public ResponseEntity<EmailResponse> mailSend(EmailRequest emailRequest) {

        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setAcknowledge(true);

        return ResponseEntity.accepted().body(emailResponse);
    }
}
