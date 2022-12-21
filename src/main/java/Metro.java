import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;

import java.util.ArrayList;
import java.util.List;

public class Metro {
    public static final double DISCOUNT_FOR_RETURN = 0.02;
    public static final double HALF_CHARGE = 0.5;
    private double serviceFeeForRecharge;
    private List<Transaction> transactions;

    public Metro() {
        transactions = new ArrayList<>();
    }

    public void checkin(MetroCard card, PassengerType passengerType, Station station) throws InvalidAmountException, InsufficientBalanceException {
        double charges = passengerType.getCharges();

        Transaction transaction = new Transaction(card, passengerType, station, false);
        transactions.add(transaction);

        double discount =0;
        if(transaction.isReturnJourney(transactions)){
             discount = charges * HALF_CHARGE;
        }


        deductFromCard(card, charges);
        station.updateAmount(charges-discount);
        station.updateDiscount(discount);
    }

    private void deductFromCard(MetroCard card, double charges) throws InvalidAmountException, InsufficientBalanceException {
        try {
            card.debit(charges);
        } catch (InsufficientBalanceException e) {
            double rechargeAmount = card.balance() - charges;
            serviceFeeForRecharge = serviceFeeForRecharge + rechargeAmount * DISCOUNT_FOR_RETURN;
            card.credit(rechargeAmount);
            card.debit(charges);
        }
    }

    public double collectionAt(Station station) {
        return station.getCollection();
    }

    public double discountAt(Station station) {
        return station.getDiscount();
    }
}
