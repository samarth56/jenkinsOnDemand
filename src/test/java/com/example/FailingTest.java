package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FailingTest {

    @Test
    public void failTest() {
        System.out.println("Failing TestNG test");
        Assert.assertTrue(false, "Intentional failure");
    }
}
