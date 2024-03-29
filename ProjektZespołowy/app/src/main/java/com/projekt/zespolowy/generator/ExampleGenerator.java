package com.projekt.zespolowy.generator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Przykładowy generator zapełniający obrazek losowo białymi i czarnymi pikselami.
 */
public class ExampleGenerator extends Generator {
	@Override
	public Bitmap generate(long seed, int w, int h) {
		init(w, h);
		Paint paint = new Paint();
		Random r = new Random(seed);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (r.nextDouble() < 0.25)
					paint.setColor(Color.WHITE);
				else
					paint.setColor(Color.BLACK);
				canvas.drawPoint(x, y, paint);
			}
		}
		return bitmap;
	}

	@Override
	public String toString() {
		return "Szum";
	}
}