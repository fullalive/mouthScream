package com.iscreamhead.mouthscream;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
	private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
	EditText userName;
	ImageView start;
	TextView gameHint;
	SoundMeter soundMeter;
	String fileName;

	private String [] permissions = {Manifest.permission.RECORD_AUDIO};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		fileName = Objects.requireNonNull(getExternalCacheDir()).getAbsolutePath();
		fileName += "/audiorecordtest.3gp";

		ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


		setContentView(R.layout.activity_main);
		setUserNameListener();
		setImagesViewListener();
	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager =
				(InputMethodManager) activity.getSystemService(
						Activity.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null) {
			inputMethodManager.hideSoftInputFromWindow(
					Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
		}
	}

	private void startRecording() {
		soundMeter = new SoundMeter();
		soundMeter.start();
	}

	private void stopRecording() {
		String amplitude = soundMeter.stop();

		gameHint.setText(amplitude);
	}

	void setImagesViewListener() {
		start = findViewById(R.id.imageView);
		gameHint = findViewById(R.id.gameHint);
		gameHint.setText("Нажми на рот");

		start.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameHint.setText("Кричи на телефон");
					start.setImageResource(R.drawable.mouth_opened);
					startRecording();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					gameHint.setText("Нажми на рот");
					start.setImageResource(R.drawable.mouth2);
					stopRecording();
				}

				return true;
			}
		});
	}

	void setUserNameListener() {
		userName = findViewById(R.id.user_name_input);

		userName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					hideSoftKeyboard(MainActivity.this);
					userName.clearFocus();
					return true;
				}

				return false;
			}
		});

		userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				Log.d("is has focus?", Boolean.toString(hasFocus));
			}
		});
	}
}
