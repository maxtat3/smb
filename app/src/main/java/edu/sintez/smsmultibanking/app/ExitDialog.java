package edu.sintez.smsmultibanking.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import edu.sintez.smsmultibanking.app.builder.BankSelector;
import edu.sintez.smsmultibanking.app.builder.Handler;

/**
 * Created by Max on 24.07.2014.
 */
public class ExitDialog extends DialogFragment implements DialogInterface.OnClickListener{



	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle("Выход");
		dialog.setMessage("Вы действительно хотите выйти ?");
		dialog.setPositiveButton("Да", this);
		dialog.setNegativeButton("Нет", this);

		return dialog.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		MainActivity mainActivity = (MainActivity)getActivity();
		switch (which){
			case Dialog.BUTTON_POSITIVE:
				mainActivity.finish();
				break;
			case Dialog.BUTTON_NEGATIVE:
				break;
		}
	}










//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		setContentView(R.layout.activity_menu_about_program);
//	}


//	public void backButtonHandler() {
//		AlertDialog.Builder alertDialog = new AlertDialog.Builder( ExitDialog.this);
//		alertDialog.setTitle("Выход");
//		alertDialog.setMessage("Вы действительно хотите выйти ?");
//		alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				finish();
//			}
//		});
//
//		alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//
//		alertDialog.show();
//	}

}
