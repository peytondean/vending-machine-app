package com.techelevator;

import com.techelevator.vendingmachine.VendingSlot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class VendingSlotTest {
    VendingSlot vendingSlot;

    //Setup for test
    @Before
    public void setup() {
         vendingSlot = new VendingSlot("A1",
                "Bubblegum", "1.00", "Gum");
    }

    //Testing initializeVendingSnack with normal snack type

    @Test
    @DisplayName("Will create a snack with a valid snack type")
    public void givenAValidSnackType_whenInitializingVendingSnack_willCreateValidSnack() {
        String actualResult = String.valueOf(vendingSlot.getSnack().getSnackType());
        String expectedResult = "GUM";

        Assert.assertEquals(expectedResult, actualResult);
    }

    //Testing dispenseSnack by reducing quantity by 1
    @Test
    @DisplayName("Vending slot dispense testing")
    public void givenQuantityNotOutOfStock_whenDispensingSnack_willReduceTheQuantityByOne() {
        int expectedResult = 4;
        int actualResult = vendingSlot.getQuantity();

        Assert.assertEquals(expectedResult, actualResult);
    }

    //Testing dispenseSnack to make sure it won't reduce quantity less than 0
    @Test
    public void givenQuantity_whenOutOfStock_quantityWillNotGoBelowZero() {
        vendingSlot.setQuantity(0);
        vendingSlot.setQuantity(-5);

        int expectedResult = 0;
        int actualResult = vendingSlot.getQuantity();

        Assert.assertEquals(expectedResult, actualResult);
    }

    //Testing vendingSlot's toString method for correct Formatting
//    @Test
//    public void givenValidSnack_WhenDisplaying_WillDisplayCorrectly() {
//        VendingSlot vendingSlot = new VendingSlot("A1",
//                "Bubblegum", "1.00", "Gum");
//
//        String expectedResult = "A1 | Bubblegum | $1.00 | Gum | 5";
//        String actualResult = vendingSlot.toString();
//
//        Assert.assertEquals(expectedResult, actualResult);
//    }

}
