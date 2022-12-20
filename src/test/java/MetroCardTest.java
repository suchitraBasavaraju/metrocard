import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MetroCardTest {

    @Test
    void shouldReturnBalance() {
        MetroCard metroCard = new MetroCard();

        double balance = metroCard.balance();

        assertEquals(0, balance);
    }

    @Test
    void shouldCreditMoneyToCard() throws Exception {
        MetroCard metroCard = new MetroCard();

        metroCard.credit(50);

        assertEquals(50, metroCard.balance());
    }

    @Test
    void shouldNotCreditNegativeMoneyToCard() {
        MetroCard metroCard = new MetroCard();

        assertThrows(InvalidAmountException.class, () -> metroCard.credit(-1));
    }

    @Test
    void shouldDebitMoneyFromCard() throws Exception {
        MetroCard metroCard = new MetroCard();
        metroCard.credit(100);

        metroCard.debit(50);

        assertEquals(50, metroCard.balance());
    }

    @Test
    void shouldNotNegativeMoneyFromCard() throws Exception {
        MetroCard metroCard = new MetroCard();
        metroCard.credit(100);

        assertThrows(InvalidAmountException.class, () -> metroCard.debit(-1));
    }

    @Test
    void shouldThrowExceptionWhenDebitedMoreThanBalance() throws Exception {
        MetroCard metroCard = new MetroCard();
        metroCard.credit(50);

        assertThrows(InsufficientBalanceException.class, () -> metroCard.debit(51));
    }
}
