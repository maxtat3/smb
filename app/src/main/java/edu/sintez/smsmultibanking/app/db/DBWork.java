package edu.sintez.smsmultibanking.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Михаил on 18.07.2014.
 */
public class DBWork {


	private static BankDB bdb;
	private static SQLiteDatabase sqdb;
	private static Cursor cursor;

	public static boolean initialized(Context context){
		bdb = BankDB.getInstance(context);
		if (bdb != null)
			return true;
		return false;
	}

	public static List<Card> getCardsFromDB(){
		List<Card> cards = new ArrayList<Card>();
		String query = "SELECT * FROM " + BankDB.TABLE_CARDS;
		sqdb = bdb.getReadableDatabase();
		if (sqdb != null){
			cursor = sqdb.rawQuery(query, null);
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex(BankDB.ID));
				String bank = cursor.getString(cursor.getColumnIndex(BankDB.BANK));
				String number = cursor.getString(cursor.getColumnIndex(BankDB.NUMBER));
				String name = cursor.getString(cursor.getColumnIndex(BankDB.NAME));
				cards.add(new Card(id, number, bank, name));
			}
			cursor.close();
			sqdb.close();
		}
		return cards;
	}

	public static boolean addCard(String numCard, String nameCard, String bankName){
		if (numCard.length() >= 4 && nameCard.length() >= 1 && bankName.length() > 0) {
			sqdb = bdb.getWritableDatabase();
			if (sqdb != null) {
				sqdb.beginTransaction();
				ContentValues values = new ContentValues();
				values.put(BankDB.BANK, bankName);
				values.put(BankDB.NUMBER, numCard);
				values.put(BankDB.NAME, nameCard);
				sqdb.insert(BankDB.TABLE_CARDS, null, values);
				sqdb.setTransactionSuccessful();
				sqdb.endTransaction();
				return true;
			}
		}
		return false;
	}

	public static boolean changeCard(int id_card, String numCard, String nameCard, String bankName){
		if (numCard.length() >= 4 && nameCard.length() >= 1 && bankName.length() > 0) {
			sqdb = bdb.getWritableDatabase();
			if (sqdb != null) {
				sqdb.beginTransaction();
				ContentValues values = new ContentValues();
				values.put(BankDB.BANK, bankName);
				values.put(BankDB.NUMBER, numCard);
				values.put(BankDB.NAME, nameCard);
				String where = BankDB.ID + "=?";
				String[] whereArgs = new String[]{String.valueOf(id_card)};
				sqdb.update(BankDB.TABLE_CARDS, values, where, whereArgs);
				sqdb.setTransactionSuccessful();
				sqdb.endTransaction();
				return true;
			}
		}
		return false;
	}

	public static boolean deleteCard(int id_card){
		if (id_card > 0) {
			sqdb = bdb.getWritableDatabase();
			if (sqdb != null) {
				sqdb.beginTransaction();
				String where = BankDB.ID + "=?";
				String[] whereArgs = new String[]{String.valueOf(id_card)};
				sqdb.delete(BankDB.TABLE_CARDS, where, whereArgs);
				sqdb.setTransactionSuccessful();
				sqdb.endTransaction();
				return true;
			}
		}
		return false;
	}

	public static List<String> getHistory(int id_card){
		List<String> list = new ArrayList<String>();
		sqdb = bdb.getReadableDatabase();
		String query = "SELECT " + BankDB.ACTION + " FROM " + BankDB.TABLE_HISTORY +" where "+BankDB.ID_CARD+"=?";
		String[] selectionArgs = new String[]{String.valueOf(id_card)};
		if (sqdb != null){
			cursor = sqdb.rawQuery(query, selectionArgs);
			while (cursor.moveToNext()) {
				list.add(cursor.getString(cursor.getColumnIndex(BankDB.ACTION)));
			}
			cursor.close();
			sqdb.close();
		}
		return list;
	}

	public static boolean addToHistory(int id_card, String sms){
		if (id_card > 0 && sms.length() > 1) {
			sqdb = bdb.getWritableDatabase();
			String query = "SELECT * FROM " + BankDB.TABLE_HISTORY + " where "+BankDB.ID_CARD+"=?";
			String[] selectionArgs = new String[]{String.valueOf(id_card)};
			if (sqdb != null) {
				sqdb.beginTransaction();
				cursor = sqdb.rawQuery(query, selectionArgs);
//                for (String str : cursor.getColumnNames())
//                    Log.d("Hist------", "."+str+".");
				if (cursor.getCount() >= 10) {
					if(cursor.moveToNext()) {
						int id = cursor.getInt(cursor.getColumnIndex(BankDB.ID));
						String where = BankDB.ID + "=?";
						String[] whereArgs = new String[] {String.valueOf(id)};
						sqdb.delete(BankDB.TABLE_CARDS, where, whereArgs);
					}
				}
				ContentValues values = new ContentValues();
				values.put(BankDB.ID_CARD, id_card);
				values.put(BankDB.ACTION, sms);
				sqdb.insert(BankDB.TABLE_HISTORY, null, values);
				cursor.close();
				sqdb.setTransactionSuccessful();
				sqdb.endTransaction();
				sqdb.close();
				return true;
			} else
				return false;
		}
		return false;
	}
}