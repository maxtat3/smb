package edu.sintez.smsmultibanking.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by Михаил on 20.06.2014.
 */
public class BankDB extends SQLiteOpenHelper {

	private static BankDB instance = null;

	private static final String DATABASE_NAME = "SMS_Banking.db";
	private static final int DATABASE_VERSION = 1;

	public static final String MatrixX = "MatrixX";
	public static final String BLOCK = "BLOCK";
	public static final String UNBLOCK = "UNBLOCK";
	public static final String MOBI = "MOBI";
	public static final String SEND = "SEND";

	public static final String TABLE_CARDS = "CARDS";
	public static final String ID = "ID";
	public static final String BANK = "BANK";
	public static final String NUMBER = "NUMBER";
	public static final String NAME = "NAME";
	public static final String BALANCE = "BALANCE";
	public static final String LAST_DATE = "LAST_DATE";

	public static final String TABLE_HISTORY = "HISTORY";
	public static final String ID_CARD = "ID_CARD";
	public static final String ACTION = "ACTION";

	public static final String TABLE_BANKS = "BANKS";
	public static final String INFO = "INFO";

	private static final String SQL_CREATE_MatrixX =
			"CREATE TABLE " + MatrixX +
					" ("+ BANK + " VARCHAR(30) PRIMARY KEY, "+
					BALANCE + " INTEGER NOT NULL, " +
					BLOCK + " INTEGER NOT NULL, " +
					UNBLOCK + " INTEGER NOT NULL, " +
					MOBI + " INTEGER NOT NULL, " +
					SEND + " INTEGER NOT NULL); ";

	private static final String SQL_CREATE_CARDS =
			"CREATE TABLE " + TABLE_CARDS +" ("+
					ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
					BANK + " VARCHAR(30) NOT NULL, " +
					NUMBER + " VARCHAR(6) NOT NULL, " +
					NAME+" VARCHAR(50) NOT NULL," +
					BALANCE + " REAL, " +
					LAST_DATE+" VARCHAR(10)); ";

	private static final String SQL_CREATE_BANKS =
			"CREATE TABLE " + TABLE_BANKS +
					" ("+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
					NAME+" VARCHAR(30) NOT NULL," +
					INFO+" VARCHAR(500) NOT NULL); ";

	private static final String SQL_CREATE_HISTORY =
			"CREATE TABLE " + TABLE_HISTORY +" ("+
					ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
					ID_CARD + " INTEGER NOT NULL, " +
					ACTION + " VARCHAR(50) NOT NULL); ";

	private static final String SQL_INSERT_INTO_BANKS = "INSERT INTO "+ TABLE_BANKS + " " +
			"("+ ID +", "+ NAME +", "+INFO +")" +
			"VALUES (1, 'ПриватБанк', ''), " +
			"(2, 'Укрсоцбанк(UniCredit)', '')," +
			"(3, 'УкрСиббанк', '')," +
			"(4, 'Укрэксимбанк', '')," +
			"(5, 'Аваль', '')," +
			"(6, 'Правэкс-Банк', '')," +
			"(7, 'ПУМБ', '')," +
			"(8, 'Кредобанк', '')," +
			"(9, 'Марфин Банк', '')," +
			"(10, 'Профин банк', '')," +
			"(11, 'Укринбанк', '')," +
			"(12, 'Укргазбанк', '');";

	private static final String SQL_INSERT_INTO_MatrixX = "INSERT INTO " + MatrixX + " " +
			"("+ BANK +", "+ BALANCE +", "+BLOCK +", "+UNBLOCK + ", "+MOBI + ", "+SEND +")" +
			"VALUES ('ПриватБанк', 1,  1,  1,   1,   1), " +
			"('Укрсоцбанк(UniCredit)', 1,   1,  0,  0,  1)," +
			"('УкрСиббанк', 1,   1,  1,   0,  0)," +
			"('Укрэксимбанк', 1,   1,  0,  0,  0)," +
			"('Аваль', 1,   1,  0,  0,  0)," +
			"('Правэкс-Банк', 1,   1,  0,  0,  0)," +
			"('ПУМБ', 1,  1,  0,  0,  0)," +
			"('Кредобанк', 1,  0, 0,  0,  0)," +
			"('Марфин Банк', 1,  0, 0,  0,  0)," +
			"('Профин банк', 1,  1,  0,  0,  0)," +
			"('Укринбанк', 0,  0, 0,  1,   0)," +
			"('Укргазбанк', 1,   1,  1,   0,  0);";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ";

	private BankDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static synchronized BankDB getInstance(Context context) {
		if (instance == null) {
			instance = new BankDB(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(SQL_CREATE_CARDS);
		sqLiteDatabase.execSQL(SQL_CREATE_BANKS);
		sqLiteDatabase.execSQL(SQL_CREATE_HISTORY);
		sqLiteDatabase.execSQL(SQL_CREATE_MatrixX);
		sqLiteDatabase.execSQL(SQL_INSERT_INTO_BANKS);
		sqLiteDatabase.execSQL(SQL_INSERT_INTO_MatrixX);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
		// Удаляем предыдущую таблицу при апгрейде
		sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES + TABLE_CARDS);
		sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES + TABLE_BANKS);
		sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES + MatrixX);
		// Создаём новый экземпляр таблицы
		onCreate(sqLiteDatabase);
	}

	@Override
	public void onDowngrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
		// Удаляем предыдущую таблицу
		sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES + TABLE_CARDS);
		sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES + TABLE_BANKS);
		sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES + MatrixX);
		// Создаём новый экземпляр таблицы
		onCreate(sqLiteDatabase);
	}
}