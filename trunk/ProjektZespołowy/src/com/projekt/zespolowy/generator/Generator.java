package com.projekt.zespolowy.generator;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.Serializable;

/**
 * Klasa podstawowa wszystkich generatorów obrazków.
 *
 * Klasy pochodne muszą zaimplementować metodę {@link #generate(long, int, int)}.
 */
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
	 * Generuje pseudolosową bitmapę o zadanych wymiarach na podstawie podanego seeda.
	 *
	 * Najpierw wywołać init(w,h), potem można używać funkcji Canvasa do rysowania. Na koniec
	 * zwrócić bitmap.
	 *
	 * Zobacz przykład w {@link ExampleGenerator}
	 *
	 * @param w szerokość rządanej bitmapy
	 * @param h wysokość rządanej bitmapy
	 */
	public abstract Bitmap generate(long seed, int w, int h);

	/// Zwraca nazwę generatora
	@Override
	public String toString()
	{
		return getClass().getSimpleName();
	}
}