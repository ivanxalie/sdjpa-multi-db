package guruspringframework.sdjpamultidb.services;

import guruspringframework.sdjpamultidb.domain.cardholder.CreditCardHolder;
import guruspringframework.sdjpamultidb.domain.creditcard.CreditCard;
import guruspringframework.sdjpamultidb.domain.pan.CreditCardPAN;
import guruspringframework.sdjpamultidb.repositories.cardholder.CreditCardHolderRepository;
import guruspringframework.sdjpamultidb.repositories.creditcard.CreditCardRepository;
import guruspringframework.sdjpamultidb.repositories.pan.CreditCardPANRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CreditCardHolderRepository creditCardHolderRepository;
    private final CreditCardPANRepository creditCardPANRepository;

    @Override
    @Transactional(readOnly = true)
    public CreditCard getCreditCardById(Long id) {
        CreditCard card = creditCardRepository.findById(id).orElseThrow();
        fillHolder(card);
        fillCardNumber(card);
        return card;
    }

    private void fillHolder(CreditCard card) {
        creditCardHolderRepository.findByCreditCardId(card.getId()).ifPresent(holder -> {
            card.setFirstName(holder.getFirstName());
            card.setLastName(holder.getLastName());
            card.setZipCode(holder.getZipCode());
        });
    }

    private void fillCardNumber(CreditCard card) {
        creditCardPANRepository.findByCreditCardId(card.getId()).ifPresent(pan ->
                card.setCreditCardNumber(pan.getCreditCardNumber()));
    }

    @Override
    @Transactional
    public CreditCard saveCreditCard(CreditCard card) {
        CreditCard saved = creditCardRepository.save(card);
        if (containsAtLeastOneElement(card.getFirstName(), card.getLastName(), card.getZipCode()))
            saveCreditCardHolder(saved);
        if (containsAtLeastOneElement(card.getCreditCardNumber()))
            saveCreditCardPan(card);
        return saved;
    }

    private boolean containsAtLeastOneElement(Object... objects) {
        return Arrays.stream(objects).anyMatch(Objects::nonNull);
    }

    private void saveCreditCardHolder(CreditCard card) {
        creditCardHolderRepository.save(CreditCardHolder.builder()
                .creditCardId(card.getId())
                .firstName(card.getFirstName())
                .lastName(card.getLastName())
                .zipCode(card.getZipCode())
                .build());
    }

    private void saveCreditCardPan(CreditCard card) {
        creditCardPANRepository.save(CreditCardPAN.builder()
                .creditCardId(card.getId())
                .creditCardNumber(card.getCreditCardNumber())
                .build());
    }

    public void purge() {
        creditCardRepository.deleteAll();
        creditCardRepository.flush();

        creditCardHolderRepository.deleteAll();
        creditCardHolderRepository.flush();

        creditCardPANRepository.deleteAll();
        creditCardPANRepository.flush();
    }
}
