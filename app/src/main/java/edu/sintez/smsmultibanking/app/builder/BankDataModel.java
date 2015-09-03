package edu.sintez.smsmultibanking.app.builder;;import edu.sintez.smsmultibanking.app.AllActions;

public class BankDataModel {

	/**
	 * Валюта перевода
	 */
	public static final String UAH = "UAH";
	public static final String USD = "USD";
	public static final String EUR = "EUR";
	public static final String RUS = "RUS";


	private String cardNumber = null;                   // 4 цифры номера карты, т.к. для укрексимбанка нужны первые и последние 4 цифры
	private int paySum = 0;                          //сумма перевода на карту или телефон
	private String transferNumber = null;               //номер карты или телефона получателя
	private String accessCode = null;                   //код доступа, необходим для pumb, aval, kredo, marfin, profin
	private String transferCurrency = null;   //валюта перевода, нужно указывать для privat и unicredit
	private long accountNumber = 0;                  //номер счета aval
	private long transactionNumber = 0;              //номер транзации, нужен для aval
	private String cardPseudonym = null;                //псевдоним карты, нужен для eximbank


	public BankDataModel() {
	}


    public BankDataModel(String cardNumber, int paySum, String transferNumber, String accessCode, String transferCurrency, long accountNumber, long transactionNumber, String cardPseudonym) {
        this.cardNumber = cardNumber;
        this.paySum = paySum;
        this.transferNumber = transferNumber;
        this.accessCode = accessCode;
        this.transferCurrency = transferCurrency;
        this.accountNumber = accountNumber;
        this.transactionNumber = transactionNumber;
        this.cardPseudonym = cardPseudonym;
    }

	public BankDataModel(String cardNumber, int paySum, String transferNumber) {
		this.cardNumber = cardNumber;
		this.paySum = paySum;
		this.transferNumber = transferNumber;
	}

	public BankDataModel(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public BankDataModel(int paySum, String transferCurrency, String cardNumber, String transferNumber) {
		this.paySum = paySum;
		this.transferCurrency = transferCurrency;
		this.cardNumber = cardNumber;
		this.transferNumber = transferNumber;
	}


	/**
     * Метод возвращает номер карты (16 цифр)
     * @return номер карты
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Метод возвращает псевдоним карты
     * @return псевдоним карты
     */
    public String getCardPseudonym() {
        return cardPseudonym;
    }

    /**
     * Метод возвращает номер транзакции
     * @return номер транзакции
     */

    public long getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * Метод возвращает количество передаваемых денег
     * @return количество передаваемых денег
     */
    public int getPaySum() {
        return paySum;
    }

    /**
     * Метод возвращает код доступа
     * @return код доступа
     */
    public String getAccessCode() {
        return accessCode;
    }

    /**
     * Метод возвращает номер получателя (номер карты или телефон)
     * @return номер получателя
     */
    public String getTransferNumber() {
        return transferNumber;
    }

    /**
     * Метод возвращает тип валюты, которая будет использоваться при переводе средств на карту
     * @return возвращает тип валюты
     */
    public String getTransferCurrency() {
        return transferCurrency;
    }

    /**
     * Метод возвращает номер счета
     * @return номер счета
     */
    public long getAccountNumber() {
        return accountNumber;
    }


}



