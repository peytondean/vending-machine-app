package com.techelevator;

import com.techelevator.snack.Drink;
import com.techelevator.snack.Snack;
import org.junit.Assert;
import org.junit.Test;

public class DrinkTest {

    //Just in case
    @Test
    public void displayCorrectDispenseMessage() {
        Snack drink = new Drink();
        String actualResult = drink.dispenseMessage();
        String expectedResult = "Glug Glug, Yum!";

        Assert.assertEquals(expectedResult, actualResult);
    }
}
