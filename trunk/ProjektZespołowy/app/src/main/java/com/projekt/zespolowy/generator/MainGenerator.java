/**
 * Moduł generujący obrazki
 */
package com.projekt.zespolowy.generator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.projekt.zespolowy.R;

/**
 * Interfejs modułu generatora.
 * <p/>
 * Pozwala użytkownikowi wybrać generator spośród dostępnych, pobiera parametr seed i wywołuje
 * {@link Display}.
 */
public class MainGenerator extends Activity implements OnClickListener {

	private Button generateBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_generator);

		generateBtn = (Button) findViewById(R.id.generateButton);
		generateBtn.setOnClickListener(this);

		// budowanie listy dostępnych generatorów
		Spinner spinner = (Spinner) findViewById(R.id.generatorsSpinner);
		ArrayAdapter<Generator> adapter = new ArrayAdapter<Generator>(this,
		android.R.layout.simple_list_item_1, generators);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectedGenerator = (Generator) parent.getItemAtPosition(position);
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
	new FreakyGenerator(), new Poziome(), new NewGenerator()};
	Generator selectedGenerator = generators[0];

	@Override
	public void onClick(View v) {
		if (v == generateBtn)
			generate(v);

	}

}