import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Metro {
    public static final double AUTO_DEBIT_CHARGE = 0.02;
    public static final double HALF_CHARGE = 0.5;
    private double serviceFeeForRecharge = 0;
    private final List<Transaction> transactions;

    public Metro() {
        transactions = new ArrayList<>();
    }

    public void checkIn(MetroCard card, PassengerType passengerType, Station station) throws InvalidAmountException, InsufficientBalanceException {
        double charges = passengerType.getCharges();
        Transaction transaction = new Transaction(card, passengerType, station,false);
        transactions.add(transaction);

        double discount = 0;
        if (transaction.isReturnJourney(transactions)) {
            discount = charges * HALF_CHARGE;
            transaction.updateJourney();
        }
        serviceFeeForRecharge = deductFromCard(card, charges - discount);
        double charge = charges - discount + serviceFeeForRecharge;
        transaction.update(charge);
        station.updateAmount(charge);
        station.updateDiscount(discount);
    }

    private double deductFromCard(MetroCard card, double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (card.isInSufficient(amount)) {
            double rechargeAmount = amount - card.balance();
            serviceFeeForRecharge = rechargeAmount * AUTO_DEBIT_CHARGE;
            card.credit(rechargeAmount + serviceFeeForRecharge);
            card.debit(amount + serviceFeeForRecharge);
            return serviceFeeForRecharge;
        }
        card.debit(amount);
        return 0;
    }

    public double collectionAt(Station station) {
        return Math.round(station.collection());
    }

    public double discountAt(Station station) {
        return Math.round(station.discount());
    }

    public Map<PassengerType, Integer> passengersAt(Station station) {
        Map<PassengerType, Double> chargesCollected = new HashMap<>();
        Map<PassengerType, Integer> passengerCount = new HashMap<>();
        Map<PassengerType, Integer> passengerSummaryInDescendingOrder = new HashMap<>();

        PassengerType[] passengerTypes = PassengerType.values();
        for (PassengerType type : passengerTypes) {
            List<Transaction> transactionsAtStation = transactions.stream()
                    .filter(transaction -> transaction.station.equals(station))
                    .filter(transaction -> transaction.passengerType.equals(type))
                    .collect(Collectors.toList());

            double collectedAmount = transactionsAtStation.stream().mapToDouble(r -> r.charge).sum();
            int count = (int) transactionsAtStation.stream().filter(tra -> !tra.returnJourney).count();
            Set<MetroCard> cards = transactionsAtStation.stream().map(trans -> trans.card).collect(Collectors.toSet());

            chargesCollected.put(type, collectedAmount);
            passengerCount.put(type, transactionsAtStation.size());

        }
        boolean Descending = false;
        Map<PassengerType, Double> result = sortMap(chargesCollected, Descending);

//        result.forEach((key, value) -> System.out.println("Station:"+ station.toString() +"Key : " + key + " Value : " + value + "Passenger Count :" + passengerCount.get(key)));
        result.forEach((key, value) -> passengerSummaryInDescendingOrder.put(key,passengerCount.get(key)));

        return passengerSummaryInDescendingOrder;
    }

    private Map<PassengerType, Double> sortMap(Map<PassengerType, Double> chargesCollected, final boolean order) {
        List<Map.Entry<PassengerType, Double>> list = new LinkedList<>(chargesCollected.entrySet());

        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

}

