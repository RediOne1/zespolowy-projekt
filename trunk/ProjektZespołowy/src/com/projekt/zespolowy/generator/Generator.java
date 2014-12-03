package com.projekt.zespolowy.generator;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.Serializable;

public abstract class Generator implements Serializable
{
	private static final long serialVersionUID = -8690093341007522387L;
	Bitmap bitmap;
	Canvas canvas;

	void init (int w, int h)
	{
		bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);
	}

	/**
	 * Bierze seed, szerokosc i wysokość ekranu i zwraca Bitmap.
	 *
	 * Najpierw wywołać init(w,h), potem używać funkcji Canvasa do rysowania. Na koniec
	 * zwrócić bitmap.
	 *
	 * Zobacz przykład w {@link ExampleGenerator}
	 *
	 * Po zaimplementowaniu dopisać do listy "generatory" w {@link MainGenerator}
	 *
	 * @param w szerokość
	 * @param h wysokość
	 */
	public abstract Bitmap generate(long seed, int w, int h);

	@Override
	public String toString()
	{
		return getClass().getSimpleName();
	}
}