package guruspringframework.sdjpamultidb;

import guruspringframework.sdjpamultidb.domain.creditcard.CreditCard;
import guruspringframework.sdjpamultidb.services.CreditCardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SdjpaMultiDbApplicationTests {
    @Autowired
    private CreditCardService cardService;

    @AfterEach
    void tearDown() {
        cardService.purge();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testSaveAndGetCreditCard() {
        CreditCard saved = createAndSave();

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreditCardNumber()).isNotNull();
    }

    private CreditCard createAndSave() {
        CreditCard card = CreditCard.builder()
                .firstName("John")
                .lastName("Thompson")
                .zipCode("12345")
                .creditCardNumber("123456")
                .cvv("123")
                .expirationDate("12/26")
                .build();

        return cardService.saveCreditCard(card);
    }

    @Test
    void testReadCard() {
        CreditCard saved = createAndSave();

        CreditCard fetched = cardService.getCreditCardById(saved.getId());

        assertThat(fetched).isNotNull();
        assertThat(fetched).satisfies(creditCard -> {
            assertThat(creditCard.getFirstName()).isEqualTo(saved.getFirstName());
            assertThat(creditCard.getLastName()).isEqualTo(saved.getLastName());
            assertThat(creditCard.getLastName()).isEqualTo(saved.getLastName());
            assertThat(creditCard.getZipCode()).isEqualTo(saved.getZipCode());
            assertThat(creditCard.getCreditCardNumber()).isEqualTo(saved.getCreditCardNumber());
            assertThat(creditCard.getCvv()).isEqualTo(saved.getCvv());
            assertThat(creditCard.getExpirationDate()).isEqualTo(saved.getExpirationDate());
        });
    }
}
