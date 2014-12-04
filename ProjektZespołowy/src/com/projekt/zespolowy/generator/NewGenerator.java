package com.projekt.zespolowy.generator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by adrian on 04.12.14.
 */
public class NewGenerator extends Generator {

	private Paint paint;

	@Override
	public Bitmap generate(long seed, int w, int h) {
		init(w, h);
		paint = new Paint();
		paint.setColor(Color.GRAY);
		canvas.drawRect((float) 0, (float) 0, (float) w, (float) h, paint);
		return bitmap;
	}

	@Override
	public String toString() {
		return "Jednolity";
	}
}
