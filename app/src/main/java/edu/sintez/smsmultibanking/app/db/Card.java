package edu.sintez.smsmultibanking.app.db;



public class Card {

	private int idCard;
	private String numCard;
	private String bankName;
	private String nameCard;

	public Card(int id, String numCard, String bankName, String nameCard) {
		this.idCard = id;
		this.numCard = numCard;
		this.bankName = bankName;
		this.nameCard = nameCard;
	}

	public int getIdCard() {
		return idCard;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

	public String getNumCard() {
		return numCard;
	}

	public void setNumCard(String numCard) {
		this.numCard = numCard;
	}

	public String getNameCard() {
		return nameCard;
	}

	public void setNameCard(String nameCard) {
		this.nameCard = nameCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


}