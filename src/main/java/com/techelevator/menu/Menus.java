package com.techelevator.menu;

import com.techelevator.vendingmachine.VendingMachine;
import com.techelevator.vendingmachine.VendingSlot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Menus {
    boolean endVend;

    //scanner to use to collect user input in the command line, to allow user to navigate through the program
    private final Scanner USER_INPUT = new Scanner(System.in);
    private final double CENTS_IN_A_DOLLAR = 100.00;
    private final int QUARTER_VALUE_IN_CENTS = 25;
    private final int DIME_VALUE_IN_CENTS = 10;
    private final int NICKEL_VALUE_IN_CENTS = 5;

    Map<String, String> messages = new HashMap<>();
    private final File messageFile = new File("src/main/java/com/techelevator/menu/menuMessages.txt");
    public void loadMessageMap(){
        try(Scanner readInMenuMessages = new Scanner(messageFile)){
            while(readInMenuMessages.hasNextLine()){
                String lineOfInput = readInMenuMessages.nextLine();
                String [] strArr = lineOfInput.split("\\^");
                if (strArr.length >=2){
                    messages.put(strArr[0],strArr[1]);
                } else{
                    System.out.println("Invalid line format");
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }


    }

    public void runMenu(VendingMachine vendingMachine) throws IOException {
        loadMessageMap();
        welcomeScreen();


        while (!endVend) {
            int userInputMM = -1;
            int userInputPM = -1;

            while (userInputMM != 2 && userInputMM != 3) {
                mainMenu();
                userInputMM = getUserInputMM();
                implementChoiceMM(vendingMachine, userInputMM);
            }
            if (userInputMM == 2) {
                while (userInputPM != 3) {
                    userInputPM = getUserInputPM(vendingMachine);
                    implementChoicePM(vendingMachine, userInputPM);
                }
            }


        }
    }

    public void welcomeScreen() {
        System.out.println("||*****************************************||");
        System.out.println("||***  Welcome to the Vendo-Matic 800  ****||");
        System.out.println("||*****************************************||");
        System.out.println("||_________________________________________||");
        System.out.println();

    }

    public void mainMenu() {

        System.out.println();
        System.out.println("Please enter a number to select your menu item");
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit");

    }

    public void purchaseMenu(double balance) {
        System.out.printf("Current money provided: $%.2f \n", balance);
        System.out.println("_____________________________________");
        System.out.println("(1) Feed Money");
        System.out.println("(2) Select Product");
        System.out.println("(3) Finish Transaction");
    }

    public int getUserInputMM() {
        int choice;
        choice = 0;
        while (choice < 1 || choice > 4) {
            try {
                System.out.println();
                System.out.println(messages.get("mainMenuPrompt"));
                choice = Integer.parseInt(USER_INPUT.nextLine());
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println(messages.get("invalidNumMessage"));
            }

        }
        return choice;
    }

    public int getUserInputPM(VendingMachine vendingMachine) {
        int choice;
        choice = 0;
        while (choice < 1 || choice > 3) {
            try {

                System.out.println(vendingMachine.toString());
                System.out.println();
                purchaseMenu(vendingMachine.getVendingMachineBalance());
                System.out.println();
                System.out.println(messages.get("purchMenuPrompt"));
                choice = Integer.parseInt(USER_INPUT.nextLine());
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println(messages.get("invalidNumMessage"));
            }

        }
        return choice;
    }

    public void implementChoiceMM(VendingMachine vendingMachine, int choice) throws IOException {
        switch (choice) {
            case 1:
                System.out.println(vendingMachine.toString());
                break;
            case 2:
                break;
            case 3:
                endVend = true;
                System.out.println(messages.get("thankYouMessage"));
                System.out.println("________________________________________");
                System.out.println("                                          ");
                System.out.println(messages.get("comeBackSoonMessage"));
                break;
            case 4:
                System.out.println(vendingMachine.generateSalesReport());
                break;

            default:
                System.out.println(messages.get("unknownErrorMessage"));
                break;
        }

    }

    public void implementChoicePM(VendingMachine vendingMachine, int choice) throws IOException {
        switch (choice) {

            case 1:
                feedMoney(vendingMachine);
                break;

            case 2:
                selectProduct(vendingMachine);
                break;

            case 3:
                System.out.println(messages.get("thankYouMessage"));
                System.out.println("________________________________________");
                System.out.println("                                          ");
                finishTransaction(vendingMachine);

                break;
            default:
                System.out.println(messages.get("unknownErrorMessage"));
                break;
        }

    }

    private void finishTransaction(VendingMachine vendingMachine) {
        try{
            double changeDue;
            changeDue = vendingMachine.dispenseChange();
            System.out.printf("The customer will receive $%.2f \n", changeDue);
            System.out.println(makeChange(changeDue));
        }catch(IOException e){
            System.out.println(e.getMessage());
        }


    }

    private String makeChange(double changeDue) {
        //these variables are used as incrementers
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        // this will hold the return value after the coins are calculated.
        String coins;
        //turns the changDue into cents to work with.
        double roundedChangeDue = Math.round(changeDue *CENTS_IN_A_DOLLAR)/CENTS_IN_A_DOLLAR;
        double cents = roundedChangeDue * CENTS_IN_A_DOLLAR;

        while(cents - QUARTER_VALUE_IN_CENTS >= 0){
            cents = cents - QUARTER_VALUE_IN_CENTS;
            quarters++;
        }

        while(cents - DIME_VALUE_IN_CENTS >= 0){
            cents = cents - DIME_VALUE_IN_CENTS;
            dimes++;
        }

        while(cents - NICKEL_VALUE_IN_CENTS >= 0){
            cents = cents - NICKEL_VALUE_IN_CENTS;
            nickels++;
        }

        coins = "\n Quarters: " + quarters + " \n Dimes: " + dimes + "\n Nickels: " + nickels + "\n";
        return coins;
    }

    // method helps customer select product.
    private void selectProduct(VendingMachine vendingMachine) throws IOException {
        try {
            String selection;
            double itemPrice;
            int quantity;

            System.out.println(vendingMachine.toString());
            System.out.println(vendingMachine.getVendingMachineBalance());
            System.out.println();
            System.out.println(messages.get("enterSlotMessage"));
            selection = USER_INPUT.nextLine().toUpperCase();
            VendingSlot dispensedItem = vendingMachine.getVendingSlotData(selection);
            itemPrice = Double.parseDouble(dispensedItem.getPrice());
            quantity = dispensedItem.getQuantity();

            if(vendingMachine.getVendingMachineBalance() >= itemPrice){
                if(quantity > 0){
                    System.out.println("\n | Item Name: " + dispensedItem.getSnack().getName() + " \n | Item Price: " +
                            dispensedItem.getPrice()+ " \n | Balance Remaining: " + vendingMachine.getVendingMachineBalance() +
                            " \n | Snack Message: "  + dispensedItem.getSnack().dispenseMessage() + "\n\n");
                } else if (quantity == 0) {
                    System.out.println();
                }
            } else if (vendingMachine.getVendingMachineBalance() < itemPrice) {
                System.out.println();
                System.out.println(messages.get("insufficientFundsMessage"));
                System.out.println();
            }
            vendingMachine.purchaseSnack(selection);

        }catch (NumberFormatException | NullPointerException e){
            System.out.println();
            System.out.println(messages.get("invalidSlotMessage"));
            System.out.println();
        }

    }

    private void feedMoney(VendingMachine vendingMachine) throws IOException {
        try {
            int amountToFeed;
            System.out.println();
            System.out.println(messages.get("feedMachine"));
            amountToFeed = Integer.parseInt(USER_INPUT.nextLine());
            if(amountToFeed < 1){
                System.out.println();
                System.out.println(messages.get("feedQuantityErrorMessage"));
                System.out.println();
            }else{
                vendingMachine.feedMoney(amountToFeed);
            }

        }catch (NumberFormatException e){
            System.out.println();
            System.out.println(messages.get("notWholeDollarError"));
            System.out.println();
            System.out.println();
            System.out.println();
        }


    }


}