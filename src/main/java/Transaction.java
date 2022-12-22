import java.util.List;

public class Transaction {
    private final MetroCard card;
    private final PassengerType passengerType;
    private final Station station;

    public Transaction(MetroCard card, PassengerType passengerType, Station station) {
        this.card = card;
        this.passengerType = passengerType;
        this.station = station;
    }

    public boolean isReturnJourney(List<Transaction> transactions) {
        int count = (int) transactions.stream().filter(transaction -> transaction.card == this.card).count();
        return count % 2 == 0;
    }
}
