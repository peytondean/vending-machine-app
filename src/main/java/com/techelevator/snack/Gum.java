package com.techelevator.snack;

/**
 * Gum is a subclass of Snack.
 * It is one of the items that will be available for purchase
 * within the VendingMachine object
 */
public class Gum extends Snack {

    // Making the dispense message here just if it ever hypothetically needed to be changed
    private static final String DISPENSE_MESSAGE = "Chew Chew, Yum!";

    @Override
    public String dispenseMessage() {
        return DISPENSE_MESSAGE;
    }
}
