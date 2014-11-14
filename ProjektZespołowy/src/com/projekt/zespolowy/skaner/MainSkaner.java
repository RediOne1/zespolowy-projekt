package com.projekt.zespolowy.skaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.projekt.zespolowy.R;

public class MainSkaner extends Activity implements OnClickListener {
	private ImageView shareBtn;
	private EditText seed_toSent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_skaner);
		shareBtn = (ImageView) findViewById(R.id.share_seed);
		seed_toSent = (EditText) findViewById(R.id.seed_toSent);
		shareBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_skaner, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void share() {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, seed_toSent.getText().toString());
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Seed");
		startActivity(Intent.createChooser(sharingIntent, "Wyślij za pomocą"));
	}

	@Override
	public void onClick(View v) {
		if (v == shareBtn)
			share();

	}
}
