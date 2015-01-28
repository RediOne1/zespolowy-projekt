package com.projekt.zespolowy.generator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by adrian on 28.01.15.
 */
public class RGBGenerator extends Generator {

	private  Paint paint;
	public int r, g, b;

	@Override
	public Bitmap generate(long seed, int w, int h) {
		init(w, h);
		paint = new Paint();
		paint.setColor(Color.rgb(r,g,b));
		canvas.drawRect(0,0,w,h,paint);
		return bitmap;
	}
}
