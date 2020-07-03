package com.abchospital.pharmacy;

public class Medicines {
	private int medicineId;
	private String name;
	private int quantityIssued;
	public Medicines(String name,int medicineId , int rate, int availableQuantity) {
		super();
		this.medicineId = medicineId;
		this.name = name;
		this.rate = rate;
		this.availableQuantity = availableQuantity;
	}
	public Medicines(int medicineId, String name, int quantityIssued, int rate) {
		super();
		this.medicineId = medicineId;
		this.name = name;
		this.quantityIssued = quantityIssued;
		this.rate = rate;
	}
	private int rate;
	private int availableQuantity;
	public int getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantityIssued() {
		return quantityIssued;
	}
	public void setQuantityIssued(int quantityIssued) {
		this.quantityIssued = quantityIssued;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public int getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
}
