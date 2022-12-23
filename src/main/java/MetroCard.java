import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;

import java.util.Objects;

public class MetroCard {
    private String number;
    private double balance;

    public MetroCard(String mc1) {
        this.number = mc1;
        this.balance = 0;
    }

    public double balance() {
        return balance;
    }

    public void credit(double amount) throws InvalidAmountException {
        if(amount<0){
            throw new InvalidAmountException();
        }
        balance = balance + amount;
    }

    public void debit(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if(amount <0){
            throw new InvalidAmountException();
        }
        if(isInSufficient(amount)){
            throw new InsufficientBalanceException();
        }
        balance = balance - amount;
    }

    public boolean isInSufficient(double amount) {
        return amount > balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetroCard metroCard = (MetroCard) o;

        return Objects.equals(number, metroCard.number);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = number != null ? number.hashCode() : 0;
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public MetroCard hasNumber(String cardNumber) {
        if(Objects.equals(this.number, cardNumber)) {
            return  this;
        }
        return  null;
    }
}
