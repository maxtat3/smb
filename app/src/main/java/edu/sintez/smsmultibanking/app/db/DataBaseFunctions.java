package edu.sintez.smsmultibanking.app.db;

import java.util.List;

/**
 * Created by Max on 17.07.2014.
 */
public interface DataBaseFunctions {

	/**
	 * Метод добавляет карту в бд
	 * @param cardNumber - номер карты (последние 4 цифры карты)
	 * @param bankName - имя карты
	 * @param bankName - имя банка
	 * @return - результат об успешном/не успешном добавлении карты в бд
	 */
	boolean addCard(int cardNumber, String cardName, String bankName);


	/**
	 * Метод удаляет карту из бд
	 * @param idCard - ID карты
	 * @return - результат об успешном/не успешном удалении карты из бд
	 */
	boolean deleteCard(int idCard);


	/**
	 * Метод производит изменение данных в карте
	 * @param idCard  - ID карты
	 * @param cardNumber - номер карты
	 * @param cardName - имя карты
	 * @param bankName - имя банка
	 * @return - результат об успешном/не успешном обновлении
	 */
	boolean changeCard(int idCard, String cardNumber, String cardName, String bankName);


	/**
	 * Метод позволяет получить историю выполненных действий с конкретной картой.
	 * Выбор конкретной карты осуществляется по ID карты
	 * @param idCard  - ID карты
	 * @return - коллекция выполненных действий с конкретной картой.
	 */
	List<String> getHistory(int idCard);


	/**
	 * Метод добавляет новую строку в историю.
	 * Вызывается при каждом новом осуществлении какого-либо действия с конкретной картой.
	 * @param idCard  - ID карты
	 * @param msg - сообщение добавляемое в историю о каждом новом выполненном действии.
	 * @return
	 */
	boolean addToHistory(int idCard, String msg);

}
