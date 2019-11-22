package com.furnesse.warzone;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class ExchangeRecipe {

	private String name;
	private ItemStack exchangeFrom;
	private ItemStack result;
	private int fromAmount;
	private int resultAmount;
	private List<String> cmds;

	public ExchangeRecipe(String name, ItemStack exchangeFrom, ItemStack result, int fromAmount, int resultAmount,
			List<String> cmds) {
		this.name = name;
		this.exchangeFrom = exchangeFrom;
		this.result = result;
		this.fromAmount = fromAmount;
		this.resultAmount = resultAmount;
		this.cmds = cmds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCmds() {
		return cmds;
	}

	public void setCmds(List<String> cmds) {
		this.cmds = cmds;
	}

	public int getResultAmount() {
		return resultAmount;
	}

	public void setResultAmount(int resultAmount) {
		this.resultAmount = resultAmount;
	}

	public ItemStack getExchangeFrom() {
		return exchangeFrom;
	}

	public void setExchangeFrom(ItemStack exchangeFrom) {
		this.exchangeFrom = exchangeFrom;
	}

	public ItemStack getResult() {
		return result;
	}

	public void setResult(ItemStack result) {
		this.result = result;
	}

	public int getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(int fromAmount) {
		this.fromAmount = fromAmount;
	}

}
