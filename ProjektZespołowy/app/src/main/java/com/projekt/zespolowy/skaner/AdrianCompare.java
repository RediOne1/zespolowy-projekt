package com.projekt.zespolowy.skaner;

import android.app.Activity;
import android.graphics.Bitmap;
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

/**
 * Created by adrian
 * on 05.01.15.
 */
public class AdrianCompare extends CompareTemplate {


	private String firstPath = null, secondPath = null;

	public AdrianCompare(Activity result) {
		super(result);
	}

	@Override
	public void compare(Bitmap picture1, Bitmap picture2) {
	}

	@Override
	public void compare(String firstPath, String secondPath) {
		this.firstPath = firstPath;
		this.secondPath = secondPath;
		new Compare().execute();
	}

	public class Compare extends AsyncTask<Void, Void, MatOfDMatch> {

		@Override
		protected MatOfDMatch doInBackground(Void... arg0) {
			//Load images to compare
			Mat img1 = Highgui.imread(firstPath, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
			Mat img2 = Highgui.imread(secondPath, Highgui.CV_LOAD_IMAGE_GRAYSCALE);

			MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
			MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
			Mat descriptors1 = new Mat();
			Mat descriptors2 = new Mat();

//Definition of ORB keypoint detector and descriptor extractors
			FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
			DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);

//Detect keypoints
			detector.detect(img1, keypoints1);
			detector.detect(img2, keypoints2);
//Extract descriptors
			extractor.compute(img1, keypoints1, descriptors1);
			extractor.compute(img2, keypoints2, descriptors2);

//Definition of descriptor matcher
			DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

//Match points of two images
			MatOfDMatch matches = new MatOfDMatch();
			matcher.match(descriptors1, descriptors2, matches);
			return matches;
		}

		@Override
		protected void onPostExecute(MatOfDMatch matOfDMatch) {
			List<DMatch> dMatch = matOfDMatch.toList();
			String wynik = "";
			for (int i = 0; i < dMatch.size(); i++)
				wynik += matOfDMatch.toList().get(i).toString() + "\n\n";
			result.setText(wynik);
			super.onPostExecute(matOfDMatch);
		}
	}

	@Override
	public String toString() {
		return "Adrian";
	}
}
