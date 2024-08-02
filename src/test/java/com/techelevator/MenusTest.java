package com.techelevator;

import com.techelevator.menu.Menus;
import com.techelevator.vendingmachine.VendingMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenusTest {

    enum menuSelection{
        OFFSET_ENUM,
        DISPLAY_VENDING_MACHINE_ITEMS,
        PURCHASE_MENU,
        EXIT,
        SALES_REPORT,
        FEED_MONEY,
        PURCHASE_SNACK,
        FINISH_TRANSACTION
    }

    Menus testMenus;
    VendingMachine testVendingMachine;
    int userInput;

    //  This property can be used to access all information that was printed to the application
    //  during its life cycle
    ByteArrayOutputStream outputStream;

    @Before
    public void initialize() {
        testMenus = new Menus();
        testVendingMachine = new VendingMachine();
        testVendingMachine.loadInVendingMachineData();
        userInput = -1;

        //  The ByteArrayOutputStream will store the output from the implementChoiceMM method
        //  So we are creating the setup for it here
        outputStream = new ByteArrayOutputStream();

        //  We are using PrintStream to redirect ALL System.out output to our systemOutLastInput object we just created
        PrintStream printStream = new PrintStream(outputStream);

        //  This method changes there buffer that System uses to queue up messages to the console
        System.setOut(printStream);
    }

    @Test
    public void givenOutOfBoundsChoice_displayErrorMessageToUser() throws IOException {
        //  Setting our userInput to 5 to test oob case
        userInput = 5;

        // Copy this line of code from the Menu class line 119
        String errorMessageToDisplay = "Our apologies, an unknown problem has occurred machine restarting";

        //  Call implementChoiceMM with our userInput we created to verify menu functionality is working as intended
        testMenus.implementChoiceMM(testVendingMachine, userInput);

        /*
          Call our assertion test to see if the same message is printed out as the default case in the implementChoiceMM method
          NOTE: I am doing systemOutLastInput.size()-2) because the PrintStream will capture "/r/n" at the end, which will always lead to
          a failure test.
          /r - Representation of the special character CR (carriage return), which moves the cursor back to the beginning of the line
          /n - New line
          So by using substring, we can remove those two characters out of our systemOutLastInput string
        */

        Assert.assertEquals(errorMessageToDisplay,
                outputStream.toString().substring(0, outputStream.size()-2));
    }

    @Test
    public void provideCorrectChange_afterTransactionIsCompleted_quartersDimesNicklesTest() throws IOException {

        //  This code is what is essentially being inputted in to System.In
        //  We are just using menuSelections to make it easier to read
        //  String userInput = "2\n1\n20\n2\nA2\n2\nC3\n3\n3";

        /////////////////////////////////////////////////////////////////////////////////////
        List<String> menuSelections = new ArrayList<>();
        menuSelections.add(String.valueOf(menuSelection.PURCHASE_MENU.ordinal()));
        menuSelections.add(String.valueOf(menuSelection.FEED_MONEY.ordinal()));
        //  You have to type in the amount of cash you want to feed into the vending machine
        menuSelections.add("20");

        //  Example of how to purchase a snack from the vending machine
        //  You MUST call the above code before you can call this method, otherwise it will not work properly
        menuSelections.add(String.valueOf(menuSelection.PURCHASE_SNACK.ordinal()));
        menuSelections.add("A2");
        /////////////////////////////////////////////////////////////////////////////

        menuSelections.add(String.valueOf(menuSelection.PURCHASE_SNACK.ordinal()));
        menuSelections.add("C3");

        menuSelections.add(String.valueOf(menuSelection.FINISH_TRANSACTION.ordinal()));
        menuSelections.add(String.valueOf(menuSelection.EXIT.ordinal()));

        menuNavigator(menuSelections);
        /////////////////////////////////////////////////////////////////////////////////////

        //  String we are using for our asser test case
        String coinsDispensed = "\n Quarters: " + "68" + " \n Dimes: " + "0" + "\n Nickels: " + "1" + "\n";

        //  Running a mock test of our application by directly calling our application here.
        Application.main(new String[0]);

        //  Capturing EVERYTHING that was printed to the user
        String outputSteam = outputStream.toString();

        //  A simple check to see if we got exactly what we wanted out of the application
        //  Checking to see if the String we are looking for did appear in our application
        Assert.assertTrue(outputSteam.contains(coinsDispensed));
    }

    @Test
    @DisplayName("Displaying the VendingMachine information")
    public void happytrails_vendingMachineDisplayOption_displayVendingMachine() throws IOException {

        //  YOU CAN STILL USE THIS WAY TO NAVIGATE THE MENUS: (String userInput = "1\n3";)
        //  It will do the same thing as below
        /////////////////////////////////////////////////////////////////////////////////////
        List<String> menuSelections = new ArrayList<>();
        menuSelections.add(String.valueOf(menuSelection.DISPLAY_VENDING_MACHINE_ITEMS.ordinal()));
        menuSelections.add(String.valueOf(menuSelection.EXIT.ordinal()));
        menuNavigator(menuSelections);
        /////////////////////////////////////////////////////////////////////////////////////

        //  String we are using for our assert test case
        String vendingMachineData = testVendingMachine.toString();

        //  Running a mock test for input by running the application main itself
        Application.main(new String[0]);

        //  Capturing EVERYTHING that was printed to the user
        String outputSteam = outputStream.toString();

        // Using Strings contains method, we are checking to see in the output stream
        //  if the vending machine information was displayed to the user
        boolean isVendingMachineBeingDisplayed = outputSteam.contains(vendingMachineData);

        //  Will fail if we didn't display the vending machine, true if we did
        Assert.assertTrue(isVendingMachineBeingDisplayed);
    }

    /**
     * Helper test method that queues up a List of Strings that will be used
     * to mimic user input when the application is requesting so.
     * @param menuSelections    A list of commands that the application will follow in FIFO.
     */
    public void menuNavigator(List<String> menuSelections) {
        StringBuilder navigationInstructions = new StringBuilder();
        for(int i = 0; i < menuSelections.size(); i++) {

            if(menuSelections.get(i).equals(String.valueOf(menuSelection.FINISH_TRANSACTION.ordinal()) ) ){
                navigationInstructions.append(String.valueOf(menuSelection.EXIT.ordinal())).append("\n");
            }
            else if(menuSelections.get(i).equals(String.valueOf(menuSelection.FEED_MONEY.ordinal()) )) {
                navigationInstructions.append(String.valueOf(menuSelection.DISPLAY_VENDING_MACHINE_ITEMS.ordinal())).append("\n");
            }else if(menuSelections.get(i).equals(String.valueOf(menuSelection.PURCHASE_SNACK.ordinal()) )) {
                navigationInstructions.append(String.valueOf(menuSelection.PURCHASE_MENU.ordinal())).append("\n");
            }
            else{
                navigationInstructions.append(String.valueOf(menuSelections.get(i)) ).append("\n");
            }
        }

        //  Setting up our inputstream to feed these inputs into the application.
        InputStream systemIn = new ByteArrayInputStream(navigationInstructions.toString().getBytes());

        //  Setting System.In to use our Inputstream over the default "stdin" stream.
        System.setIn(systemIn);
    }

    //Testing feedMoney with a negative amount
    @Test
    @DisplayName("Displays error message if given a negative amount")
    public void givenNegativeAmount_whenFeedingMoney_willDisplayErrorMessage() throws IOException {
        List<String> menuSelections = new ArrayList<>();
        menuSelections.add(String.valueOf(menuSelection.PURCHASE_MENU.ordinal()));
        menuSelections.add(String.valueOf(menuSelection.FEED_MONEY.ordinal()));
        menuSelections.add("-5");
        menuSelections.add(String.valueOf(menuSelection.FINISH_TRANSACTION.ordinal()));
        menuSelections.add(String.valueOf(menuSelection.EXIT.ordinal()));

        menuNavigator(menuSelections);

        String expectedResult = "Please feed a monetary amount that is a positive value... try again";
        Application.main(new String[0]);
        String outputSteam = outputStream.toString();

        Assert.assertTrue(outputSteam.contains(expectedResult));
    }

    //Testing feedMoney with a non-whole number
    @Test
    @DisplayName("Displays error message if given a non-whole amount")
    public void givenDecimalPoint_whenFeedingMoney_willDisplayErrorMessage() throws IOException {
        List<String> menuSelections = new ArrayList<>();
        menuSelections.add(String.valueOf(menuSelection.PURCHASE_MENU.ordinal()));
        menuSelections.add(String.valueOf(menuSelection.FEED_MONEY.ordinal()));
        menuSelections.add("23.45");
        menuSelections.add(String.valueOf(menuSelection.FINISH_TRANSACTION.ordinal()));
        menuSelections.add(String.valueOf(menuSelection.EXIT.ordinal()));

        menuNavigator(menuSelections);

        String expectedResult = "\n Our apologies, you may not have entered a whole dollar amount, please try again. \n";
        Application.main(new String[0]);
        String outputSteam = outputStream.toString();

        Assert.assertTrue(outputSteam.contains(expectedResult));
    }
}