package com.projekt.zespolowy.generator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;
 
/**
 * Linie w poziomie i pionie (dobrze było na nich widać scratche na ostatnich zajęciach)
 *
 */
public class Poziome extends Generator
{
	@Override
	public Bitmap generate(long seed, int w, int h)
	{
		init(w, h);
		Paint paint = new Paint();
		Random r = new Random(seed);
		for (int y = 0; y<h; y++){
				if (r.nextDouble()<0.30){
						y++;
				}
			for (int x = 0; x<w; x++){
				paint.setColor(Color.BLACK);

					x++;


				canvas.drawPoint(x, y, paint);
			}
		}
		return bitmap;
	}
 
	@Override
	public String toString()
	{
		return "Kamil";
	}
}