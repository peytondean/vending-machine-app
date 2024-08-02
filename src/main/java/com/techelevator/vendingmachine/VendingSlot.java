package com.techelevator.vendingmachine;

import com.techelevator.exception.ErrorCode;
import com.techelevator.exception.SnackException;
import com.techelevator.snack.*;

/**
 * Holds a Snack object, how many snacks are currently here and the price
 * for the snack
 */
public class VendingSlot {
    private static final int OUT_OF_STOCK = 0;
    private static final String OOS_DISPLAY_MESSAGE = "OUT OF STOCK";
    private static final int MAXIMUM_QUANTITY = 5;
    private String slotLocation;
    private String price;
    private int quantity;
    private Snack consumable = null;

    public VendingSlot(String slotLocation, String snackName, String price, String snackType) {
        this.slotLocation = slotLocation;
        this.price = price;
        this.quantity = MAXIMUM_QUANTITY;
        initializeVendingSnack(snackType, snackName);
    }

    /**
     * Helper method to initialize the Snack object within our properties.
     *
     * @param snackType String object that will be used to check to see if this snack exists in this vending machine.
     * @param snackName String object that will be used for naming the Snack object.
     */
    private void initializeVendingSnack(String snackType, String snackName) {
        /*
          This switch expression is used to determine what Snack subclass object to create.
          The switch case is going directly into the Snack class and getting the SnackType enum created in there
          Once accessed, we can call valueOf to return the enum constant that matches with the snackType string
          passed in.
        */
        Snack.SnackType snack = Snack.SnackType.valueOf(snackType.toUpperCase());
        switch (snack) {
            case GUM: {
                consumable = new Gum();
                break;
            }
            case CHIP: {
                consumable = new Chip();
                break;
            }
            case DRINK: {
                consumable = new Drink();
                break;
            }
            case CANDY: {
                consumable = new Candy();
                break;
            }
            default: {
                throw new SnackException("SnackType enum does not have this constant implemented.",
                        ErrorCode.INVALID_SNACK_CREATION_ATTEMPT);
            }
        }
        //  Since all the cases need to case this method, I left it out
        //  until the very end to do so
        consumable.setSnackType(snack);
        consumable.setName(snackName);
    }

    /**
     * {@inheritDoc}
     * Checks to see if there is any snacks left in stock here before
     * building a String object to return of the vending slot data.
     * in order to display all the objects that are currently in our vending machine
     *
     * @return the slot location, quantity of the snacks remaining, name of the snack, price, and the type of snack in this order
     */
    @Override
    public String toString() {
        String quantityDisplay = "";
        //  We are checking to make sure that we cover the scenario where
        //  the vending machine is out of stock of the snack
        if (quantity <= OUT_OF_STOCK)
            quantityDisplay = OOS_DISPLAY_MESSAGE;
        else
            quantityDisplay = Integer.toString(quantity);

        return slotLocation + " | " + consumable.getName() + " | " + price + " | " + consumable.getSnackType().toString() + " | " + quantityDisplay + "\n";
    }

    /**
     * Decrements the quantity of the snacks in the vending slot
     */
    protected void dispenseSnack() {
        if (quantity > OUT_OF_STOCK) {
            quantity--;
        }
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public void setSlotLocation(String slotLocation) {
        this.slotLocation = slotLocation;
    }

    public final String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public final int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity >= 0)
            this.quantity = quantity;
    }

    public final Snack getSnack() {
        return consumable;
    }

    public final int getMaxQuantity() {
        return MAXIMUM_QUANTITY;
    }
}
