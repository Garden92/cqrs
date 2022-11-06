package io.dddbyexamples.cqrs.servcie;

import io.dddbyexamples.cqrs.model.CreditCard;
import io.dddbyexamples.cqrs.repository.CreditCardRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WithdrawalService {

    private final CreditCardRepository creditCardRepository;
    private final JdbcTemplate jdbcTemplate;

    WithdrawalService(CreditCardRepository creditCardRepository, JdbcTemplate jdbcTemplate) {
        this.creditCardRepository = creditCardRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void withdraw(UUID cardId, BigDecimal amount) {
        CreditCard creditCard = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalStateException("Cannot find card with id " + cardId));
        creditCard.withdraw(amount);
        jdbcTemplate.update("INSERT INTO WITHDRAWAL(ID, CARD_ID, AMOUNT) VALUES (?,?,?)", UUID.randomUUID(), cardId, amount);
    }
}
