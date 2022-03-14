/** 
 * 
 * ATM
 * 
 *ATM that allows you to get transaction history, withdrawal money, deposit money, or transfer money.
 * 
 * @author Fernando Canchola 
 */

import java.util.ArrayList;
import java.util.Random;

public class Bank {
   private String name;
   private ArrayList<User> users;
   private ArrayList<Account> accounts;

   public Bank(String name) {
      this.name = name;
      this.users = new ArrayList<User>();
      this.accounts = new ArrayList<Account>();
   }
   // Get name
   public String getName() {
      return name;
   }
   // Set name
   public void setName(String name) {
      this.name = name;
   }
   // Get users
   public ArrayList<User> getUsers() {
      return users;
   }
   // Set users
   public void setUsers(ArrayList<User> users) {
      this.users = users;
   }
   // Get Accounts
   public ArrayList<Account> getAccounts() {
      return accounts;
   }
   // Set Accounts
   public void setAccounts(ArrayList<Account> accounts) {
      this.accounts = accounts;
   }
   // New User UUID
   public String getNewUserUUID() {
      String uuid;
      Random random = new Random();
      int uuidLeng = 8;
      boolean nonUnique;
      // Loop until we get a unique ID
      do {
         // Generate the number
         uuid = "";
         for (int i = 0; i < uuidLeng; i++) {
            uuid += ((Integer)random.nextInt(10)).toString();
         }
         // Check to make sure it's unique
         nonUnique = false;
         for (User user : this.users) {
            if (user.getUuid().equals(uuid)) {
               nonUnique = true;
               break;
            }
         }
      } while (nonUnique);
      return uuid;
   }
   // Get New Bank Account UUID
   public String getNewBankAccountUUID() {
      String uuid;
      Random random = new Random();
      int uuidLeng = 10;
      boolean nonUnique;
      // Loop until we get a unique ID
      do {
         // Generate the number
         uuid = "";
         for (int i = 0; i < uuidLeng; i++) {
            uuid += ((Integer)random.nextInt(10)).toString();
         }
         // Check to make sure it's unique
         nonUnique = false;
         for (Account account : this.accounts) {
            if (account.getUuid().equals(uuid)) {
               nonUnique = true;
               break;
            }
         }
      } while (nonUnique);
      return uuid;
   }
   // Add Account
   public void addAccount(Account anAccount) {
      this.accounts.add(anAccount);
   }

   public User addUser(String firstName, String lastName, String pin) {
      // Create a new user object and add it to the list
      User newUser = new User(firstName, lastName, pin, this);
      this.users.add(newUser);
      // Create a saving bank account for user
      Account newAccount = new Account("Savings", newUser, this);
      newUser.addAccount(newAccount);
      this.addAccount(newAccount);
      return newUser;
   }

   public User userLogin(String userId, String pin) {
      // Search through list of users
      for (User user : this.users) {
         if (user.getUuid().equals(userId) && user.validatePin(pin)) {
            return user;
         }
      }
      return null;
   }
}