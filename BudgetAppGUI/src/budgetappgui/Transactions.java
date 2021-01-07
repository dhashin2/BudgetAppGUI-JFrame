package budgetappgui;

import java.sql.Date;
import java.time.LocalDate;

public class Transactions {
    private int TransactionID;
    private Date Date;
    private String Username;
    private String Transaction;
    private String Type;
    private double Amount;
    private String Category;
    private int Year;
    private String Month;

    public Transactions(int TransactionID, Date Date, String Username, String Transaction, String Type, double Amount, String Category, String Month, int Year) {
        this.TransactionID = TransactionID;
        this.Date = Date;
        this.Username = Username;
        this.Transaction = Transaction;
        this.Type = Type;
        this.Amount = Amount;
        this.Category = Category;
        this.Month = Month;
        this.Year = Year;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public int getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(int TransactionID) {
        this.TransactionID = TransactionID;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getTransaction() {
        return Transaction;
    }

    public void setTransaction(String Transaction) {
        this.Transaction = Transaction;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }
}
