package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class fail {

    @Test
    public void failTest() {
        System.out.println("Failing TestNG test");
        Assert.assertTrue(false, "Intentional failure");
    }
}
