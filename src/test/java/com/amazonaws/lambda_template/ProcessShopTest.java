package com.amazonaws.lambda_template;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ProcessShopTest {

    private static Shop shop;
    private static Paginator paginator;


    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
    	paginator = new Paginator(1,2,3,"None");
    	shop = new Shop("23123", 2, paginator);
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
        
        Map<String, Object> output = handler.handleRequest(shop, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Shop with ID "+shop.getShop_id()+ " got a score of "+shop.getOperation_number(), output);
    }
}
