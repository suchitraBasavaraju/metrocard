public enum PassengerType {
    SeniorCitizen(100), Adult(200), kids(50);

    private final double charges;

    PassengerType(double charges) {
        this.charges = charges;
    }

    public double getCharges() {
            return charges;
    }
}
