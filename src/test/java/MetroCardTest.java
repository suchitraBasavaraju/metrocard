import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MetroCardTest {

    @Test
    void shouldReturnBalance() {
        MetroCard metroCard = new MetroCard("MC1");

        double balance = metroCard.balance();

        assertEquals(0, balance);
    }

    @Test
    void shouldCreditMoneyToCard() throws Exception {
        MetroCard metroCard = new MetroCard("MC1");

        metroCard.credit(50);

        assertEquals(50, metroCard.balance());
    }

    @Test
    void shouldNotCreditNegativeMoneyToCard() {
        MetroCard metroCard = new MetroCard("MC1");

        assertThrows(InvalidAmountException.class, () -> metroCard.credit(-1));
    }

    @Test
    void shouldDebitMoneyFromCard() throws Exception {
        MetroCard metroCard = new MetroCard("MC1");
        metroCard.credit(100);

        metroCard.debit(50);

        assertEquals(50, metroCard.balance());
    }

    @Test
    void shouldNotNegativeMoneyFromCard() throws Exception {
        MetroCard metroCard = new MetroCard("MC1");
        metroCard.credit(100);

        assertThrows(InvalidAmountException.class, () -> metroCard.debit(-1));
    }

    @Test
    void shouldThrowExceptionWhenDebitedMoreThanBalance() throws Exception {
        MetroCard metroCard = new MetroCard("MC1");
        metroCard.credit(50);

        assertThrows(InsufficientBalanceException.class, () -> metroCard.debit(51));
    }


    @Test
    void shouldBeTrueWhenMetroCardAreSame() {
        MetroCard mc1 = new MetroCard("MC1");

        assertEquals(mc1,mc1);
    }

    @Test
    void shouldBeTrueWhenMetroCardNumbersAreSame() {
        MetroCard mc1 = new MetroCard("MC1");
        MetroCard anothermc1 = new MetroCard("MC1");

        assertEquals(mc1,anothermc1);
    }

    @Test
    void shouldBeFalseWhenMetroCardNumbersAreDifferent() {
        MetroCard mc1 = new MetroCard("MC1");
        MetroCard mc2 = new MetroCard("MC2");

        assertNotEquals(mc1,mc2);
    }
}
