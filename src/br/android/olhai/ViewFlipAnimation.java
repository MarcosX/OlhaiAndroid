package br.android.olhai;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class ViewFlipAnimation {

	private static ViewFlipAnimation thisInstance = new ViewFlipAnimation();
	private Animation inFromLeft;
	private Animation outToRight;
	private Animation inFromRight;
	private Animation outToLeft;

	// Construtor privado pq usa o padrão singleton
	private ViewFlipAnimation() {
		inFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
				-1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());

		outToRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outToRight.setDuration(500);
		outToRight.setInterpolator(new AccelerateInterpolator());

		inFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
				+1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new AccelerateInterpolator());

		outToLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outToLeft.setDuration(500);
		outToLeft.setInterpolator(new AccelerateInterpolator());
	}

	public static ViewFlipAnimation getInstance() {
		return thisInstance;
	}

	public Animation getInFromLeft() {
		return inFromLeft;
	}

	public Animation getOutToRight() {
		return outToRight;
	}

	public Animation getInFromRight() {
		return inFromRight;
	}

	public Animation getOutToLeft() {
		return outToLeft;
	}

}
