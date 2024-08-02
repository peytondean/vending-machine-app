package com.techelevator;

import com.techelevator.vendingmachine.VendingMachine;
import com.techelevator.vendingmachine.VendingSlot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VendingMachineTest {

    VendingMachine testVendingMachine;

    //Set up new vending machine
    @Before
    public void init() {
        testVendingMachine = new VendingMachine();
        testVendingMachine.loadInVendingMachineData();
    }

    // Testing loadInVendingMachineData with invalid file path
//    @Test
//    public void givenInvalidFile_whenLoadingInVendingMachineData_throwException() {
//        String invalidFilePath = "invalidFile.txt";
//
//        testVendingMachine.loadInVendingMachineData(invalidFilePath);
//        Assert.fail();
//
//
//    }

    //testing what happens when you enter an empty file
//    @Test
//    public void givenEmptyFile_whenLoadingInVendingMachineData_WillNotAllowEmptyFile() {
//        // Create a temporary empty file for testing
//        File emptyFile = createEmptyFile();
//
//        // Call the method with the path to the empty file
//        testVendingMachine.loadInVendingMachineData(emptyFile.getPath());
//
//        // check to see if loadInVendingMachineData will accept an empty file
//
//
//    }
//
//    // Helper method to create an empty file for testing
//    private File createEmptyFile() {
//        try {
//            // Create a temporary file with an empty content
//            File emptyFile = File.createTempFile("emptyFile", ".txt");
//            emptyFile.deleteOnExit(); // Delete the file when JVM exits
//            return emptyFile;
//        } catch (IOException e) {
//            // Handle any exception occurred during file creation
//            System.err.println(e.getMessage());
//            return null; // Return null in case of failure
//        }
//    }

    // Testing feedMoney with normal amount without logging
    @Test
    public void given5Dollars_whenFeedingMoney_purchaseBalanceWillBe5() throws IOException {
        double expectedResult = 5.0;
        testVendingMachine.feedMoney(5.0, true);
        double actualResult = testVendingMachine.getVendingMachineBalance();

        Assert.assertEquals(expectedResult, actualResult, 0.0);
    }

    // Testing feedMoney with negative amount
    @Test
    public void givenNegativeAmount_whenFeedingMoney_balanceWillBe0() throws IOException {
        double expectedResult = 0;
        testVendingMachine.feedMoney(-5, true);
        double actualResult = testVendingMachine.getVendingMachineBalance();

        Assert.assertEquals(expectedResult, actualResult, 0.0);
    }


    // Testing if dispenseChange gives correct change
    @Test
    public void givenNormalAmountOfMoney_whenDispensingChange_willGiveCorrectChange() throws IOException {
        double amountOfChangeToTakeOut = 12.64;
        testVendingMachine.feedMoney(amountOfChangeToTakeOut, true);
        double expectedChangeGiven = amountOfChangeToTakeOut;
        double actualChangeGiven = testVendingMachine.dispenseChange(true);

        Assert.assertEquals(expectedChangeGiven, actualChangeGiven, 0.0);

    }

    @Test
    @DisplayName("Creating vending slots with valid data")
    public void happyTrails_createVendingMachineWithValidData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //  IT IS NOT A GOOD PRACTICE TO UNIT TEST PRIVATE METHODS
        //  I did this to test out how to do so but you can also attempt it if you wish

        //  Resource used on this topic: https://www.baeldung.com/java-unit-test-private-methods
        String [] testVendingSlotData = {"A5", "C4", "9.99", "Drink"};
        VendingSlot expectedSlot = new VendingSlot("A5", "C4", "9.99", "Drink");

        // Creating a Method Object in order to allow me to test private methods in our code base
        //  The first param is the name of the method and the second is what the method takes in
        Method method = VendingMachine.class.getDeclaredMethod("createVendingSlot", String[].class);
        //  This will toggle private to public for this method Object call
        method.setAccessible(true);

        //  Capturing the results of our method call to use for testing
        VendingSlot testSlot = (VendingSlot) method.invoke(testVendingMachine, (Object) testVendingSlotData);

        Assert.assertEquals(expectedSlot.toString(), testSlot.toString());
    }

    //Testing


}