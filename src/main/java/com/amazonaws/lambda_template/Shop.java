package com.amazonaws.lambda_template;

public class Shop {

	String shop_id;
	Integer operation_number;
	Paginator paginator;

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public Integer getOperation_number() {
		return operation_number;
	}

	public void setOperation_number(Integer operation_number) {
		this.operation_number = operation_number;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}


	public Shop(String shop_id, Integer operation_number, Paginator paginator) {
		this.shop_id = shop_id;
		this.operation_number = operation_number;
		this.paginator = paginator;
	}
	
	public Shop() {
		
	}
	
}
