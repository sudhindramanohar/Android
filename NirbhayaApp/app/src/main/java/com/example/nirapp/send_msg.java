package com.example.nirapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;

@SuppressWarnings("deprecation")
public class send_msg extends IntentService implements ConnectionCallbacks,
		OnConnectionFailedListener {

	alaram_receiver am;
	String mv, msg, smsg, gmsg, ymsg, rmsg;
	int tim, i = 0, s, counter = 0;
	Context con = this;
	SharedPreferences asetting;
	SharedPreferences.Editor edit;
	static MediaRecorder mRecorder;
	String mFileName;
	String gcnt[] = new String[3];
	MediaPlayer mPlayer;
	boolean rec = false;
	GPSTracker gps;
	static double latitude;
	static double longitude;
	String gm;
	getlocation gl = new getlocation();

	// audiorec r=new audiorec();

	public send_msg() {
		super("send_msg");

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		WidgetReceiver wr = new WidgetReceiver();
		Intent inten = new Intent();
		SmsManager sms = SmsManager.getDefault();
		asetting = con.getSharedPreferences("signal", 0);

		msg = alaram_receiver.get_msg();
		database db = new database(con);

		// Send SMS For Green Signal
		if (msg.equals("green")) {

			getlocation.getlc(con, "green");
			gmsg = asetting.getString("gmsg1", "");
			String s = asetting.getString("gmsg", "");
			Log.v("gmsg", s + " " + gmsg);
			Toast.makeText(con, gmsg, Toast.LENGTH_SHORT).show();
			Cursor c1 = db.getContact("green");
			if (c1.getCount() > 0) {
				if (c1.moveToLast()) {
					for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
						Log.v("con", c1.getString(1));

						try {
							Log.v("msg", c1.getString(1));

							sms.sendTextMessage(c1.getString(1), null, s + " "
									+ gmsg, null, null);
						}

						catch (Exception e) {
							Log.v("msg", "send fail!");
						}

					}

				}
			} else {

				inten.setAction("CHANGE_PICTUREG");
				wr.onReceive(getApplicationContext(), inten);
				Log.v("data", "not found");
			}
			db.close();

		}

		// Send SMS For Yellow Signal
		if (msg.equals("yellow")) {
			// getlc("yellow");
			getlocation.getlc(con, "yellow");
			ymsg = asetting.getString("ymsg1", "");
			String s = asetting.getString("ymsg", "");
			Log.v("ymsg", s + " " + ymsg);
			Cursor c1 = db.getContact("yellow");
			int yt = asetting.getInt("starttime", 0) + 10;
			Log.v("ti", String.valueOf(yt));
			String cget = asetting.getString("cset", "");
			String timeStamp = new SimpleDateFormat("HHmmss")
					.format(new Date());
			Integer t = Integer.parseInt(String.valueOf(timeStamp.charAt(2))
					+ String.valueOf(timeStamp.charAt(3)));
			Log.v("ti", String.valueOf(t));
			if (cget.equals("true")) {
				if (t >= yt) {

					inten.setAction("CHANGE_PICTURER");
					wr.onReceive(getApplicationContext(), inten);

				} else {

				}
			} else {

				inten.setAction("CHANGE_PICTUREY");
				wr.onReceive(getApplicationContext(), inten);
				Log.v("data", "not found");
			}
			db.close();
			// sms.sendTextMessage(ycntact, null, ymsg, null, null);
		}

		// Send SMS For Red Signal
		if (msg.equals("red")) {

			// getlc("red");
			getlocation.getlc(con, "red");
			rmsg = asetting.getString("rmsg1", "");
			String s = asetting.getString("rmsg", "");
			Log.v("rmsg", s + " " + rmsg);
			Cursor c1 = db.getContact("red");
			if (c1.getCount() > 0) {
				if (c1.moveToLast()) {
					for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
						Log.v("con", c1.getString(1));
						try {

							sms.sendTextMessage(c1.getString(1), null, s + " "
									+ rmsg, null, null);
							Log.v("msg", c1.getString(1));
						}

						catch (Exception e) {
							Log.v("msg", "send fail!");
						}
					}

				}
			} else {

				inten.setAction("CHANGE_PICTURER");
				wr.onReceive(getApplicationContext(), inten);
				Log.v("data", "not found");
			}
			db.close();

			// sms.sendTextMessage(rcntact, null, ymsg, null, null);
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

}
