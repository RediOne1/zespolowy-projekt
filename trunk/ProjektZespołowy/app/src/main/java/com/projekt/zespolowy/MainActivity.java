/**
 * \mainpage
 * Projekt ten ma na celu zbadanie możliwości użycia ekranu oraz aparatu telefonu komórkowego do
 * uwierzytelniania danego urządzenia. Na ekran telefou miałaby zostać nałożona folia,
 * która byłaby w sposób niepodrabialny mechanicznie zadrapana. Na ekranie telefonu byłby
 * wyświetlony wzór, który razem z folią dawałby obraz wynikowy, możliwy do zweryfikowania
 * przez drugie urządzenie wyposażone w aparat.
 *
 * Program skłąda się z dwóch części, generatora i skanera. Klasa [MainActivity](@ref com.projekt.zespolowy.MainActivity),
 * która jest punktem wejścia aplikacji, wyświetla proste menu pozwalające wybrać moduł.
 *  - Generator
 *
 *    Jest to moduł generujący wzory używając różnych algorytmów. Główny interfejs znajduje
 *    się w klasie [MainGenerator](@ref com.projekt.zespolowy.generator.MainGenerator).
 *    Pozwala on na wprowadzenie wartości *seed* oraz wybranie algorytmu z listy wszystkich dostępnych.
 *
 *    Po wciśnięciu przycisku *Generuj* uruchamiany jest widok
 *    [Display](@ref com.projekt.zespolowy.generator.Display), do którego poprzez *intent*
 *    przekazywany jest parametr *seed* oraz serializowany obiekt implementacji
 *    [generatora](@ref com.projekt.zespolowy.generator.Generator). Klasa
 *    [Display](@ref com.projekt.zespolowy.generator.Display) definiuje własny widok
 *    [DisplayView](@ref com.projekt.zespolowy.generator.Display.DisplayView), którego używa do
 *    wyświetlenia bitmapy zwróconej przez metodę generate() generatora.
 *
 *    Klasa abstrakcyjna [Generator](@ref com.projekt.zespolowy.generator.Generator) jest podstawą
 *    wszystkich algorytmów generowania wzorów. Każdy generator implementuje funkcję generate,
 *    która pobiera seed, szerokość oraz wysokość i zwraca wygenerowany wzór pod postacią
 *    [android.graphics.Bitmap](https://developer.android.com/reference/android/graphics/Bitmap.html).
 *
 *  - Skaner i porównywarka
 *
 * findScratch:
 *	Klasa spelnia role filtra nakladanego na wykonane zdjecia ekranow. 
 *	Na podstawie uśrednionej wartości bieli wyszukuje punkty podejrzane o bycie rysami.
 *	Pola takie kolorowane są na czarno. Wszystkie piksele nie będące domniemanymi rysami
 *	zostają pokolorowane na biało.
 *	Celem takiego działania jest próba jednoznacznego określenia położenia rys.
 *	Klasa działa wyjątkowo wolno ze względu na dużą rodzielczość zdjęć 
 *	oraz algorytm o rozwiazaniu silowym - każdy z pikseli zdjęcia zostaje sprawdzony.
 *	Zdjęcia po przeszukaniu zostają zapisane na urządzeniu.
 *
 */

package com.projekt.zespolowy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.projekt.zespolowy.generator.MainGenerator;
import com.projekt.zespolowy.skaner.MainSkaner;

/**
 * Activity uruchamiane przy starcie aplikacji.
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button generatorBtn, skanerBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		generatorBtn = (Button) findViewById(R.id.main_generator_btn);
		skanerBtn = (Button) findViewById(R.id.main_skaner_btn);
		generatorBtn.setOnClickListener(this);
		skanerBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == generatorBtn) {
			Intent i = new Intent(this, MainGenerator.class);
			startActivity(i);
		} else if (v == skanerBtn) {
			Intent i = new Intent(this, MainSkaner.class);
			startActivity(i);
		}
	}
}
