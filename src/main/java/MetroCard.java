import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;

public class MetroCard {
    private double balance;

    public MetroCard() {
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
        if(amount > balance){
            throw new InsufficientBalanceException();
        }
        balance = balance - amount;
    }
}
