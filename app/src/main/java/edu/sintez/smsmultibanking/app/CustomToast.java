package edu.sintez.smsmultibanking.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by Max on 25.07.2014.
 */
public class CustomToast {

	private static void show(final long durationInMilliseconds, final Toast toast) {
		Thread t = new Thread() {
			long timeElapsed = 0l;
			public void run() {
				try {
					while (timeElapsed <= durationInMilliseconds) {
						long start = System.currentTimeMillis();
						toast.show();
						sleep(250);
						timeElapsed += System.currentTimeMillis() - start;
					}
				} catch (InterruptedException e) {
				}
			}
		};
		t.start();
	}


	private static void showToast(Context context, View view, long duration) {
		Toast toast = new Toast(context);

		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);

		show(duration * 1000, toast);
	}


	/**
	 * Создает сообщение Toast с заданными параметрами.
	 * Пример создания: CustomToast.makeToast(getApplicationContext(), "Ваш запрос в процессе выполнения", 5);
	 * @param context - в каком контексте показывать Toast
	 * @param msg - сообщение, строка (String)
	 * @param duration - время отображения Toast (в секундах)
	 */
	public static void makeToast(Context context, String msg, long duration) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.pattern_custom_toast, null);

		((TextView) view.findViewById(R.id.textCustomToast)).setText(msg);

		showToast(context, view, duration);
	}

	/**
	 *Пример создания: CustomToast.makeToast(getApplicationContext(), R.string.msgText, 3);
	 * @param context - в каком контексте показывать Toast
	 * @param msg - сообщение, идентификатор строкого ресурса (int)
	 * @param duration - время отображения Toast (в секундах)
	 */
	public static void makeToast(Context context, int msg, long duration) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.pattern_custom_toast, null);

		((TextView) view.findViewById(R.id.textCustomToast)).setText(msg);

		showToast(context, view, duration);
	}


}
