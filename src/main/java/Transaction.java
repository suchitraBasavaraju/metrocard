import java.util.List;

public class Transaction {
    final MetroCard card;
    final PassengerType passengerType;
    final Station station;
    boolean returnJourney;
    double charge;

    public Transaction(MetroCard card, PassengerType passengerType, Station station, boolean returnJourney) {
        this.card = card;
        this.passengerType = passengerType;
        this.station = station;
        this.returnJourney = returnJourney;
    }

    public boolean isReturnJourney(List<Transaction> transactions) {
        int count = (int) transactions.stream().filter(transaction -> transaction.card == this.card).count();
        return count % 2 == 0;
    }

    public void update(double charge) {
        this.charge = charge;
    }

    public void updateJourney() {
        this.returnJourney = true;
    }
}
