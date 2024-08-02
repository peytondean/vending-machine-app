package com.techelevator;

import com.techelevator.snack.Chip;
import com.techelevator.snack.Snack;
import org.junit.Assert;
import org.junit.Test;

public class ChipTest {

    //Just in case
    @Test
    public void displayCorrectDispenseMessage() {
        Snack chip = new Chip();
        String actualResult = chip.dispenseMessage();
        String expectedResult = "Crunch Crunch, Yum!";

        Assert.assertEquals(expectedResult, actualResult);
    }
}
