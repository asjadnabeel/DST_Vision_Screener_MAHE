package com.manipal.dst_amsler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class Utils
{

	public static final String TIME = "time";
	public static final String DATE = "date";
	public static final String SCORE = "score";
	public static final String PATH = "path";
	public static final String TIMESTAMP = "timestamp";

	public static String[] getFileList(Context c)
	{
		File file = new File(c.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MAmsler");

		return file.list();
	}

	public static ArrayList<HashMap<String, String>> getSortedAdapterData(ArrayList<HashMap<String, String>> adapterData)
	{
		Collections.sort(adapterData, new Comparator<HashMap<String, String>>()
		{

			@Override
			public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs)
			{
				return rhs.get(TIMESTAMP).compareTo(lhs.get(TIMESTAMP));
			}

		});

		return adapterData;
	}

	public static ArrayList<HashMap<String, String>> getAdapterData(String[] fileList, File dir)
	{

		if (fileList == null)
			return null;

		ArrayList<HashMap<String, String>> adapterData = new ArrayList<HashMap<String, String>>();

		for (String s : fileList)
		{
			String strWithoutExtension = s.substring(0, s.lastIndexOf("."));

			String[] strWithSplit = strWithoutExtension.split("_");

			String timeStamp = strWithSplit[0];
			String score = strWithSplit[1];

			String date, time;

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(Long.parseLong(timeStamp));

			Date tempDate = cal.getTime();

			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("HH:mm:ss");

			time = formatter.format(tempDate);

			formatter = new SimpleDateFormat("dd/MM/yyyy");

			date = formatter.format(tempDate);

			HashMap<String, String> map = new HashMap<String, String>();

			map.put(PATH, dir.getAbsolutePath() + "/" + s);
			map.put(SCORE, score);
			map.put(TIME, time);
			map.put(DATE, date);
			map.put(TIMESTAMP, Long.parseLong(timeStamp) + "");

			adapterData.add(map);

		}

		Collections.sort(adapterData, new Comparator<HashMap<String, String>>()
		{

			@Override
			public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs)
			{
				return rhs.get(TIMESTAMP).compareTo(lhs.get(TIMESTAMP));
			}

		});

		/*
		 * for (HashMap<String, String> map : adapterData) { Log.i("PATH",
		 * map.get(PATH)); Log.i("SCORE", map.get(SCORE)); Log.i("TIME",
		 * map.get(TIME)); Log.i("DATE", map.get(DATE)); }
		 */

		return adapterData;

	}

	public static int dpToPx(Context c, int dp)
	{
		DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth)
		{

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight)
	{

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

}
