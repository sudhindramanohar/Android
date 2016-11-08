package com.bSecure;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bSecure.R;

public class Reg extends Activity {
	EditText name, uname, email;
	SharedPreferences regpage;
	SharedPreferences.Editor edit;
	SharedPreferences asetting;
	SharedPreferences.Editor editor;
	SharedPreferences prefs;
	Button addbt;
	String regexStr = "^[0-9]{10}$";
	String emailstr = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_screen);
		SharedPreferences wmbPreference = PreferenceManager
				.getDefaultSharedPreferences(this);
		// boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (!prefs.getBoolean("firstTime", false)) {
			// run your one time code here

			asetting = getSharedPreferences("signal", 0);
			editor = asetting.edit();
			editor.putString("gmsg", "Hi I m saafe !");
			editor.putString("ymsg", "Hi I m in critical situation !");
			editor.putString("rmsg", "Hi I m in trouble, please help me !");
			editor.putInt("gtime", 0);
			editor.putInt("ytime", 0);
			editor.putInt("rtime", 0);
			editor.commit();

			regpage = getSharedPreferences("reg", 0);
			edit = regpage.edit();
			// final DBAdapter dba = new DBAdapter(this);
			name = (EditText) findViewById(R.id.name);
			uname = (EditText) findViewById(R.id.uname);

			email = (EditText) findViewById(R.id.email);
			addbt = (Button) findViewById(R.id.button1);

			addbt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					try {

						// Toast.makeText(Reg.this, "hello",
						// Toast.LENGTH_LONG).show();

						if (name.getText().toString().equals("")
								|| uname.getText().toString().equals("")
								|| email.getText().toString().equals("")) {
							Toast.makeText(Reg.this, "Please Fill All Data",
									Toast.LENGTH_SHORT).show();

						} else

						{
							SharedPreferences.Editor editor = prefs.edit();
							editor.putBoolean("firstTime", true);
							editor.commit();
							if (uname.getText().toString().matches(regexStr)) {
								if (email.getText().toString()
										.matches(emailstr)) {

									/*
									 * dba.open();
									 * 
									 * 
									 * dba.insertUserdetail(name.getText().toString
									 * (
									 * ),uname.getText().toString(),pas.getText(
									 * ).toString(),cpas.getText().toString());
									 * dba.close();
									 */
									edit.putString("name", name.getText()
											.toString());
									edit.putString("mobile", uname.getText()
											.toString());
									edit.putString("email", email.getText()
											.toString());
									edit.commit();

									showMessage();

									Intent intent_first = new Intent(Reg.this,
											wid_act_setting.class);
									startActivity(intent_first);

									finish();

								} else {
									Toast.makeText(Reg.this,
											"Enter Valid Email Address",
											Toast.LENGTH_SHORT).show();
								}
							}

							else {
								Toast.makeText(Reg.this,
										"Invalid Mobile number",
										Toast.LENGTH_LONG).show();
							}
						}

					} catch (Exception e) {
						System.out.println("com.ems.AddNew" + e.getMessage());
					}

				}
			});

		} else {
			Intent in = new Intent(getApplicationContext(),
					wid_act_setting.class);
			startActivity(in);
			finish();
		}

	}

	public void showMessage() {
		Toast.makeText(this, "Data Succesfully saved", Toast.LENGTH_LONG)
				.show();
	}
}
