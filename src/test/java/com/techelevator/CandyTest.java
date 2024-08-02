package com.techelevator;

import com.techelevator.snack.Candy;
import com.techelevator.snack.Snack;
import org.junit.Assert;
import org.junit.Test;

public class CandyTest {

    //Just in case
    @Test
    public void displayCorrectDispenseMessage() {
        Snack candy = new Candy();
        String actualResult = candy.dispenseMessage();
        String expectedResult = "Munch Munch, Yum!";

        Assert.assertEquals(expectedResult, actualResult);
    }


}
