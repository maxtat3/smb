package edu.sintez.smsmultibanking.app.builder;

/**
 * Created by andrew on 7/11/14.
 */
public interface AbstractOperations {

    /**
     * Метод создает строку - запрос баланса
     * @param model - данные для построения запроса
     * @return сформированные запрос
     */
    String getBalance(BankDataModel model);

    /**
     * Метод создает строку - запрос на блокировку карту
     * @param model - данные для построения запроса
     * @return сформированные запрос
     */
    String blockCard(BankDataModel model);

    /**
     * Метод создает строку - запрос выписка
     * @param model - данные для построения запроса
     * @return сформированные запрос
     */
    String getReport(BankDataModel model);

    /**
     * Метод создает строку - запрос перевода денег на карту
     * @param model - данные для построения запроса
     * @return сформированные запрос
     */
    String moneyTransfer(BankDataModel model);

    /**
     * Метод создает строку - запрос на разблокирование карты
     * @param model - данные для построения запроса
     * @return сформированные запрос
     */
    String unblockCard(BankDataModel model);

    /**
     * Метод создает строку - запрос пополнения телефона
     * @param model - данные для построения запроса
     * @return сформированные запрос
     */
    String mobileTransfer(BankDataModel model);

    /**
     * Метод возвращает номер банка для отправки смс
     * @return номер банка
     */
    String getBankPhone();
}
