package com.amazonaws.lambda_template;

import com.amazonaws.lambda_template.Shop;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ProcessShop implements RequestHandler<Shop, String> {
	

    public String handleRequest(Shop shop, Context context) {
    	String shop_id = System.getenv("SHOP_ID");

        context.getLogger().log("Input: " + shop.shop_id +" "+ shop.operation_number);
        context.getLogger().log("ENV_VAR: " + shop_id);

        // TODO: implement your handler
        return "Shop with ID "+shop.shop_id+ " got a score of "+shop.operation_number;
    }

}
