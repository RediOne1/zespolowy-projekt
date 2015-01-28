/**
 * Moduł generujący obrazki
 */
package com.projekt.zespolowy.generator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.projekt.zespolowy.R;

/**
 * Interfejs modułu generatora.
 * <p/>
 * Pozwala użytkownikowi wybrać generator spośród dostępnych, pobiera parametr seed i wywołuje
 * {@link Display}.
 */
public class MainGenerator extends Activity implements OnClickListener, SeekBar.OnSeekBarChangeListener {

	private Button generateBtn;
	private SeekBar rSeekBar, gSeekBar, bSeekBar;
	private TextView rText, gText, bText;
	private View rootLayout, rLayout, gLayout, bLayout;
	private int r, g, b;
	private static RGBGenerator rgbGenerator = new RGBGenerator();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_generator);
		rootLayout = (View) findViewById(R.id.rootLayout);

		rLayout = (View) findViewById(R.id.rLayout);
		gLayout = (View) findViewById(R.id.gLayout);
		bLayout = (View) findViewById(R.id.bLayout);

		generateBtn = (Button) findViewById(R.id.generateButton);
		generateBtn.setOnClickListener(this);

		rSeekBar = (SeekBar) findViewById(R.id.RseekBar);
		gSeekBar = (SeekBar) findViewById(R.id.GseekBar);
		bSeekBar = (SeekBar) findViewById(R.id.BseekBar);
		rSeekBar.setOnSeekBarChangeListener(this);
		gSeekBar.setOnSeekBarChangeListener(this);
		bSeekBar.setOnSeekBarChangeListener(this);

		rText = (TextView) findViewById(R.id.Rtext);
		gText = (TextView) findViewById(R.id.Gtext);
		bText = (TextView) findViewById(R.id.Btext);

		// budowanie listy dostępnych generatorów
		Spinner spinner = (Spinner) findViewById(R.id.generatorsSpinner);
		ArrayAdapter<Generator> adapter = new ArrayAdapter<Generator>(this,
		android.R.layout.simple_list_item_1, generators);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectedGenerator = (Generator) parent.getItemAtPosition(position);
				if (position == generators.length - 1) {
					rLayout.setVisibility(View.VISIBLE);
					gLayout.setVisibility(View.VISIBLE);
					bLayout.setVisibility(View.VISIBLE);
				} else {
					rLayout.setVisibility(View.GONE);
					gLayout.setVisibility(View.GONE);
					bLayout.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	public void generate(View _) {
		EditText et = (EditText) findViewById(R.id.seedInput);
		long seed;
		try {
			seed = Long.parseLong(et.getText().toString());
		} catch (NumberFormatException e) {
			return;
		}
		Intent intent = new Intent(this, Display.class);
		intent.putExtra("seed", seed)
		.putExtra("generator", selectedGenerator);
		startActivity(intent);
	}

	/// lista dostępnych generatorów
	static Generator[] generators = {new LinesGenerator(), new ExampleGenerator(),
	new FreakyGenerator(), new Poziome(), new NewGenerator(), rgbGenerator};
	Generator selectedGenerator = generators[0];

	@Override
	public void onClick(View v) {
		if (v == generateBtn)
			generate(v);
	}

	private void updateBackground() {
		rgbGenerator.r = r;
		rgbGenerator.g = g;
		rgbGenerator.b = b;
		rootLayout.setBackgroundColor(Color.rgb(r, g, b));
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (seekBar == rSeekBar) {
			r = progress;
			rText.setText("" + progress);
		} else if (seekBar == gSeekBar) {
			g = progress;
			gText.setText("" + progress);
		} else if (seekBar == bSeekBar) {
			b = progress;
			bText.setText("" + progress);
		}
		updateBackground();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}
}