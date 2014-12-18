package com.projekt.zespolowy.generator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class LinesGenerator extends Generator {
	@Override
	public Bitmap generate(long seed, int w, int h) {
		init(w, h);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
		int target = (int) (w * h * 0.6);
		Random random = new Random(seed);

		do {
			final int nlines = 200;
			float[] lines = new float[nlines * 4];
			for (int i = 0; i < nlines; i++) {
				float x1 = (float) (random.nextDouble() * w),
				x2 = (float) (random.nextDouble() * w),
				y1 = (float) (random.nextDouble() * h),
				y2 = (float) (random.nextDouble() * h);
				lines[i * 4] = x1;
				lines[i * 4 + 1] = y1;
				lines[i * 4 + 2] = x2;
				lines[i * 4 + 3] = y2;
			}
			canvas.drawLines(lines, paint);
		} while (cover(w, h) < target);

		return bitmap;
	}

	int cover(int w, int h) {
		int counter = 0;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (bitmap.getPixel(x, y) != 0) counter++;
			}
		}
		Log.d("LinesGenerator", "cover: " + (double) counter / (w * h));
		return counter;
	}

	@Override
	public String toString() {
		return "Linie";
	}
}