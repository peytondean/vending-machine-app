package com.techelevator.snack;

/**
 * Candy is a subclass of Snack.
 * It is one of the items that will be available for purchase
 * within the VendingMachine object
 */
public class Candy extends Snack {

    // Making the dispense message here just if it ever hypothetically needed to be changed
    private static final String DISPENSE_MESSAGE = "Munch Munch, Yum!";

    @Override
    public String dispenseMessage() {
        return DISPENSE_MESSAGE;
    }

}
