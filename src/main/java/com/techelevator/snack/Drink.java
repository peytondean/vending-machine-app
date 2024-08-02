package com.techelevator.snack;

/**
 * Drink is a subclass of Snack (we should probably review this and have
 * a beverage subclass?)
 * It is one of the items that will be available for purchase
 * within the VendingMachine object
 */
public class Drink extends Snack {

    // Making the dispense message here just if it ever hypothetically needed to be changed
    private static final String DISPENSE_MESSAGE = "Glug Glug, Yum!";

    @Override
    public String dispenseMessage() {
        return DISPENSE_MESSAGE;
    }
}
