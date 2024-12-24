import java.util.*;
import java.time.LocalDateTime;



class Atm{


    Scanner sc;
    private String Account_Number;
    private int PIN;
    private float Account_Balance=5000.0f; //Initial Balance is Set to 5,000 INR.
    private static final HashMap<String,Integer> Users=new HashMap<>(); // Valid Users of ATM.
    private boolean isValid=false;
    private HashMap<Date,String> transactions_History =new HashMap<>(); // Stores the Transaction History of Each User's Operation.

    // Initialize the Users of ATM.
    protected final static void startMachine(){

        // Each User has A Unique Account Number,PIN.
        Users.put("A21126511180",2003);
        Users.put("A21126511146",2007);
        Users.put("A21126511016",2000);
        Users.put("A21126511007",2004);
        Users.put("A21126511041",2009);
    }

    // Constructer to Initialize the obj Values
    public Atm(String Account_Number,int PIN,Scanner sc){
        this.Account_Number=Account_Number;
        this.PIN=PIN;
        this.sc=sc;
    }

    //Method to Clear the console Screen.
    private void clear(){
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }

    // Validates the given User with The Registerd Users .
    // Grants access to Machine only if the Credentials are already present in Users.
    public void  validate(){
        if(Users.containsKey(Account_Number)){
            if(Users.get(Account_Number)==PIN){
                isValid=true;
                System.out.println("Welcome To ATM Machine ");
                System.out.println();
                menu();
            }
            else{
                System.out.println("Enter A Valid PIN");
                System.out.println();
            }
        }
        else{
            System.out.println("Enter A valid Acc Number");
            System.out.println();
        }
    }

    // Main Menu
    private void menu(){
        if(!isValid){
            System.out.println("Invalid User!");
            System.out.println();
            return;
        }

        // Options
        System.out.println("Choose The Operation :");
        System.out.println("Enter 1. to Check Balance");
        System.out.println("Enter 2. to Cash Withdraw");
        System.out.println("Enter 3. to Cash Deposit");
        System.out.println("Enter 4. to PIN Change");
        System.out.println("Enter 5. for Transaction History");
        System.out.println("0 To QUIT");
        try{
            int choice=sc.nextInt();

            // Mapping the user Request.
            switch(choice){
                case 1: checkBalance();
                        break;
                case 2: cashWithdraw();
                        break;
                case 3: cashDeposit();
                        break;
                case 4: pinChange();
                        break;
                case 5: transactionHistory();
                        break;
                case 0: exit_Atm();
                        break;
                default: clear();
                        System.out.println("Enter A Valid Choice!");
                        System.out.println();
                        menu();
                        break;
            }
        }

        // If User enters an mismatches Data Type.
        catch(InputMismatchException e){
            clear();
            System.out.println("Enter A Valid Choice!");
            System.out.println();
            return ;
        }
    }

    // Checks the current Balance.
    private void checkBalance(){
        clear();
        System.out.println("The Current Balance is : "+ this.Account_Balance);
        System.out.println();
        menu();
    }

    // Used to Deposit An Certain Amount.
    private void cashDeposit(){
        clear();
        System.out.print("Enter Amount to Deposit :");
        try{
            float cash=sc.nextFloat();
            Account_Balance+=cash;

            // History of Transtions is Stored.
            String transaction="+ "+ cash;
            transactions_History.put(new Date(),transaction);
            // Operation is printed.
            System.out.println("Cash of INR "+cash+" is Credited into your Account.");
            System.out.println();
        }
        catch(InputMismatchException e){
            System.out.println("Enter a Valid Amount!");
            System.out.println();
            return;
        }
        menu();
    }

    // Used to Withdraw A certain Amount.
    private void cashWithdraw(){
        clear();
        try{
            System.out.print("Enter Amount to Withdraw :");
            float cash=sc.nextFloat();
            if(cash>this.Account_Balance){
                System.out.println("Insufficient Funds!");
            }
            else{
                this.Account_Balance-=cash;

                // History of Transactions is Stored.
                String transaction="- "+ cash;
                transactions_History.put(new Date(),transaction);
                // Operation is printed.
                System.out.println("Amount of INR "+ cash +" is DEBITED from your Account.");
            }
            System.out.println();
        }
        catch(InputMismatchException e){
            System.out.println("Enter a Valid Amount!");
            System.out.println();
            return;
        }
        menu();
    }

    // Change The Existing PIN.
    private void pinChange(){

        clear();
        // Reading the old PIN.
        System.out.print("Enter Your old PIN :");
        int old_pin=sc.nextInt();
        // Proceded Only if the PIN is Validated .
        if(old_pin!= this.PIN){
            System.out.println("Incorrect PIN Number!");
            System.out.println();
        }
        else{
            System.out.print("Enter The New PIN : ");
            int curr_pin=sc.nextInt();

            System.out.print("Re-Enter the New PIN : ");
            int conf_pin=sc.nextInt();
            // Re Entered PIN should match the entered PIN. 
            if(curr_pin !=conf_pin){
                System.out.println("Passwords Did not Match!");
                System.out.println();
            }
            else{
                // Updating the PIN of Requested User.
                Users.put(Account_Number, curr_pin);
                System.out.println("PIN Changed Successfully!");
                System.out.println();
            }
        }
        menu();
    }

    // Prints The Transations occured in that particular Account.
    private void transactionHistory(){
        clear();

        // Time-Stamps and Transactions are Printed in a Table like Structure.
        System.out.println("           TIME STAMP      "+"            TRANSACTION     ");
        System.out.println("---------------------------------------------------------");
        for(Map.Entry<Date,String> e: transactions_History.entrySet()){
            System.out.println("  "+e.getKey()+"    |     "+e.getValue());
        }
        System.out.println();
        System.out.println("Enter 'B' to go BACK to MENU ");
        String c=sc.next();
        if(c.equals("B") || c.equals("b")){
            clear();
            menu();
        }
        else{
            transactionHistory();
        }
    }
    private void exit_Atm(){
        clear();
        System.out.println("Thank You! Visit Again !");
    }
    
}
public class AtmMachine{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Atm.startMachine();
        try{
            System.out.print("Enter The Account Number : ");
            String Account_Number=sc.next();
            System.out.print("Enter The PIN : ");
            int PIN=sc.nextInt();
            Atm userAtm =new Atm(Account_Number,PIN,sc);
            userAtm.validate();

        }
        catch(InputMismatchException e){
            System.out.println("Enter Valid credentials!");
        }
        finally{
            sc.close();
        }
    }
}