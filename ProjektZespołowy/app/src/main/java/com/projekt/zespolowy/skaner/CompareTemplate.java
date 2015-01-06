package com.projekt.zespolowy.skaner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.TextView;

import com.projekt.zespolowy.R;

/**
 * Created by adrian
 * on 05.01.15.
 */
public abstract class CompareTemplate {

	public TextView result;

	public CompareTemplate(Activity activity){
		result = (TextView) activity.findViewById(R.id.result);
	}

	public abstract void compare(Bitmap picture1, Bitmap picture2);

	public abstract void compare(String firstPath, String secondPath);

	public abstract String toString();
}
