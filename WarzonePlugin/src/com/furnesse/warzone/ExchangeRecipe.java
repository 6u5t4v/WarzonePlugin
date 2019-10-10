package com.furnesse.warzone;

public class ExchangeRecipe {

	private CustomItem exchangeFrom;
	private CustomItem exchangeInto;
	private int fromAmount;
	private int intoAmount;
	
	public ExchangeRecipe (CustomItem exchangeFrom, CustomItem exchangeInto, int fromAmount, int intoAmount) {
		this.exchangeFrom = exchangeFrom;
		this.exchangeInto = exchangeInto;
		this.fromAmount = fromAmount;
		this.intoAmount = intoAmount;
	}

	public int getIntoAmount() {
		return intoAmount;
	}

	public void setIntoAmount(int intoAmount) {
		this.intoAmount = intoAmount;
	}

	public CustomItem getExchangeFrom() {
		return exchangeFrom;
	}

	public void setExchangeFrom(CustomItem exchangeFrom) {
		this.exchangeFrom = exchangeFrom;
	}

	public CustomItem getExchangeInto() {
		return exchangeInto;
	}

	public void setExchangeInto(CustomItem exchangeInto) {
		this.exchangeInto = exchangeInto;
	}

	public int getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(int fromAmount) {
		this.fromAmount = fromAmount;
	}
	
	
}
