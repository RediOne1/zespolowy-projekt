package com.projekt.zespolowy.generator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import com.projekt.zespolowy.R;

/**
 * Activity wyświetlający na ekranie wygenerowany obrazek.
 * <p/>
 * Klasa pobiera dwa elementy przekazane przez intent:
 *
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class Display extends Activity {

	/**
	 * <p/>
	 * Bitmapa musi mieć te same wymiary, co obszar wyświetlania.
	 */
	public static class DisplayView extends View {

		public DisplayView(Context context) {
			super(context);
		}

		public DisplayView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			if (bitmap == null ||
			(bitmap.getHeight() != canvas.getHeight() &&
			bitmap.getWidth() != canvas.getWidth()))
				return;
			canvas.drawBitmap(bitmap, null, new Rect(0, 0, canvas.getWidth(),
			canvas.getHeight()), null);
		}

		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
			invalidate();
		}

		Bitmap bitmap = null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_display);

		view = (DisplayView) findViewById(R.id.displayView);

		Intent intent = getIntent();
		long seed = intent.getLongExtra("seed", -1);
		if (seed == -1)
			throw new Error("coś się zrypało w getextra");
		Generator g = (Generator) intent.getSerializableExtra("generator");
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Bitmap b = g.generate(seed, metrics.widthPixels, metrics.heightPixels);
		view.setBitmap(b);
	}

	DisplayView view;
}