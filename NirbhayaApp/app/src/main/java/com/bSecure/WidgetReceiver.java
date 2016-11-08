package com.bSecure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bSecure.R;

public class WidgetReceiver extends BroadcastReceiver {

	Context con;

	SharedPreferences mode;
	SharedPreferences mmode;
	SharedPreferences gmode;
	SharedPreferences ymode;
	SharedPreferences rmode;
	public static final String PREFS_NAME = "MyPrefs";
	public static final String PREFS_NAMEM = "Mmode";
	public static final String PREFS_NAMEG = "MyPrefsGFile";
	public static final String PREFS_NAMEY = "MyPrefsYFile";
	public static final String PREFS_NAMER = "MyPrefsRFile";
	SharedPreferences.Editor meditor;
	SharedPreferences.Editor editor;

	@Override
	public void onReceive(Context context, Intent intent) {
		mode = context.getSharedPreferences(PREFS_NAME, 0);
		gmode = context.getSharedPreferences(PREFS_NAMEG, 0);
		ymode = context.getSharedPreferences(PREFS_NAMEY, 0);
		rmode = context.getSharedPreferences(PREFS_NAMER, 0);
		mmode = context.getSharedPreferences(PREFS_NAMEM, 0);
		editor = mode.edit();

		meditor = mmode.edit();
		
		//intent.getAction().equals("");

		if (intent.getAction().equals("CHANGE_PICTUREG")) {

			if (mode.getBoolean("yimage", false)) {
				updateWidgetPictureAndButtonListenery(context);

			}
			if (mode.getBoolean("rimage", false)) {
				updateWidgetPictureAndButtonListenerr(context);
			}
			updateWidgetPictureAndButtonListenerg(context);
		}
		if (intent.getAction().equals("CHANGE_PICTUREY")) {
			if (mode.getBoolean("gimage", false)) {
				updateWidgetPictureAndButtonListenerg(context);
			}
			if (mode.getBoolean("rimage", false)) {
				updateWidgetPictureAndButtonListenerr(context);
			}
			updateWidgetPictureAndButtonListenery(context);
		}
		if (intent.getAction().equals("CHANGE_PICTURER")) {
			if (mode.getBoolean("gimage", false)) {
				updateWidgetPictureAndButtonListenerg(context);
			}
			if (mode.getBoolean("yimage", false)) {
				updateWidgetPictureAndButtonListenery(context);
			}
			updateWidgetPictureAndButtonListenerr(context);
		}
		editor.commit();
	}

	private void updateWidgetPictureAndButtonListenerg(Context context) {
		con = context;
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);
		remoteViews.setImageViewResource(R.id.greenbtn, getImageToSetg());
		remoteViews.setOnClickPendingIntent(R.id.greenbtn,
				WidgetActivity.buildButtonPendingIntentg(context));
		WidgetActivity.pushWidgetUpdate(context.getApplicationContext(),
				remoteViews);

	}

	private void updateWidgetPictureAndButtonListenery(Context context) {
		con = context;
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);
		remoteViews.setImageViewResource(R.id.yellowbtn, getImageToSety());

		remoteViews.setOnClickPendingIntent(R.id.yellowbtn,
				WidgetActivity.buildButtonPendingIntenty(context));
		WidgetActivity.pushWidgetUpdate(context.getApplicationContext(),
				remoteViews);
	}

	private void updateWidgetPictureAndButtonListenerr(Context context) {
		con = context;
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);
		remoteViews.setImageViewResource(R.id.redbtn, getImageToSetr());

		remoteViews.setOnClickPendingIntent(R.id.redbtn,
				WidgetActivity.buildButtonPendingIntentr(context));
		WidgetActivity.pushWidgetUpdate(context.getApplicationContext(),
				remoteViews);
	}

	private int getImageToSetg() {

		if (mode.getBoolean("gimage", false)) {

			Toast.makeText(con, "Green mod off...", Toast.LENGTH_LONG).show();
			alaram_receiver.cancelAlarm(con);

			editor.putBoolean("gimage", false);
			editor.commit();

			return R.drawable.greenicon;

		} else {

			Toast.makeText(con, "Green mod on...", Toast.LENGTH_LONG).show();

			meditor.putString("mode", "green");
			meditor.commit();

			alaram_receiver.scheduleAlarms(con, "green");

			editor.putBoolean("gimage", true);

			editor.commit();

			return R.drawable.pausegreen;
		}
	}

	private int getImageToSety() {
		if (mode.getBoolean("yimage", false)) {

			Toast.makeText(con, "Yellow mod off...", Toast.LENGTH_LONG).show();
			alaram_receiver.cancelAlarm(con);
			Log.v("ymm","fail");
			editor.putBoolean("yimage", false);
			editor.commit();

			return R.drawable.yellowicon;

		} else {

			Toast.makeText(con, "Yellow mod on...", Toast.LENGTH_LONG).show();

			meditor.putString("mode", "yellow");
			meditor.commit();

			alaram_receiver.scheduleAlarms(con, "yellow");

			editor.putBoolean("yimage", true);
			editor.commit();

			return R.drawable.pauseyellow;
		}

	}

	private int getImageToSetr() {
		if (mode.getBoolean("rimage", false)) {

			Toast.makeText(con, "Red mod off...", Toast.LENGTH_LONG).show();
			alaram_receiver.cancelRAlarm(con);

			editor.putBoolean("rimage", false);
			editor.commit();

			return R.drawable.redicon;

		} else {

			Toast.makeText(con, "Red mod on...", Toast.LENGTH_LONG).show();

			meditor.putString("mode", "red");
			meditor.commit();

			alaram_receiver.scheduleAlarms(con, "red");

			editor.putBoolean("rimage", true);
			editor.commit();

			return R.drawable.pausered;
		}
	}
}