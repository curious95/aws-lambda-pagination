package com.amazonaws.lambda_template;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda_template.ProcessShop;
import com.amazonaws.lambda_template.Shop;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ProcessShopTest {

    private static Shop shop;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        shop = new Shop("23123", 2);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testProcessShopHandler() {
        ProcessShop handler = new ProcessShop();
        Context ctx = createContext();
        
        String output = handler.handleRequest(shop, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Shop with ID "+shop.shop_id+ " got a score of "+shop.operation_number, output);
    }
}
