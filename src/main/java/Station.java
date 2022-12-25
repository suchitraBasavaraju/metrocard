public class Station {
    private final String stationName;
    private double collection;
    private double discount;

    @Override
    public String toString() {
        return "Station{" +
                "stationName='" + stationName + '\'' +
                ", collection=" + collection +
                ", discount=" + discount +
                '}';
    }

    private Station(String stationName) {
        this.stationName = stationName;
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

    public double collection() {
        return collection;
    }

    public void updateDiscount(double amount) {
        discount = discount + amount;
    }

    public double discount() {
        return discount;
    }
}
