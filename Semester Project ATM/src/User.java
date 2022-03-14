/** 
 * 
 * ATM
 * 
 *ATM that allows you to get transaction history, withdrawal money, deposit money, or transfer money.
 * 
 * @author Fernando Canchola 
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import static java.lang.System.*;

public class User {

   private String firstName;
   private String lastName;
   private String uuid;
   private byte pinHash[];
   private ArrayList<Account> accounts;

   public User(String firstName, String lastName, String pin, Bank theBank) {
      // Set user name
      this.firstName = firstName;
      this.lastName = lastName;
      // Store the pin's MD5 hash , rather than original value , for security reason
      try {
         MessageDigest messageDigest = MessageDigest.getInstance("MD5");
         this.pinHash = messageDigest.digest(pin.getBytes());
      } catch (NoSuchAlgorithmException e) {
         err.println("error, caught NoSuchAlgorithmsException");
         e.printStackTrace();
         exit(1);
      }
      // Get a new Unique Universal ID for user
      this.uuid = theBank.getNewUserUUID();
      // Create empty list of accounts
      this.accounts = new ArrayList<Account>();
      //Display log message
      out.printf("New User %s, %s with ID %s created \nPin is: 1234 \n", firstName, lastName, this.uuid);
   }
   // Add Account
   public void addAccount(Account anAccount) {
      this.accounts.add(anAccount);
   }
   // Get UUID
   public String getUuid() {
      return this.uuid;
   }
   // Get first name
   public String getFirstName() {
      return firstName;
   }

   public boolean validatePin(String aPin) {
      try {
         MessageDigest messageDigest = MessageDigest.getInstance("MD5");
         return MessageDigest.isEqual(messageDigest.digest(aPin.getBytes()), this.pinHash);
      } catch (NoSuchAlgorithmException e) {
         err.println("error, caught NoSuchAlgorithmsException");
         e.printStackTrace();
         exit(1);
      }
      return false;
   }
   // Display account summary
   public void printAccountSummary() {
      out.printf("\n\n%s's accounts summary\n", this.getFirstName());
      for (int a = 0; a < this.accounts.size(); a++) {
         out.printf("%d) %s", a+1, this.accounts.get(a).getSummaryLine());
         out.println();
      }
      out.println();
   }

   public int numAccounts() {
      return this.accounts.size();
   }
   // Display account transaction history
   public void printAccountTransactionHistory(int accountIndex) {
      this.accounts.get(accountIndex).printTransactionHistory();
   }
   // Get account balance
   public double getAccountBalance(int fromAccountIndex) {
      return this.accounts.get(fromAccountIndex).getBalance();
   }
   // Get account UUID
   public Object getAccountUUID(int accountIndex) {
      return this.accounts.get(accountIndex).getUuid();
   }
   // Add account transaction
   public void addAccountTransaction(int accountIndex, double amount, String memo) {
      this.accounts.get(accountIndex).addTransaction(amount, memo);
   }
}