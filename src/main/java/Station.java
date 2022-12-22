public class Station {
    private double collection;
    private double discount;

    public Station(String central) {
    }

    static Station central() {
        return new Station("Central");
    }

    static Station airport() {
        return new Station("Airport");
    }

    public void updateAmount(double amount) {
        collection = collection + amount;
    }

    public double getCollection() {
        return collection;
    }

    public void updateDiscount(double amount) {
        discount = discount + amount;
    }

    public double getDiscount() {
        return discount;
    }
}
