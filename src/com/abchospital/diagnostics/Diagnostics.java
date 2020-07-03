package com.abchospital.diagnostics;

public class Diagnostics {
	private int testId;
	private String name;
	private int charge;
	public Diagnostics(String name, int charge) {
		super();
		this.name = name;
		this.charge = charge;
	}
	public Diagnostics(int testId, String name, int charge) {
		super();
		this.testId = testId;
		this.name = name;
		this.charge = charge;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}

}
