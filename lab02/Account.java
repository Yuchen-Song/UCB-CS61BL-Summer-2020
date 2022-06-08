/**
 * This class represents a bank account whose current balance is a nonnegative
 * amount in US dollars.
 */
public class Account {

    public int balance;
    public Account parentAccount;//saving account

    /** Initialize an account with the given balance. */
    public Account(int balance) {
        this.balance = balance;
        parentAccount = null;
    }

    /** Deposits amount into the current account. */
    public void deposit(int amount) {
        if (amount < 0) {
            System.out.println("Cannot deposit negative amount.");
        } else {
            balance += amount;
        }
    }

    /** Initialize an account with the given balance and another account. */
    public Account(int balance, Account parent){
        this.balance = balance;
        parentAccount = parent;
    }

    /**
     * Subtract amount from the account if possible. If subtracting amount
     * would leave a negative balance, print an error message and leave the
     * balance unchanged.
     */
    public boolean withdraw(int amount) {
        // TODO
        if (amount < 0) {
            System.out.println("Cannot withdraw negative amount.");
            return false;
        } else if (balance < amount) {
            if(parentAccount == null){
                System.out.println("Insufficient funds");
                return false;
            }
            else if(parentAccount.balance + balance < amount){
                System.out.println("Insufficient funds");
                return false;
            } else {
                amount = amount - balance;
                balance = 0;//withdraw all the balance from the child account
                parentAccount.withdraw(amount);
                return true;
            }
        } else {
            balance -= amount;
            return true;
        }
    }

    /**
     * Merge account other into this account by removing all money from other
     * and depositing it into this account.
     */
    public void merge(Account other) {
        // TODO
        deposit(other.balance);
        other.withdraw(other.balance);
    }

    //testing part
/*
    public static void main(String[] args){
        Account zoe = new Account(500);
        Account matt = new Account(100, zoe);
        if(matt.withdraw(50) == true) {
            System.out.println("After withdrawing, matt has " + matt.balance);
            System.out.println("while zoe has " + zoe.balance);
        }
    }
*/


}