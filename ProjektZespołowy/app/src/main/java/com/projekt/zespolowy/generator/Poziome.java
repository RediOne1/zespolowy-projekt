package com.projekt.zespolowy.generator;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Generator rysuje linie w poziomie i w pionie.
 *
 * @author Kamil Falszewski
 */
public class Poziome extends Generator {

	/**
	 * @param seed ziarno dla którego zostanie wygenerowana bitmapa
	 * @param w    szerokosc ekranu,
	 * @param h    wysokosc ekranu
	 * @return bitmap zwraca wygenerowaną bitmape
	 */
	@Override
	public Bitmap generate(long seed, int w, int h) {
		init(w, h);
		/** Metoda rysująca */
		Paint paint = new Paint();
		/**
		 * Zapewnienie losowości wygenerowanego obrazu, na podstawie podanego
		 * seeda
		 */
		Random r = new Random(seed);
		for (int y = 0; y < h; y++) {
			if (r.nextDouble() < 0.30) {
				y++;
			}
			for (int x = 0; x < w; x++) {
				/** Określenie koloru generowanych linii */
				paint.setColor(Color.BLACK);

				x++;

				/** Wywołanie rysowania */
				canvas.drawPoint(x, y, paint);
			}
		}
		return bitmap;
	}

	@Override
	public String toString() {
		/** Zwracanie nazwy autora generatora do głownego ekranu */
		return "Kamil";

	}
}