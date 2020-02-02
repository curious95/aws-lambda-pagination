package com.amazonaws.lambda_template;

public class Paginator {
	int count;
	int step;
	int index;
	String last_shop_id;

	public String getLast_shop_id() {
		return last_shop_id;
	}

	public void setLast_shop_id(String last_shop_id) {
		this.last_shop_id = last_shop_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Paginator(int count, int step, int index, String last_shop_id) {
		super();
		this.count = count;
		this.step = step;
		this.index = index;
		this.last_shop_id = last_shop_id;
	}
	
	public Paginator() {
		
	}
	

}
