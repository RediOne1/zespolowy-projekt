/**
 * \mainpage
 * Tu będzie ogólny opis całego projektu
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
