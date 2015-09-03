package edu.sintez.smsmultibanking.app.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.sintez.smsmultibanking.app.R;

/**
 * Created by Max on 14.07.2014.
 */
public class AboutProgram extends Activity {

	Button btn_menu_about_program;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_about_program);

		btn_menu_about_program = (Button)findViewById(R.id.btn_menu_about_program);
		btn_menu_about_program.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}



}