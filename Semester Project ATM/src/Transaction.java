/** 
 * 
 * ATM
 * 
 *ATM that allows you to get transaction history, withdrawal money, deposit money, or transfer money.
 * 
 * @author Fernando Canchola 
 */

import java.util.Date;

public class Transaction {
   private double amount;
   private Date timeStamp;
   private String memo;
   private Account inAccount;

   public Transaction(double amount, Account inAccount){
      this.amount = amount;
      this.inAccount = inAccount;
      this.timeStamp = new Date();
      this.memo = "";
   }
   public Transaction(double amount, String memo, Account inAccount) {
      // Call the two-arg constructor first
      this(amount, inAccount);
      // Set the memo
      this.memo = memo;
   }
   // Get amount
   public double getAmount() {
      return amount;
   }
   // Get summary line
   public String getSummaryLine() {
      if(amount > 0) return String.format("%s : $(%.02f) : %s", this.timeStamp.toString(), this.amount, this.memo);
      else return String.format("%s : $(%.02f) : %s", this.timeStamp.toString(), -this.amount, this.memo);
   }
}