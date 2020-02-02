package com.amazonaws.lambda_template;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.JsonObject;

public class ProcessShop implements RequestHandler<Shop, Map<String, Object>> {

	private DynamoDB dynamoDb;
	private String DYNAMODB_TABLE_NAME = "shop_processing_table";
	private Regions REGION = Regions.US_EAST_2;

	public Map<String, Object> handleRequest(Shop shop, Context context) {

		context.getLogger().log("Input: " + shop.getShop_id() + " " + shop.getOperation_number());
		context.getLogger().log("Input: " + shop.getPaginator().getCount() + " " + shop.getPaginator().getIndex());

		// Initializing the dynamodb client
		this.initDynamoDbClient();

		// Checks if shop is currently being processed
		Boolean do_read = readData(shop, context);

		// Message and response objects
		JsonObject response_obj = new JsonObject();
		JsonObject message_obj = new JsonObject();
		Boolean _continue = true;

		// If not being processed does the operation other wise notifies the user
		if ((do_read == false && shop.getPaginator().getLast_shop_id().equals("None"))
				|| (do_read == true && shop.getPaginator().getLast_shop_id().equals(shop.getShop_id()))) {
			try {
				// Creates a record in the dynamodb
				persistData(shop);

				// Processing and generating messages
				// TODO: implement your handler
				Thread.sleep(5000);
				String message = "Shop with ID " + shop.getShop_id() + " got a score of " + shop.getOperation_number();
				message_obj.addProperty("message", message);
				shop.paginator.setIndex(shop.getPaginator().getIndex() + shop.getPaginator().getStep());
				_continue = shop.getPaginator().getIndex() == shop.getOperation_number();
			} catch (InterruptedException e) {
				context.getLogger().log(e.getMessage());
			}

			// Deleting the record
			if (_continue) {
				deleteData(shop, context);
			}

		} else {
			String message = "Shop with ID " + shop.getShop_id() + "is already being processed";
			message_obj.addProperty("message", message);
			_continue = true;
		}

		// Response creation for REST API
		JsonObject header_obj = new JsonObject();
		header_obj.addProperty("Content-Type", "application/json");
		response_obj.add("body", message_obj);
		response_obj.addProperty("statusCode", 200);
		response_obj.add("headers", header_obj);
		response_obj.addProperty("step", shop.getPaginator().getStep());
		response_obj.addProperty("count", shop.getPaginator().getCount());
		response_obj.addProperty("index", shop.getPaginator().getIndex());

		response_obj.addProperty("continue", !_continue);
		context.getLogger().log(response_obj.toString());

		Map<String, Object> output = new HashMap<>();
		output.put("step", shop.getPaginator().getStep());
		output.put("count", shop.getPaginator().getCount());
		output.put("index", shop.getPaginator().getIndex());
		output.put("last_shop_id", shop.getShop_id());
		output.put("continue", !_continue);

		return output;
	}

	// Function to put record in dynamodb
	private PutItemOutcome persistData(Shop shop) throws ConditionalCheckFailedException {

		return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
				.putItem(new PutItemSpec().withItem(new Item().withString("shop_id", shop.getShop_id())
						.withString("operation_number", "" + shop.getOperation_number())));
	}

	// Function to read record in dynamodb
	private Boolean readData(Shop shop, Context context) throws ConditionalCheckFailedException {

		Item shops = this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
				.getItemOutcome(new GetItemSpec().withPrimaryKey("shop_id", shop.getShop_id())).getItem();

		try {
			context.getLogger().log(shops.getString("shop_id"));
			if (shops.get("shop_id").equals(shop.getShop_id())) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
	}

	// Function to delete record in dynamodb
	private Boolean deleteData(Shop shop, Context context) throws ConditionalCheckFailedException {

		Item deleted_item = this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
				.deleteItem(new DeleteItemSpec().withPrimaryKey("shop_id", shop.getShop_id())).getItem();

		// context.getLogger().log(deleted_item.getString("shop_id"));

		return false;
	}

	// Function to initialize dynamodb
	private void initDynamoDbClient() {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setRegion(Region.getRegion(REGION));
		this.dynamoDb = new DynamoDB(client);
	}

}
