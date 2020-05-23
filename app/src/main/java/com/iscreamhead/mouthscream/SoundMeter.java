package com.iscreamhead.mouthscream;

import android.media.MediaRecorder;

import java.io.IOException;

class SoundMeter {

	private MediaRecorder mRecorder = null;

	void start() {
		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mRecorder.setOutputFile("/dev/null");
			try {
				mRecorder.prepare();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mRecorder.start();
			mRecorder.getMaxAmplitude();
		}
	}

	String stop() {
		if (mRecorder != null) {
			String amplitude = String.valueOf(this.getAmplitude());

			mRecorder.reset();
			mRecorder.release();
			mRecorder = null;

			return amplitude;
		}

		return "";
	}

	private double getAmplitude() {
		if (mRecorder != null) {
			return mRecorder.getMaxAmplitude();
		}
		else
			return 0;

	}
}
