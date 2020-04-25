package com.iscreamhead.mouthscream;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
	EditText userName;
	ImageView start;
	ImageView inGame;
	TextView gameHint;

	final List<Pair<Integer, String>> titleList = new ArrayList<>(Arrays.asList(
			new Pair<>(4000, "Тихо"),
			new Pair<>(12000, "Плохо"),
			new Pair<>(20000, "Неплохо"),
			new Pair<>(32000, "Нормально"),
			new Pair<>(Integer.MAX_VALUE, "Громко"))
	);
	int currentTitleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUserNameListener();
		setImagesViewListener();
//        currentScore = findViewById(R.id.currentScore);
//        setTextScore();
//        setNameUserInputLayer();
//        startRecordSound();
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

//    private void startRecordSound() {
//        final SoundMeter soundMeter = new SoundMeter();
//        soundMeter.start();
//        final Handler changeTextView = new Handler();
//        changeTextView.post(new Runnable() {
//            int maxAmplitude;
//
//            @Override
//            public void run() {
//                double amplitude = soundMeter.getAmplitude();
//                if (maxAmplitude < amplitude) {
//                    maxAmplitude = (int) amplitude;
//                }
//                currentScore.setText(String.valueOf(maxAmplitude));
//                while (maxAmplitude > titleList.get(currentTitleList).first) {
//                    currentTitleList++;
//                    textScore.setText(titleList.get(currentTitleList).second);
//                }
//                changeTextView.postDelayed(this, 1);
//            }
//        });
//    }

	void setImagesViewListener() {
		start = (ImageView)findViewById(R.id.imageView);
		inGame = (ImageView)findViewById(R.id.imageView2);
		gameHint = (TextView)findViewById(R.id.textView2);

		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				start.setVisibility(View.INVISIBLE);
				inGame.setVisibility(View.VISIBLE);
				gameHint.setText("Кричи на телефон");
			}
		});

		inGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				start.setVisibility(View.VISIBLE);
				inGame.setVisibility(View.INVISIBLE);
				gameHint.setText("Нажми на рот");
			}
		});
	}

	void setUserNameListener() {
		userName = (EditText)findViewById(R.id.user_name_input);

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

//    void setTextScore() {
//        textScore = findViewById(R.id.textScore);
//        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/intro.otf");
//        textScore.setTypeface(custom_font);
//        textScore.setText(titleList.get(currentTitleList).second);
//    }
}
