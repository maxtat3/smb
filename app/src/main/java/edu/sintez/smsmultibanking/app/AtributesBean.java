package edu.sintez.smsmultibanking.app;

import edu.sintez.smsmultibanking.app.builder.AbstractOperations;

/**
 * Created by Max on 14.07.2014.
 */
public class AtributesBean {

	private static AtributesBean instance;

	public synchronized static AtributesBean getInstance(){
		if (instance == null){
			instance = new AtributesBean();
		}
		return instance;
	}

	private AtributesBean() {
	}



	private String selAction;  //хранит выбранное действие пользователя (из первого окна)
	private int idCard; 		//хранит id выбранной карты
	private String selCardName; //хранит выбранное имя карты (от выбранного банка)
	private String selBankName; //хранит выбранное имя банка
	private AbstractOperations abstractOperation;



	public AbstractOperations getAbstractOperation() {
		return abstractOperation;
	}

	public void setAbstractOperation(AbstractOperations abstractOperation) {
		this.abstractOperation = abstractOperation;
	}



	public String getSelAction() {
		return selAction;
	}

	public void setSelAction(String selAction) {
		this.selAction = selAction;
	}



	public String getSelCardName() {
		return selCardName;
	}

	public void setSelCardName(String USER_CARD_SELECTED) {
		this.selCardName = USER_CARD_SELECTED;
	}



	public String getSelBankName() {
		return selBankName;
	}

	public void setSelBankName(String selBankName) {
		this.selBankName = selBankName;
	}



	public int getIdCard() {
		return idCard;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}
}