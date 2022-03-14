/** 
 * 
 * ATM
 * 
 *ATM that allows you to get transaction history, withdrawal money, deposit money, or transfer money.
 * 
 * @author Fernando Canchola 
 */

import java.util.Scanner;
import static java.lang.System.*;

public class ATM {

   public static void main(String[] args) {
      Scanner scanner = new Scanner(in);
      Bank theBank = new Bank("Bank of Indiana");
      // Add a user and create a savings account
      User aUser = theBank.addUser("Frank", "Brown","1234");
      // Add a checking account for the user
      Account newAccount = new Account("Checking", aUser, theBank);
      aUser.addAccount(newAccount);
      theBank.addAccount(newAccount);
      User currentUser;
      while (true) {
         // Stay in the login prompt until successful login
         currentUser = ATM.mainMenuPrompt(theBank, scanner);
         // Stay in main menu until user quits
         ATM.printUserMenu(currentUser, scanner);
      }
   }

   private static User mainMenuPrompt(Bank theBank, Scanner scanner) {
      String userID;
      String pin;
      User authUser;
      // Prompt the user for user id and pin until correct one is entered
      do {
         out.printf("\n\nWelcome to %s \n\n", theBank.getName());
         out.printf("Enter user ID: ");
         userID = scanner.nextLine();
         out.printf("Enter Pin: ");
         pin = scanner.nextLine();
         // Get the user object corresponding to the ID and PIN
         authUser = theBank.userLogin(userID, pin);
         if (authUser == null) {
            out.println("Incorrect user id / pin combination. Please try again.");
         }
      } while (authUser == null); // Loop until successful login
      return authUser;
   }

   private static void printUserMenu(User currentUser, Scanner scanner) {
      // print a summary of the user's accounts
      currentUser.printAccountSummary();
      int choice;

      // Display menu
      do {
         out.printf("Welcome %s, what would you like to do?\n", currentUser.getFirstName());
         out.println(" 1) Transaction History.");
         out.println(" 2) Withdrawal");
         out.println(" 3) Deposit");
         out.println(" 4) Transfer");
         out.println(" 5) Quit");
         choice = scanner.nextInt();
         // Display Invalid choice
         if (choice < 1 || choice > 5) {
            out.println("Invalid choice, Please choose 1 - 5");
         }
      } while (choice < 1 || choice > 5);
      // Process the choice
      switch (choice){
         case 1 :
            ATM.showTransactionHistory(currentUser, scanner);
            break;
         case 2 :
            ATM.withdrawalFunds(currentUser, scanner);
            break;
         case 3 :
            ATM.depositFunds(currentUser, scanner);
            break;
         case 4 :
            ATM.transferFunds(currentUser, scanner);
            break;
         case 5:
            scanner.nextLine();
            break;
      }

      // Display this menu unless user wants to quit
      if (choice != 5)
         ATM.printUserMenu(currentUser, scanner);

   }

   // Funds Transfer
   public static void transferFunds(User currentUser, Scanner scanner) {
      int fromAccount;
      int toAccount;
      double amount;
      double accountBalance;
      // Get the account to transfer from
      do {
         out.printf("Enter the number (1-%d) of the account " +
         "to transfer from: ", currentUser.numAccounts());
         fromAccount = scanner.nextInt() - 1;
         if (fromAccount < 0 || fromAccount >= currentUser.numAccounts()) {
            out.println("Invalid account. Please try again");
         }
      } while (fromAccount < 0 || fromAccount >= currentUser.numAccounts());
      accountBalance = currentUser.getAccountBalance(fromAccount);
      // Get the account to transfer to
      do {
         out.printf("Enter the number (1-%d) of the account " +
               "to transfer to: ", currentUser.numAccounts());
         toAccount = scanner.nextInt() - 1;
         if (toAccount < 0 || toAccount >= currentUser.numAccounts()) {
            out.println("Invalid account. Please try again");
         }
      } while (toAccount < 0 || toAccount >= currentUser.numAccounts());
      // Get amount to transfer
      do {
         out.printf("Enter amount to transfer (max $%.02f) : $", accountBalance);
         amount = scanner.nextDouble();
         if (amount < 0) {
            out.println("Amount must be greater than Zero.");
         } else if (amount > accountBalance) {
            out.printf("Amount must not be greater than \n" +
                  "balance of $%.02f.\n", accountBalance);
         }
      } while (amount < 0 || amount > accountBalance);
      // Do the transfer
      currentUser.addAccountTransaction(fromAccount, -1*amount, String.format("Transfer to account %s", currentUser.getAccountUUID(toAccount)));
      currentUser.addAccountTransaction(toAccount, amount, String.format("Transfer to account %s", currentUser.getAccountUUID(fromAccount)));
   }

   // Funds Deposit
   public static void depositFunds(User currentUser, Scanner scanner) {
      int toAccount;
      double amount;
      double accountBalance;
      String memo;
      // Get the account to transfer from
      do {
         out.printf("Enter the number (1-%d) of the account " +
               "to deposit in: ", currentUser.numAccounts());
         toAccount = scanner.nextInt() - 1;
         if (toAccount < 0 || toAccount >= currentUser.numAccounts()) {
            out.println("Invalid account. Please try again");
         }
      } while (toAccount < 0 || toAccount >= currentUser.numAccounts());
      accountBalance = currentUser.getAccountBalance(toAccount);
      // Get amount to transfer
      do {
         out.printf("Enter amount to deposit : $", accountBalance);
         amount = scanner.nextDouble();
         if (amount < 0) {
            out.println("Amount must be greater than Zero.");
         }
      } while (amount < 0);

      scanner.nextLine();
      // Get a memo
      out.print("Enter a memo : ");
      memo = scanner.nextLine();
      // Do the withdrawal
      currentUser.addAccountTransaction(toAccount, amount, memo);

   }

   // Funds Withdraw
   public static void withdrawalFunds(User currentUser, Scanner scanner) {
      int fromAccount;
      double amount;
      double accountBalance;
      String memo;
      // Get the account to transfer from
      do {
         out.printf("Enter the number (1-%d) of the account " +
               "to withdraw from: ", currentUser.numAccounts());
         fromAccount = scanner.nextInt() - 1;
         if (fromAccount < 0 || fromAccount >= currentUser.numAccounts()) {
            out.println("Invalid account. Please try again");
         }
      } while (fromAccount < 0 || fromAccount >= currentUser.numAccounts());
      accountBalance = currentUser.getAccountBalance(fromAccount);
      // Get amount to transfer
      do {
         out.printf("Enter amount to transfer (max $%.02f) : $", accountBalance);
         amount = scanner.nextDouble();
         if (amount < 0) {
            out.println("Amount must be greater than Zero.");
         } else if (amount > accountBalance) {
            out.printf("Amount must not be greater than \n" +
                  "balance of $%.02f.\n", accountBalance);
         }
      } while (amount < 0 || amount > accountBalance);

      scanner.nextLine();
      // Get a memo
      out.print("Enter a memo : ");
      memo = scanner.nextLine();
      // Do the withdrawal
      currentUser.addAccountTransaction(fromAccount, -1*amount, memo);
   }

   public static void showTransactionHistory(User currentUser, Scanner scanner) {
      int theAccount;
      // Get account whose transaction history to look at
      do {
         out.printf("Enter the number (1-%d) of the account " +
               "whose transactions you want to see: ", currentUser.numAccounts());
         theAccount = scanner.nextInt() - 1;
         if (theAccount < 0 || theAccount >= currentUser.numAccounts()) {
            out.println("Invalid account. please try again");
         }
      } while (theAccount < 0 || theAccount >= currentUser.numAccounts());
      // Display the transaction history
      currentUser.printAccountTransactionHistory(theAccount);
   }
}