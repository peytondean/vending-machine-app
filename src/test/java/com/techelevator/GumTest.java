package com.techelevator;

import com.techelevator.snack.Gum;
import com.techelevator.snack.Snack;
import org.junit.Assert;
import org.junit.Test;

public class GumTest {

    //Just in case
    @Test
    public void displayCorrectDispenseMessage() {
        Snack gum = new Gum();
        String actualResult = gum.dispenseMessage();
        String expectedResult = "Chew Chew, Yum!";

        Assert.assertEquals(expectedResult, actualResult);
    }
}
