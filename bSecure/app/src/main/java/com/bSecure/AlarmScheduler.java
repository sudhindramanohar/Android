package com.bSecure;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.SystemClock;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmScheduler {

    static AlarmManager alarmManager;
    static PendingIntent pendingAlarmIntent;
    static SharedPreferences appSettingSharedPref;
    static String sig_msg;
    static Integer greenTime, yellowTime, redTime;
    static String greenMessage;
    static String ymsg;
    static String redMessage;
    static Database database;
    static SharedPreferences.Editor edit;
    static int xc = 0, start = 0;
    static Context context;

    public AlarmScheduler() {
        // TODO Auto-generated constructor stub
    }

    static void scheduleAlarms(Context localContext, String msg) {
        SmsManager sms = SmsManager.getDefault();
        context = localContext;
        appSettingSharedPref = localContext.getSharedPreferences("signal", 0);
        edit = appSettingSharedPref.edit();
        String sm;
        database = new Database(localContext);
        Log.v("contxt", String.valueOf(localContext));
        alarmManager = (AlarmManager) localContext.getSystemService(Context.ALARM_SERVICE);
        // GREEN
        if (msg.equals("green")) {

            set_msg("green");
            sm = get_msg();
            Log.v("hi", "gcreate" + sm);
            greenMessage = appSettingSharedPref.getString("gmsg1", "");
            String g = greenMessage;
            String s = appSettingSharedPref.getString("greenMessage", "") + " " + g;

            Log.v("greenMessage", s);

            // Toast.makeText(ctxt, greenMessage, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(localContext, AudioRecorder.class);
            // add infos for the service which file to download and where to
            // store
            intent.putExtra("audio", "loc");
            localContext.startService(intent);
            Cursor c1 = database.getContact("green");
            if (c1.getCount() > 0) {
                if (c1.moveToLast()) {
                    for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
                        Log.v("context", c1.getString(1));
                        try {
                            Log.v("msg", c1.getString(1));
                        } catch (Exception e) {
                            Log.v("msg", "send fail!");
                        }
                    }
                }
            } else {
                Log.v("data", "not found");
                Intent ii = new Intent(localContext, ApplicationSettings.class);
                ii.putExtra("signal", "green");
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localContext.startActivity(ii);
                xc = 1;
            }
            database.close();
            Intent i = new Intent(localContext, MessageSender.class);
            Log.v("hi", "gcreate");
            pendingAlarmIntent = PendingIntent.getService(localContext, 0, i, 0);
            greenTime = appSettingSharedPref.getInt("gtime", 0);
            Log.v("greenTime", greenTime.toString());
            if (xc == 1) {
                alarmManager.set(AlarmManager.ELAPSED_REALTIME, 0, pendingAlarmIntent);
                xc = 0;
            } else {
                if (greenTime == 0) {
                    Log.v("set", "time");
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                            SystemClock.elapsedRealtime() + 5000, 5000,
                            pendingAlarmIntent);
                } else {
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                            SystemClock.elapsedRealtime() + 5000, greenTime * 1000,
                            pendingAlarmIntent);
                }
            }
        }

        // YELLOW MODE
        if (msg.equals("yellow")) {
            set_msg("yellow");
            sm = get_msg();
            Intent i = new Intent(localContext, MessageSender.class);
            Log.v("hi", "ycreate " + msg + sm);
            /*
			 * Intent intent = new Intent(ctxt,AudioRecorder.class); // add infos for
			 * the service which file to download and where to store
			 * intent.putExtra("audio", "loc"); ctxt.startService(intent);
			 */
            LocationFinder gl = new LocationFinder();
            gl.getlc(localContext, "yellow");
            ymsg = appSettingSharedPref.getString("ymsg1", "");
            String g = ymsg;
            String s = appSettingSharedPref.getString("ymsg", "") + " " + g;
            Log.v("ymsg1", s);
            Cursor c1 = database.getContact("yellow");
            if (c1.getCount() > 0) {
                if (c1.moveToLast()) {
                    for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
                        Log.v("context", c1.getString(1));
                        try {
                            sms.sendTextMessage(c1.getString(1), null, s, null,
                                    null);
                            Log.v("msg", c1.getString(1));
                            String timeStamp = new SimpleDateFormat("HHmmss")
                                    .format(new Date());
                            Integer t = Integer.parseInt(String
                                    .valueOf(timeStamp.charAt(2))
                                    + String.valueOf(timeStamp.charAt(3)));
                            Log.v("mm", t.toString());
                            Log.v("msg", timeStamp);
                            edit.putInt("starttime", t);
                            edit.commit();
                        } catch (Exception e) {
                            Log.v("msg", "send fail!");
                        }
                    }
                }
            } else {
                Intent ii = new Intent(localContext, ApplicationSettings.class);
                ii.putExtra("signal", "yellow");
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localContext.startActivity(ii);
                xc = 1;
                Log.v("data", "not found");
            }
            database.close();
            pendingAlarmIntent = PendingIntent.getService(localContext, 0, i, 0);
            if (xc == 1) {
                alarmManager.set(AlarmManager.ELAPSED_REALTIME, 0, pendingAlarmIntent);
                edit.putString("cset", "false");
                edit.commit();
                xc = 0;
            } else {
                edit.putString("cset", "true");
                edit.commit();
                yellowTime = appSettingSharedPref.getInt("ytime", 0);
                if (yellowTime == 0) {
                    Log.v("set", "time");
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                            SystemClock.elapsedRealtime() + 5000, 600 * 1000,
                            pendingAlarmIntent);
                } else {
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                            SystemClock.elapsedRealtime() + 5000, yellowTime * 1000,
                            pendingAlarmIntent);
                }
            }
        }

        // RED MODE
        if (msg.equals("red")) {
            set_msg("red");
            sm = get_msg();
            Intent intent1 = new Intent(localContext, AudioRecorder.class);
            // add infos for the service which file to download and where to
            // store
            intent1.putExtra("audio", "loc");
            localContext.startService(intent1);
            localContext.stopService(new Intent(localContext, AudioRecorder.class));

            Intent intent = new Intent(localContext, AudioRecorder.class);
            // add infos for the service which file to download and where to
            // store
            intent.putExtra("audio", "start");
            start = 1;
            localContext.startService(intent);

            Intent i = new Intent(localContext, MessageSender.class);
            Log.v("hi", "rcreate " + msg + sm);
            redMessage = appSettingSharedPref.getString("rmsg1", "");
            String g = redMessage;
            String s = appSettingSharedPref.getString("redMessage", "") + " " + g;
            Log.v("red", s);

            Cursor c1 = database.getContact("red");
            if (c1.getCount() > 0) {
                if (c1.moveToLast()) {
                    for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
                        Log.v("context", c1.getString(1));
                        try {
                            Log.v("msg", c1.getString(1));
                        } catch (Exception e) {
                            Log.v("msg", "send fail!");
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Add contact in application_settings page",
                        Toast.LENGTH_SHORT).show();
                Intent ii = new Intent(context, ApplicationSettings.class);
                ii.putExtra("signal", "red");
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);
                xc = 1;
                Log.v("data", "not found");
            }
            database.close();
            // r.startRec();
            Log.v("com", "after set no");
            pendingAlarmIntent = PendingIntent.getService(localContext, 0, i, 0);
            if (xc == 1) {
                Log.v("red", "come");
                alarmManager.set(AlarmManager.ELAPSED_REALTIME, 0, pendingAlarmIntent);
                xc = 0;
            } else {
                redTime = appSettingSharedPref.getInt("rtime", 0);
                if (redTime == 0) {
                    Log.v("set", "time");
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                            SystemClock.elapsedRealtime() + 5000, 900 * 1000,
                            pendingAlarmIntent);
                } else {
                    Log.v("set", "time");
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                            SystemClock.elapsedRealtime() + 5000, redTime * 1000,
                            pendingAlarmIntent);
                }
            }
        }
    }

    static public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmManager != null) {
            alarmManager.cancel(pendingAlarmIntent);
            context.stopService(new Intent(context, AudioRecorder.class));
        }
    }

    static public void cancelRAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmManager != null) {
            Log.v("v", "com");
            alarmManager.cancel(pendingAlarmIntent);

            context.stopService(new Intent(context, AudioRecorder.class));
            if (start == 1) {
                AudioRecorder.stopRec();
                start = 0;
            }
        }
    }

    static public void set_msg(String msg) {
        sig_msg = msg;

    }

    static public String get_msg() {
        return sig_msg;
    }
}
