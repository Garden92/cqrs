package io.dddbyexamples.cqrs.model;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class CreditCardTest {

    @Test
    public void shouldNotBeAbleToWithdrawWhenThereIsNotEnoughMoney() {
        //given
        CreditCard creditCard = new CreditCard(TEN);

        //expect
        assertThatExceptionOfType(NotEnoughMoneyException.class)
                .isThrownBy(
                () -> creditCard.withdraw(new BigDecimal(100)));
    }

    @Test
    public void shouldBeAbleToWithdrawMoney() {
        //given
        CreditCard creditCard = new CreditCard(TEN);

        //when
        CardWithdrawn event = creditCard.withdraw(ONE);

        //expect
        assertThat(event.getAmount()).isEqualByComparingTo(ONE);
        assertThat(creditCard.availableBalance()).isEqualByComparingTo(new BigDecimal(9));
    }

}