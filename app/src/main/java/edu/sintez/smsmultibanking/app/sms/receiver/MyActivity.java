package edu.sintez.smsmultibanking.app.sms.receiver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements View.OnClickListener{

	public TextView text;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//            text = (TextView) findViewById(R.id.text1);
        }

        @Override
        public void onClick(View v) {
            //показываем инфу из ресивера
            Bundle extras = getIntent().getExtras();
            String phoneNumber = extras.getString("phone");
            String msg = extras.getString("message");
            text.setText("Сообщение от: " + phoneNumber + "\nДанные: " + msg);
            Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_LONG).show();

        }

    }

