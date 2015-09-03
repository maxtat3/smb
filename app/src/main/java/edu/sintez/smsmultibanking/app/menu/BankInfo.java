package edu.sintez.smsmultibanking.app.menu;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Patterns;
import android.widget.TextView;
import edu.sintez.smsmultibanking.app.R;

/**
 * Created by Max on 24.07.2014.
 */
public class BankInfo extends Activity{

	private TextView txtAddress;
	private TextView txtTel;
	private TextView txtSite;
	private int key;

	private String[] banks_address;
	private String[] banks_tel;
	private String[] banks_site;

	private final static int MY_PHONE_NUMBER_MINIMUM_DIGITS = 3;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_bank_info);

		Bundle extras = getIntent().getExtras();
		key = extras.getInt("selectedBank");

		txtAddress = (TextView) findViewById(R.id.txtAddress);
		banks_address = getResources().getStringArray(R.array.banks_address);
		txtAddress.setText(banks_address[key]);

		txtTel = (TextView) findViewById(R.id.txtTel);
		banks_tel = getResources().getStringArray(R.array.banks_tel);
		txtTel.setText(banks_tel[key]);

		Linkify.addLinks(txtTel, Patterns.PHONE, "tel:", new Linkify.MatchFilter() {
			public final boolean acceptMatch(CharSequence s, int start, int end) {
				int digitCount = 0;

				for (int i = start; i < end; i++) {
					if (Character.isDigit(s.charAt(i))) {
						digitCount++;
						if (digitCount >= MY_PHONE_NUMBER_MINIMUM_DIGITS) {
							return true;
						}
					}
				}
				return false;
			}
		},
		Linkify.sPhoneNumberTransformFilter);

		txtSite = (TextView) findViewById(R.id.txtSite);
		banks_site = getResources().getStringArray(R.array.banks_site);
		txtSite.setText(banks_site[key]);
	}

}
