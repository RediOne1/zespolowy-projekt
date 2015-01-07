package com.projekt.zespolowy.skaner;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.projekt.zespolowy.R;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.Highgui;

import java.util.List;

import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;


public class findScratch extends CompareTemplate {
	 
	 public findScratch (Activity result) { 
	 	super(result); 
	 }
	 
	@Override public void compare(Bitmap picture1, Bitmap picture2) {
		storeImage(generateScratchImage(picture1));
		result.setText("Wynik");
		storeImage(generateScratchImage(picture2));
		result.setText("Wynik");
	}

	//@Override public void compare(String firstPath, String secondPath) { }
	 

	/**
	 * Metoda wyszukuje na podanej mapie bitowej rysy i zaznacza je kolorem czarnym
	   pozostałe pixele zaznaczane są kolorem białym
	   
	   @return zwraca przetworzona mape bitowa
	 */
	public Bitmap generateScratchImage(Bitmap obraz) {
	
		Paint paint = new Paint();
	
		int width = obraz.getWidth();
		int height = obraz.getHeight();
		
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
		 int i,j;
		 for (i = 0; i < 10; i++) {
		 	for (j = 0; j < width; j++) {
		 		
		 		pixel = obraz.getPixel(i,j);
		 		
		 		red = Color.red(pixel);
		 		blue = Color.blue(pixel);
		 		green = Color.green(pixel);
		 		
		 		value = red * blue * green;
		 		
		 		if ( extremum < value ) {
		 			extremum = value;
		 		}
		 	}
		 }
		 
		int vector = 16581375 - extremum; // 16581375 = 255^3
		 
		int R1,R2,L1,L2; /// granice od ktorych zaczynaja sie rysy
		R1 = 15000000 - vector;
		R2 = 16581375 - vector;
		L1 = 12000000 - vector;
		L2 = 0;
		 
		 /**
		  * Główna pętla wyszukująca rysy
		  */
		  
		for ( i = 10; i < height; i++){
			for ( j = 0; j < width; j++) {
				pixel = obraz.getPixel(i,j);
		 		
		 		red = Color.red(pixel);
		 		blue = Color.blue(pixel);
		 		green = Color.green(pixel);
		 		
		 		value = red * blue * green;
		 		
		 		if ( value >= R1 && value <= R2 ) {	// rysa
		 			paint.setColor(Color.BLACK);
		 		}
		 		else if ( value >= L1 && value <= L2 ) {	// rysa
		 			paint.setColor(Color.BLACK);
		 		}
		 		else {
		 			paint.setColor(Color.WHITE);
					
		 		}
		 		
		 		canvas.drawPoint(x, y, paint);	 		
			}
		
		}
		
		return bitmap;
	}
	
	// zakomentowane z powodu nie dolaczenia bibliotek
	/**
	 * Metoda zapisuje obraz w pliku
	 
	  @return zwraca true, jesli nie wystapil blad podczas zapisu
	 */	
	private boolean storeImage(Bitmap imageData) {
		
		String iconsStoragePath = Environment.getExternalStorageDirectory() + "/myAppDir/myImages/"
		File sdIconStorageDir = new File(iconsStoragePath);

		sdIconStorageDir.mkdirs();

		Random generator = new Random();
		String filename = "test_"+generator.nextInt(1000);
		try {
			String filePath = sdIconStorageDir.toString() + filename;
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);

			BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

			imageData.compress(CompressFormat.PNG, 100, bos);

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
