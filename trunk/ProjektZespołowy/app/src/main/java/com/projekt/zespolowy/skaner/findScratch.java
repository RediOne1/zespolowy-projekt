package com.projekt.zespolowy.skaner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class findScratch extends CompareTemplate {

	public findScratch(Activity result) {
		super(result);
	}

	@Override
	public void compare(Bitmap picture1, Bitmap picture2) {
		if (picture1 != null)
			storeImage(generateScratchImage(picture1));
		result.setText("Wynik");
		if (picture2 != null)
			storeImage(generateScratchImage(picture2));
		result.setText("Wynik");
	}

	@Override
	public void compare(String firstPath, String secondPath) {

	}

	@Override
	public String toString() {
		return "Michał";
	}

	//@Override public void compare(String firstPath, String secondPath) { }


	/**
	 * Metoda wyszukuje na podanej mapie bitowej rysy i zaznacza je kolorem czarnym
	 * pozostałe pixele zaznaczane są kolorem białym
	 *
	 * @return zwraca przetworzona mape bitowa
	 */
	public Bitmap generateScratchImage(Bitmap obraz) {

		Paint paint = new Paint();
		Bitmap workingBitmap = Bitmap.createBitmap(obraz);
		Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(mutableBitmap);
		int width = obraz.getWidth() - 1;
		int height = obraz.getHeight() - 1;

		int x = 0;
		int y = 0;
		int pixel; //= obraz.getPixel(x,y);

		/**
		 * Pobieramy extremum z gornej warstwy 10pixeli
		 */

		int value;
		int red = 0;
		int green = 0;
		int blue = 0;
		int extremum = 0;
		int i, j;
		for (i = 0; i < 10; i++) {
			for (j = 0; j < width; j++) {
				pixel = obraz.getPixel(j, i);

				red = Color.red(pixel);
				blue = Color.blue(pixel);
				green = Color.green(pixel);

				value = red * blue * green;

				if (extremum < value) {
					extremum = value;
				}
			}
		}

		int vector = 16581375 - extremum; // 16581375 = 255^3

		int R1, R2, L1, L2; /// granice od ktorych zaczynaja sie rysy
		R1 = 15000000 - vector;
		R2 = 16581375 - vector;
		L1 = 12000000 - vector;
		L2 = 0;

		/**
		 * Główna pętla wyszukująca rysy
		 */

		for (i = 10; i < height; i++) {
			for (j = 0; j < width; j++) {
				pixel = obraz.getPixel(j, i);

				red = Color.red(pixel);
				blue = Color.blue(pixel);
				green = Color.green(pixel);

				value = red * blue * green;

				if (value >= R1 && value <= R2) {    // rysa
					//paint.setColor(Color.BLACK);
					obraz.setPixel(j,i,Color.BLACK);
				} else if (value >= L1 && value <= L2) {    // rysa
					//paint.setColor(Color.BLACK);
					obraz.setPixel(j,i,Color.BLACK);
				} else {
					//paint.setColor(Color.WHITE);
					obraz.setPixel(j,i,Color.WHITE);
				}

				//canvas.drawPoint(j, i, paint); // ?
			}

		}

		return mutableBitmap; // ?
	}

	/**
	 * Metoda zapisuje obraz w pliku
	 *
	 * @return zwraca true, jesli nie wystapil blad podczas zapisu
	 */
	private boolean storeImage(Bitmap imageData) {

		String iconsStoragePath = Environment.getExternalStorageDirectory() + "/myAppDir/myImages/";
		File sdIconStorageDir = new File(iconsStoragePath);

		sdIconStorageDir.mkdirs();

		Random generator = new Random();
		String filename = "test_" + generator.nextInt(1000) + ".png";
		try {
			String filePath = sdIconStorageDir.toString() + filename;
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);

			BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

			imageData.compress(Bitmap.CompressFormat.PNG, 100, bos);

			bos.flush();
			bos.close();

		} catch (FileNotFoundException e) {
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return false;
		} catch (IOException e) {
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return false;
		}

		return true;
	}

}
