package com.techelevator.snack;

/**
 * Abstract class for our Chip, Drink, Gum and Candy objects to derive from should contain name and type
 */
public abstract class Snack {
    private SnackType snackType;

    public enum SnackType {
        CANDY,
        CHIP,
        DRINK,
        GUM
    }

    // Every snack will have these
    private String name;
    private int quantity;

    // Method that all snacks need to contain
    public abstract String dispenseMessage();

    //From what I understand, if I put getters and setters here without them being abstract all we won't need getters and setters in the child classes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public final SnackType getSnackType() {
        return snackType;
    }

    public void setSnackType(SnackType snackType) {
        this.snackType = snackType;
    }
}
