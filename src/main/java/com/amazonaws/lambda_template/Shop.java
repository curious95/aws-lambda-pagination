package com.amazonaws.lambda_template;

public class Shop {
	String shop_id;
	int operation_number;

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public int getOperation_number() {
		return operation_number;
	}

	public void setOperation_number(int operation_number) {
		this.operation_number = operation_number;
	}

	public Shop(String shop_id, int operation_number) {
		this.shop_id = shop_id;
		this.operation_number = operation_number;
	}

	public Shop() {
	}
}
