public enum PassengerType {
    SENIOR_CITIZEN(100),
    ADULT(200),
    KID(50);

    private final double charges;

    PassengerType(double charges) {
        this.charges = charges;
    }

    public double getCharges() {
            return charges;
    }
}
