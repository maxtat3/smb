package edu.sintez.smsmultibanking.app.tab1actions.unique.fragments;

import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.sintez.smsmultibanking.app.MainActivity;
import edu.sintez.smsmultibanking.app.R;
import edu.sintez.smsmultibanking.app.tab1actions.unique.dialogsfragments.ReplenishMobTelDialog;


/**
 * Created by Max on 12.07.2014.
 */
public class ReplenishMobTel extends Fragment{

	private static final String LOG_TAG = ReplenishMobTel.class.getName();

    private String[] prohibitedCodes = {"101", "102", "103", "104", "105", "106", "107", "108", "109",
            "01", "02", "03", "04", "05", "06", "07", "08", "09"};

    private String prohibited = "На этот номер нельзя отправлять СМС!";
    private String badInput = "Не заполнены все поля!";



    private EditText editTextPhoneNumber;
	private EditText editTextSum;
	private Button btnFromPhoneBook;
	private Button btnMyPhoneNumber;
	private Button btnGoReplenishMobTel;

	private ReplenishMobTelDialog dialog;

	private int sum = 0;
	private String mobTel = "";


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_tab1_replenish_mob_tel, null);

		final MainActivity mainActivity = (MainActivity)getActivity();


		editTextPhoneNumber = (EditText)view.findViewById(R.id.editTextPhoneNumber);
		editTextSum = (EditText)view.findViewById(R.id.editTextSum);


		// нажатие на кнопку [из контактов]
		btnFromPhoneBook = (Button) view.findViewById(R.id.btnFromPhoneBook);
		btnFromPhoneBook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				String mobTel = mainActivity.getSelectedPhoneBookContact();
//				editTextPhoneNumber.setText(mobTel);
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, 3);
				// вызов этого intent-а должен возвращать номер в mobTel !!!
			}
		});


		// нажатие на кнопку [мой номер]
		btnMyPhoneNumber = (Button)view.findViewById(R.id.btnMyPhoneNumber);
			btnMyPhoneNumber.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mobTel = mainActivity.getMyTelNumber();
					editTextPhoneNumber.setText(mobTel);
				}
		});


		// нажатие на кнопку [пополнить]
		btnGoReplenishMobTel = (Button)view.findViewById(R.id.btnGoReplenishMobTel);
		btnGoReplenishMobTel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//введен ли номер телефона ?
				if ( !(String.valueOf(editTextPhoneNumber.getText())).equals("") ){

					//введено ли какое-то значение суммы пополнения ?
					if ( !String.valueOf(editTextSum.getText()).isEmpty() ) {
						sum = Integer.valueOf(String.valueOf(editTextSum.getText()));

						//значение суммы пополнения больше 0 грн ?
						if (sum > 0){
							// тут выполняется основное действие !!!
							mobTel = editTextPhoneNumber.getText().toString();
							String preMobTel = removeExcessChars(mobTel);

							dialog = new ReplenishMobTelDialog(preMobTel, sum);
							dialog.show(getFragmentManager(), "dialogReplMobTel");
						}else {
							Toast.makeText(mainActivity.getApplicationContext(), "Минимальная сумма пополнения = 1 грн !", Toast.LENGTH_SHORT).show();
						}

					}else {
						Toast.makeText(mainActivity.getApplicationContext(), "Введите сумму !", Toast.LENGTH_SHORT).show();
					}

				}else {
					Toast.makeText(mainActivity.getApplicationContext(), "Введите номер телефона !", Toast.LENGTH_SHORT).show();
				}
			}
		});

		return view;
	}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK){
            switch (requestCode) {
                case (3):
                    String id = getContactID(data);
                    String phoneNumb = getPhoneNumber(id);
                    if (phoneNumb == null) {
                        Toast.makeText(getActivity().getApplicationContext(), "Нельзя выбрать номер данного контакта", Toast.LENGTH_LONG).show();
                    } else {
                        String withoutSpaces = removeSpaces(phoneNumb);

                        if (prohibitedCodes(withoutSpaces)) {
                            editTextPhoneNumber.setText(removeExcessChars(withoutSpaces));
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), prohibited, Toast.LENGTH_LONG).show();
                        }
                    }
                    break;

            }
        }
    else {
                Toast.makeText(getActivity().getApplicationContext(), "Выбор контакта отменен!", Toast.LENGTH_LONG).show();
            }

        }

    /**
     * Получаем ID контакта
     */
    private String getContactID(Intent data) {
        Uri contactData = data.getData();
        Cursor contactCursor = new ContextWrapper(getActivity()).getContentResolver().query(contactData,
                new String[]{ContactsContract.Contacts._ID},
                null,
                null,
                null);
        String id = null;
        if (contactCursor.moveToFirst()) {
            id = contactCursor.getString(contactCursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
        }

        contactCursor.close();
        return id;
    }

    /**
     * Получаем номер контакта по ID
     */
    private String getPhoneNumber(String id) {
        String phoneNumber = null;
         Cursor phoneCursor = new ContextWrapper(getActivity()).getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ? ",
                new String[]{id},
                null);
        if (phoneCursor.moveToFirst()) {
            phoneNumber = phoneCursor
                    .getString(phoneCursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
        phoneCursor.close();
        return phoneNumber;
    }

    /**
     * Удаляем все пробелы из номера телефона
     */
    private String removeSpaces(String input) {
        return input.replaceAll(" ", "");
    }

    private boolean prohibitedCodes(String phoneNumber) {
        for (String deprecatedCode : prohibitedCodes) {
            if (phoneNumber.equals(deprecatedCode)) {
                return false;
            }
        }
        return true;
    }

    private String removeExcessChars(String mobPhone){
        if (mobPhone.startsWith("+38")){
            return mobPhone.replace(mobPhone.substring(0,3), "");
        } else if (mobPhone.startsWith("38")) {
            return mobPhone.replace(mobPhone.substring(0,2), "");
        } else if (mobPhone.startsWith("8")){
        return mobPhone.replace(mobPhone.substring(0,1), "");
        }
        return mobPhone;
    }

}
