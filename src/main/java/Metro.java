import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;

import java.util.ArrayList;
import java.util.List;

public class Metro {
    public static final double AUTO_DEBIT_CHARGE = 0.02;
    public static final double HALF_CHARGE = 0.5;
    private double serviceFeeForRecharge = 0;
    private List<Transaction> transactions;

    public Metro() {
        transactions = new ArrayList<>();
    }

    public void checkin(MetroCard card, PassengerType passengerType, Station station) throws InvalidAmountException, InsufficientBalanceException {
        double charges = passengerType.getCharges();
        serviceFeeForRecharge = 0;
        Transaction transaction = new Transaction(card, passengerType, station);
        transactions.add(transaction);

        double discount =0;
        if(transaction.isReturnJourney(transactions)){
             discount = charges * HALF_CHARGE;
        }
        serviceFeeForRecharge = deductFromCard(card, charges-discount);
        station.updateAmount(charges-discount);
        station.updateAmount(serviceFeeForRecharge);
        station.updateDiscount(discount);
    }

    private double deductFromCard(MetroCard card, double amount) throws InvalidAmountException, InsufficientBalanceException {
       if(!card.hasSufficient(amount))
       {
           card.debit(amount);
           return 0;
       }
        double rechargeAmount = amount- card.balance();
        serviceFeeForRecharge =  rechargeAmount * AUTO_DEBIT_CHARGE;
        card.credit(rechargeAmount+serviceFeeForRecharge);
        card.debit(amount + serviceFeeForRecharge);
        return serviceFeeForRecharge;
    }

    public double collectionAt(Station station) {
        return Math.round(station.getCollection());
    }

    public double discountAt(Station station) {
        return Math.round(station.getDiscount());
    }
}
