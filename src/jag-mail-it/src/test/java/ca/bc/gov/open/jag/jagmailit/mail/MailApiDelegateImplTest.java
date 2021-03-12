package ca.bc.gov.open.jag.jagmailit.mail;

import ca.bc.gov.open.jag.jagmailit.api.model.EmailRequest;
import ca.bc.gov.open.jag.jagmailit.api.model.EmailResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MailApiDelegateImplTest {

    private MailApiDelegateImpl sut;

    @BeforeAll
    public void beforeAll() {
        sut = new MailApiDelegateImpl();
    }

    @Test
    @DisplayName("202: with valid email request should return 202")
    public void withValidEmailRequestShouldReturn202() {
        ResponseEntity<EmailResponse> actual = sut.mailSend(new EmailRequest());

        assertEquals(HttpStatus.ACCEPTED, actual.getStatusCode());
        assertEquals(true, actual.getBody().getAcknowledge());
    }
}