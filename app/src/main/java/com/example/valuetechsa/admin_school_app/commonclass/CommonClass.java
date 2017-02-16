package com.example.valuetechsa.admin_school_app.commonclass;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import com.example.valuetechsa.admin_school_app.libs.ScalingUtilities;


public class CommonClass 
{
	Context context;
	public CommonClass(Context mthis)
	{
		context=mthis;
	}
	@SuppressWarnings("deprecation")
	public boolean isOnline()
	{
		ConnectivityManager connectivity = (ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) 
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) 
				for (int i = 0; i < info.length; i++) 
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
		}
		return false;
	}

	public void showAlertForInternet()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		alertDialog.setTitle("Internet settings");
		alertDialog.setMessage("You need proper internet connection!");
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog,int which) 
			{
				Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
				context.startActivity(intent);
			}
		});
		alertDialog.show();
	}

	public void showAlertWithTitle(String title, String strMsg)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		alertDialog.setTitle(title);
		alertDialog.setMessage(strMsg);

		alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.cancel();
			}
		});
		alertDialog.show();
	}

	public void showGPSSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle("SETTINGS");
		alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(intent);
			}
		});
		alertDialog.show();
	}


	@SuppressLint("SimpleDateFormat") 
	public String formatDateToString(Date date, String format,String timeZone) 
	{
		if (date == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) 
		{
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));

		return sdf.format(date);
	}

	public String getDate(long timeStamp){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date netDate = (new Date(timeStamp));
		return sdf.format(netDate);
	} 

	public String resizeImage(String path,int DESIREDWIDTH, int DESIREDHEIGHT) 
	{
		String strMyImagePath = null;
		Bitmap scaledBitmap = null;

		try {
			// Part 1: Decode image
			Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);

			if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
				// Part 2: Scale image
				scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
			} else {
				unscaledBitmap.recycle();
				return path;
			}

			// Store to tmp file

			String extr = Environment.getExternalStorageDirectory().toString();
			File mFolder = new File(extr + "/TMMFOLDER");
			if (!mFolder.exists()) {
				mFolder.mkdir();
			}

			String s =path.substring(path.lastIndexOf("/")+1);

			File f = new File(mFolder.getAbsolutePath(), s);

			strMyImagePath = f.getAbsolutePath();
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}

			scaledBitmap.recycle();
		} catch (Throwable e) {
		}

		if (strMyImagePath == null) {
			return path;
		}
		return strMyImagePath;

	}

	public String readInputStreamToString(HttpURLConnection connection) {
		String result = null;
		StringBuffer sb = new StringBuffer();
		InputStream is = null;

		try {
			is = new BufferedInputStream(connection.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String inputLine = "";
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			result = sb.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		finally {
			if (is != null) {
				try { 
					is.close(); 
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}   
		}

		return result;
	}

	@SuppressLint("NewApi") 
	public Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
	{
		Bitmap bitmap = null;
		MediaMetadataRetriever mediaMetadataRetriever = null;
		try
		{
			mediaMetadataRetriever = new MediaMetadataRetriever();
			if (Build.VERSION.SDK_INT >= 14)
				mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
			else
				mediaMetadataRetriever.setDataSource(videoPath);
			//   mediaMetadataRetriever.setDataSource(videoPath);
			bitmap = mediaMetadataRetriever.getFrameAtTime();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Throwable(
					"Exception in retriveVideoFrameFromVideo(String videoPath)"
							+ e.getMessage());
		}
		finally
		{
			if (mediaMetadataRetriever != null)
			{
				mediaMetadataRetriever.release();
			}
		}
		return bitmap;
	}
}
