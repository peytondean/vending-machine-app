package com.techelevator.vendingmachine;

import com.techelevator.util.VMLog;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * VendingMachine will be the class to connect all of our classes together
 * in order to implement the functionality all in one area
 */
public class VendingMachine {

    // variable to hold the beginning quantity! No Magic numbers!
    // From Peyton: small detail of making the final variable name all caps
    private static final String VENDING_MACHINE_CSV = "vendingmachine.csv";

    private static final char STARTING_LETTER_LOCATION = 'A';
    private static final char ENDING_LETTER_LOCATION = 'Z';
    private static final int STARTING_NUMBER_LOCATION = 1;
    private static final String SALES_REPORT_STARTING_FILE_NAME = "Sales_Report_";

    //  DO NOT USE FLOAT/DOUBLE WHEN HANDLING MONEY/CURRENCY
    private BigDecimal purchaseBalance;

    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    //Map to hold the vending machine slot information: string for location, VendingSlot for everything else.
    private final Map<String, VendingSlot> slotInformation = new HashMap<>();

    public VendingMachine() {
        this.purchaseBalance = new BigDecimal("0.00");
    }

    /**
     * Generates a sales report within the sales reports directory
     *
     * @return A string that can be printed out to the user to
     * display the latest sales report that was created.
     * @throws IOException Handles any issues if something else is trying to modify the file while
     *                     it is being written to.
     */
    public String generateSalesReport() throws IOException {
        return VMLog.logSalesReport(SALES_REPORT_STARTING_FILE_NAME, slotInformation);
    }

    /**
     * Loads in the vending machine data from the vendingMachineSetupCSV String property
     */
    public void loadInVendingMachineData() {

        //	Creating the File object using the filePath provided in order to pass
        //	it in to the Scanner object to be used in the try-catch block
        //Nathan Comment - loaded the file path from vending machine
        File vendingMachineData = new File(VENDING_MACHINE_CSV);

        try (Scanner fileReader = new Scanner(vendingMachineData)) {
            //	Making String object in order to prevent from constantly creating new ones
            //	inside the while loop
            //  I moved the local Map container outside the while loop
            //  to fix another issue with this.
            Map<String, VendingSlot> vendMachineSlotMapItem = new HashMap<>();

            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();
                String[] row = currentLine.split("\\|");
                VendingSlot eachSlot = new VendingSlot(row[0], row[1], row[2], row[3]);
                vendMachineSlotMapItem.put(row[0], eachSlot);
            }
            // using string array created above populate slots and map in vending machine class
            setSlotInformation(vendMachineSlotMapItem);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Loads in the vending machine data from a provided file path.
     *
     * @param filePath The location of the file that we want to load in.
     */
    public void loadInVendingMachineData(String filePath) {

        //	Creating the File object using the filePath provided in order to pass
        //	it in to the Scanner object to be used in the try-catch block
        //Nathan Comment - loaded the file path from vending machine
        File vendingMachineData = new File(filePath);

        try (Scanner fileReader = new Scanner(vendingMachineData)) {
            //	Making String object in order to prevent from constantly creating new ones
            //	inside the while loop

            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();

                //	Parsing data via delimiter splitting
                String[] vendorInformation = currentLine.split("\\|");

                //  Made a helper method just to keep the code readable
                VendingSlot currentSlot = createVendingSlot(vendorInformation);

                //  Adding the restocked vending slot to the container
                slotInformation.put(currentSlot.getSlotLocation(), currentSlot);
            }
            // using string array created above populate slots and map in vending machine class
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Creates a VendingSlot from data parsed from a CSV file
     *
     * @param vendingSlotData An array of string objects that contains
     *                        the slot location in the vending machine,
     *                        the name of the snack, the price for the snack,
     *                        and what category of snack it is.
     *                        [0] - Slot location in the machine
     *                        [1] - Name of the snack
     *                        [2] - Price of the snack
     *                        [3] - Type of snack
     * @return A VendingSlot object with its properties initialized
     */
    private VendingSlot createVendingSlot(String[] vendingSlotData) {
        return new VendingSlot(vendingSlotData[0], vendingSlotData[1], vendingSlotData[2], vendingSlotData[3]);
    }

    /**
     * {@inheritDoc}
     * Loops through the container to call each VendingSlot objects toString method
     * in order to display all the objects that are currently in our vending machine.
     *
     * @return A String object that contains all the key values data in the container in alphanumeric order.
     */
    @Override
    public String toString() {
        StringBuilder vendorInventory = new StringBuilder();
        ///////////////////////////////////
        //  Setting up for a while loop here
        //  Essentially iterating through the Map container to find keys in alphanumeric order
        //  We are starting at A and 1 as a default
        boolean isVendingMachineDisplayCompleted = false;
        char alphaNumericIterator = STARTING_LETTER_LOCATION;
        int currentSlot = STARTING_NUMBER_LOCATION;
        ///////////////////////////////////

        //  Creating a String object that concatenates the char and int from earlier to get access via key value
        //  EX: A + 1 = "A1" in String form, which will tell the application where to go in the Map container
        String currentKeyToCheck = Character.toString(alphaNumericIterator) + currentSlot;

        //  So, if we are still building our StringBuffer, continuing searching in the Map
        while (!isVendingMachineDisplayCompleted) {
            //  If this key is valid, lets get the value and call toString on it
            if (slotInformation.containsKey(currentKeyToCheck)) {
                vendorInventory.append(slotInformation.get(currentKeyToCheck).toString());

                //  Postfix incrementing the starting point
                currentSlot++;
            } else {
                //  If we are at the beginning of a new letter and there wasn't anything there
                //  then we need to get out of this while loop
                if (currentSlot == STARTING_NUMBER_LOCATION || alphaNumericIterator > ENDING_LETTER_LOCATION) {
                    isVendingMachineDisplayCompleted = true;
                    continue;
                }

                //  Otherwise, lets reset our slot counter and increment our char(ASCII) value
                //  This will change A->B due to how char values work
                //  A = 65, so if we add one to it, it will be at B, which equals 66, etc...
                currentSlot = STARTING_NUMBER_LOCATION;
                alphaNumericIterator++;
            }

            //  Updating the current key to look for here
            currentKeyToCheck = Character.toString(alphaNumericIterator) + currentSlot;
        }

        //  Our String buffer is finally built, so lets call its toString method
        //  and return out of here
        return vendorInventory.toString();
    }

    /**
     * Provides a VendingSlot object in order to obtain information that pertains to it
     *
     * @param vendingSlotNumber A string that will be used to index into the Map container holding all the VendingSlots
     * @return A VendingSlot object that can be accessed and modified if needed or a nullptr if isn't found
     */
    public VendingSlot getVendingSlotData(String vendingSlotNumber) {
        return slotInformation.getOrDefault(vendingSlotNumber, null);
    }

    /**
     * Empties out the vending machine with the remainder cash left over from the transactions
     * This method will also log this transaction
     *
     * @return The remaining amount of change to the customer via double primitive
     */
    public double dispenseChange() throws IOException {
        BigDecimal changedReturn = purchaseBalance;
        if (changedReturn.compareTo(purchaseBalance) < 0) {
            changedReturn = new BigDecimal("0.0");
        }
        purchaseBalance = new BigDecimal("0.0");

        VMLog.logTransaction(VMLog.LogType.GIVE.messageToAppend() + " " +
                currencyFormatter.format(changedReturn.doubleValue()) + " " +
                currencyFormatter.format(purchaseBalance.doubleValue()));

        return changedReturn.doubleValue();
    }


    /**
     * Empties out the vending machine with the remainder cash left over from the transactions
     * This method will also log this transaction
     *
     * @param disableLogging True to disable logging.
     *                       False to enable logging.
     * @return The remaining amount of change to the customer via double primitive
     */
    public double dispenseChange(boolean disableLogging) throws IOException {
        BigDecimal changedReturn = purchaseBalance;
        if (changedReturn.compareTo(purchaseBalance) < 0) {
            changedReturn = new BigDecimal("0.0");
        }
        purchaseBalance = new BigDecimal("0.0");

        if (!disableLogging) {
            VMLog.logTransaction(VMLog.LogType.GIVE.messageToAppend() + " " +
                    currencyFormatter.format(changedReturn.doubleValue()) + " " +
                    currencyFormatter.format(purchaseBalance.doubleValue()));
        }

        return changedReturn.doubleValue();
    }

    /**
     * Purchases a snack from the Map container if the key is valid, the balance can over it and if the quantity is sufficient.
     *
     * @param vendingSlotLocation The key needed to find the snack in the Map container
     * @throws IOException Handling IOException when writing to the vending machine transaction log file.
     */
    public void purchaseSnack(String vendingSlotLocation) throws IOException {

        //  Checking to see if the vending slot location is valid and if there is anything in stock here
        if (slotInformation.containsKey(vendingSlotLocation) && slotInformation.get(vendingSlotLocation).getQuantity() > 0) {

            //  Capturing the current vending machine slot here for readability
            VendingSlot currentSlot = slotInformation.get(vendingSlotLocation);

            //  Capturing the cost of the snack here for readability
            double snackPrice = Double.parseDouble(currentSlot.getPrice());

            //  If the vending machine was fed enough money to cover the transaction...
            if (purchaseBalance.doubleValue() >= snackPrice) {

                //  Remove that amount from the vending machine balance
                purchaseBalance = purchaseBalance.subtract(BigDecimal.valueOf(snackPrice));

                //  Update our vending machine slot
                slotInformation.put(vendingSlotLocation, currentSlot);

                //  Make sure to remove the snack that was bought from the vending machine slot location
                slotInformation.get(vendingSlotLocation).dispenseSnack();

                //  Building a string to pass into the vending machine transaction log
                String purchasedProduct = currentSlot.getSnack().getName() + " " + vendingSlotLocation + " ";

                //  Logging the transaction
                VMLog.logTransaction(purchasedProduct + " " +
                        currencyFormatter.format(snackPrice) + " " +
                        currencyFormatter.format(purchaseBalance.doubleValue()));

            }
        }
    }

    /**
     * Adds money to the vending machines balance.
     * This method checks in ensure that there isn't any way to accept a negative amount.
     * This method will also log this transaction
     *
     * @param amountToFeed The amount to add to the vending machines balance.
     */
    public void feedMoney(int amountToFeed) throws IOException {
        if (amountToFeed > 0) {
            purchaseBalance = purchaseBalance.add(BigDecimal.valueOf(amountToFeed));

            VMLog.logTransaction(VMLog.LogType.DEPOSIT.messageToAppend() + " " +
                    currencyFormatter.format(amountToFeed) + " " +
                    currencyFormatter.format(purchaseBalance));
        }
    }

    /**
     * Adds money to the vending machines balance.
     * This method checks in ensure that there isn't any way to accept a negative amount.
     * This method will also log this transaction
     * THIS METHOD IS ONLY INTENDED FOR UNIT TESTING ONLY!
     *
     * @param amountToFeed   The amount to add to the vending machines balance.
     * @param disableLogging True to disable logging.
     *                       False to leave enabled.
     */
    public void feedMoney(double amountToFeed, boolean disableLogging) throws IOException {
        if (amountToFeed > 0.0) {
            purchaseBalance = purchaseBalance.add(BigDecimal.valueOf(amountToFeed));

            if (!disableLogging) {
                VMLog.logTransaction(VMLog.LogType.DEPOSIT.messageToAppend() + " " +
                        currencyFormatter.format(amountToFeed) + " " +
                        currencyFormatter.format(purchaseBalance));
            }
        }
    }

    /**
     * Implement Getters and Setters
     */
    private void setSlotInformation(Map<String, VendingSlot> slotInformation) {
        this.slotInformation.putAll(slotInformation);
    }

    /**
     * Method that returns the balance in the vending machine as a double primitive value
     *
     * @return The vending machine balance provided as a double
     */
    public final double getVendingMachineBalance() {
        return purchaseBalance.doubleValue();
    }
}
