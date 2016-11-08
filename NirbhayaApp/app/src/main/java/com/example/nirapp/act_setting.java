package com.example.nirapp;

import java.util.ArrayList;

import com.google.android.gms.ads.a;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

public class act_setting extends FragmentActivity {
	Context con = this;
	SharedPreferences asetting,prefs;
	SharedPreferences.Editor edit,sedit;
	EditText edtmsg, edtcontact;
	Button btnsave, btnsetmsg, btnh, btnm, bt, btnsake;
	Integer sec = 0, secm, mh, gsec, ysec, rsec, hour = 0, minute = 0, th, tm;
	String phoneNumber, gvalue, yvalue, rvalue, total;
	ListView lv;
	ArrayList<String> strarr;
	ArrayAdapter<String> adapter;
	String[] pno = new String[7];
	int counter = 0, xc = 0;
	database db;
	private static boolean shakeimg = true;
	Cursor phoneCursor;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.setting);
		db = new database(con);
		bt = (Button) findViewById(R.id.btnaddcnt);
		lv = (ListView) findViewById(R.id.liscontact);
		btnsave = (Button) findViewById(R.id.btnsaveg);
		btnsetmsg = (Button) findViewById(R.id.btnsetmsg);
		btnh = (Button) findViewById(R.id.btnhour);
		btnm = (Button) findViewById(R.id.btnminute);
		btnsake = (Button) findViewById(R.id.btnshake);

		Intent i = getIntent();
		strarr = new ArrayList<String>();
		String[] getph = new String[3];
		adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, strarr);
		lv.setAdapter(adapter);
		asetting = getSharedPreferences("signal", 0);
		String msg = i.getStringExtra("signal");
		int flg = i.getFlags();
		Log.v("flag", String.valueOf(flg));
		Log.v("msg", msg);
		edit = asetting.edit();
		prefs =getSharedPreferences("shake", 0);
		sedit=prefs.edit();
		btnsake.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (prefs.getBoolean("shake", false)){
					sedit.putBoolean("shake", true);
					sedit.putString("shakeon", "Disable");
					sedit.commit();
					btnsake.setText(prefs.getString("shakeon", ""));
					startService(new Intent(act_setting.this,
							ShakeWakeupService.class));
				} else {
					sedit.putBoolean("shake", false);
					sedit.putString("shakeon", "Enable");
					sedit.commit();
					btnsake.setText("Enable");
					stopService(new Intent(act_setting.this,
							ShakeWakeupService.class));

				}
			}
		});

		// Green Signal
		if (msg.equals("green")) {
			listcall("green");
			int GHour = asetting.getInt("GHour", 0);

			if (GHour == 0) {
				btnh.setText("Set Hour");
			} else {
				btnh.setText(asetting.getInt("GHour", 0) + " " + "Hour");
			}

			int GMin = asetting.getInt("GMin", 0);

			if (GMin == 0) {
				btnm.setText("Set Minute");
			} else {
				btnm.setText(asetting.getInt("GMin", 0) + " " + "Minute");
			}
			btnsetmsg.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					setMessage("green");
				}
			});
			bt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int i = strarr.size();
					if (i == 3 || i > 3) {
						Toast.makeText(getApplicationContext(),
								"Maximum 3 Added !", Toast.LENGTH_SHORT).show();
					} else {

						Intent intent = new Intent(Intent.ACTION_PICK,
								ContactsContract.Contacts.CONTENT_URI);
						startActivityForResult(intent, 2);
						// counter++;
					}
				}
			});
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int index, long arg3) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(con);
					builder.setTitle("Hello User");
					builder.setMessage("Are you want delete contact ?");

					builder.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String s = strarr.get(index).toString();
									String in = String.valueOf(s.charAt(0));
									db.deletContact(in, "green");
									listcall("green");
								}
							})
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).show();

				}

			});

			btnh.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openDialog("hour", "green");

				}
			});

			btnm.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openDialog("minute", "green");

				}
			});

			btnsave.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					if (strarr.size() > 0) {

						gsec = getTime("green");
						if (gsec == 0) {
							Toast.makeText(
									getApplicationContext(),
									"You select Application Default time for send Message!",
									Toast.LENGTH_SHORT).show();
						}
						edit.putInt("gtime", gsec);
						edit.commit();

						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"please add contact!", Toast.LENGTH_SHORT)
								.show();
					}

				}
			});

		}

		// Yellow Signal
		if (msg.equals("yellow")) {
			listcall("yellow");
			int YHour = asetting.getInt("YHour", 0);

			if (YHour == 0) {
				btnh.setText("Set Hour");
			} else {
				btnh.setText(asetting.getInt("YHour", 0) + " " + "Hour");
			}

			int YMin = asetting.getInt("YMin", 0);

			if (YMin == 0) {
				btnm.setText("Set Minute");
			} else {
				btnm.setText(asetting.getInt("YMin", 0) + " " + "Minute");
			}
			btnsetmsg.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					setMessage("yellow");
				}
			});

			bt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					int i = strarr.size();
					if (i == 5 || i > 5) {
						Toast.makeText(getApplicationContext(),
								"Maximum 5 Added !", Toast.LENGTH_SHORT).show();
					} else {

						Intent intent = new Intent(Intent.ACTION_PICK,
								ContactsContract.Contacts.CONTENT_URI);
						startActivityForResult(intent, 4);
						// counter++;
					}
				}
			});

			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int index, long arg3) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(con);
					builder.setTitle("Hello User");
					builder.setMessage("Are you want delete contact ?");

					builder.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String s = strarr.get(index).toString();
									Log.v("s", s);
									String in = String.valueOf(s.charAt(0));

									db.deletContact(in, "yellow");
									listcall("yellow");

								}
							})
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).show();

				}

			});
			btnh.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openDialog("hour", "yellow");
				}
			});

			btnm.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openDialog("minute", "yellow");

				}
			});

			btnsave.setOnClickListener(new View.OnClickListener() {
				// edttime.getSelectedItem().toString();
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					if (strarr.size() > 0) {

						ysec = getTime("yellow");
						if (ysec == 0) {
							Toast.makeText(
									getApplicationContext(),
									"You select Application Default time for send Message!",
									Toast.LENGTH_SHORT).show();
						}
						edit.putInt("ytime", ysec);
						edit.commit();
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"please add contact!", Toast.LENGTH_SHORT)
								.show();
					}
				}
			});
		}

		// Red Signal
		if (msg.equals("red")) {
			listcall("red");
			int RHour = asetting.getInt("RHour", 0);

			if (RHour == 0) {
				btnh.setText("Set Hour");
			} else {
				btnh.setText(asetting.getInt("RHour", 0) + " " + "Hour");
			}

			int RMin = asetting.getInt("RMin", 0);

			if (RMin == 0) {
				btnm.setText("Set Minute");
			} else {
				btnm.setText(asetting.getInt("RMin", 0) + " " + "Minute");
			}
			Log.v("msg", "red");
			btnsetmsg.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					setMessage("red");
				}
			});
			bt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int i = strarr.size();
					if (i == 7 || i > 7) {
						Toast.makeText(getApplicationContext(),
								"Maximum 7 Added !", Toast.LENGTH_SHORT).show();
					} else {

						Intent intent = new Intent(Intent.ACTION_PICK,
								ContactsContract.Contacts.CONTENT_URI);
						startActivityForResult(intent, 6);
						// counter++;
					}
				}
			});

			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int index, long arg3) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(con);
					builder.setTitle("Hello User");
					builder.setMessage("Are you want delete contact ?");

					builder.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String s = strarr.get(index).toString();
									String in = String.valueOf(s.charAt(0));
									db.deletContact(in, "red");

									listcall("red");

								}
							})
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).show();

				}

			});

			btnh.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openDialog("hour", "red");
				}
			});

			btnm.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openDialog("minute", "red");

				}
			});
			btnsave.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stu

					if (strarr.size() > 0) {
						rsec = getTime("red");
						if (rsec == 0) {
							Toast.makeText(
									getApplicationContext(),
									"You select Application Default time for send Message!",
									Toast.LENGTH_LONG).show();
						}
						edit.putInt("rtime", rsec);
						edit.commit();
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"please add contact!", Toast.LENGTH_SHORT)
								.show();
					}
				}
			});
		}
	}

	private int getTime(String sig) {

		String m = minute.toString();

		if (sig.equals("green")) {
			hour = asetting.getInt("GHour", 0);
			minute = asetting.getInt("GMin", 0);
		}
		if (sig.equals("yellow")) {
			hour = asetting.getInt("YHour", 0);
			minute = asetting.getInt("YMin", 0);
		}
		if (sig.equals("red")) {
			hour = asetting.getInt("RHour", 0);
			minute = asetting.getInt("RMin", 0);
		}

		if (hour > 0) {
			Log.v("h", hour.toString());
			mh = hour * 60;
			Log.v("h", mh.toString());
			sec = mh * 60;
		} else {
			sec = 0;
		}

		if (minute > 0) {
			Log.v("m", minute.toString());
			secm = minute * 60;
			// Toast.makeText(con, secm.toString(), Toast.LENGTH_SHORT).show();
			Log.v("sm", secm.toString());
			sec += secm;
			Log.v("s", sec.toString());
		} else {
			sec += 0;
		}
		String s = sec.toString();
		Toast.makeText(con, s, Toast.LENGTH_SHORT).show();

		return sec;
	}

	@Override
	protected void onActivityResult(int reqCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (2):
			setcnt(reqCode, resultCode, data, "green");
			break;
		case (4):
			setcnt(reqCode, resultCode, data, "yellow");
			break;
		case (6):
			setcnt(reqCode, resultCode, data, "red");
			break;
		}
	}

	private void setcnt(int reqCode, int resultCode, Intent data, String signal) {
		String sig = null;

		if (resultCode == Activity.RESULT_OK) {
			String s = data.getData().toString();
			// Integer n=reqCode;
			// Log.v("rc",n.toString());
			Uri contactData = data.getData();

			@SuppressWarnings("deprecation")
			Cursor c = managedQuery(contactData, null, null, null, null);

			if (c.moveToFirst()) {
				// other data is available for the Contact. I have decided
				// to only get the name of the Contact.
				try {
					String name = c
							.getString(c
									.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
					Log.v("nam", name);
					int hasPhoneNumber = Integer
							.parseInt(c.getString(c
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

					String contact_id = c.getString(c
							.getColumnIndex(ContactsContract.Contacts._ID));
					Log.v("hn", String.valueOf(hasPhoneNumber));
					if (hasPhoneNumber > 0) {

						// Query and loop for every phone number of the contact

						phoneCursor = managedQuery(
								ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
								null,
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID
										+ " = ?", new String[] { contact_id },
								null);

						if (phoneCursor.moveToFirst()) {
							phoneNumber = phoneCursor
									.getString(phoneCursor
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							Log.v("Phone number:", phoneNumber);

						}

						try {
							if (signal.equals("green")) {
								Log.v("d", "c");
								db.addContact(new nir_data(phoneNumber),
										"green");
								sig = "green";
							}
							if (signal.equals("yellow")) {
								Log.v("d", "c");
								db.addContact(new nir_data(phoneNumber),
										"yellow");
								sig = "yellow";
							}
							if (signal.equals("red")) {
								Log.v("d", "c");
								db.addContact(new nir_data(phoneNumber), "red");
								sig = "red";
							}
						} catch (Exception e) {
							Toast.makeText(act_setting.this,
									"Date Insert Failed", Toast.LENGTH_SHORT)
									.show();
							Log.v("data", "fail");
						}
						// phoneCursor.close();

						phoneNumber = null;

						Toast.makeText(getApplicationContext(), name,
								Toast.LENGTH_SHORT).show();

					} else {
						xc = 1;
						Log.v("no", "number");
						Toast.makeText(getApplicationContext(),
								"No number Found !", Toast.LENGTH_SHORT).show();
					}
				}

				catch (Exception e) {
					xc = 1;
					Log.v("catch", String.valueOf(xc));
					Log.v("no", "number");
					Toast.makeText(getApplicationContext(),
							"No number Found !", Toast.LENGTH_SHORT).show();
				}
			}

		}

		Log.v("acatch", String.valueOf(xc));
		if (xc == 1) {
			Toast.makeText(getApplicationContext(), "No number Found !",
					Toast.LENGTH_SHORT).show();
			xc = 0;
			Log.v("concatch", String.valueOf(xc));
		} else {
			String str = null;
			Cursor c1 = db.getContact(signal);

			if (c1.getCount() > 0) {
				if (c1.moveToLast()) {
					// for(c1.moveToFirst(); !c1.isAfterLast();c1.moveToNext()){
					// Log.v("con",c1.getString(1).toString());
					// }
					int s = strarr.size() - 1;
					if (s >= 0) {
						str = strarr.get(s).toString();
						Log.v("last", str);
					}

					if (Integer.parseInt(c1.getString(0)) > 0) {
						if (s >= 0) {
							if (str.equals(c1.getString(0) + "   "
									+ c1.getString(1))) {
								Log.v("no", "no selected");
							} else {
								strarr.add(c1.getString(0) + "   "
										+ c1.getString(1));
								adapter.notifyDataSetChanged();
							}
						} else {
							strarr.add(c1.getString(0) + "   "
									+ c1.getString(1));
							adapter.notifyDataSetChanged();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Add only Phone contact", Toast.LENGTH_SHORT)
								.show();
					}
				}
			} else {
				Log.v("data", "not found");
			}
		}

	}

	// Number Picker
	private void openDialog(final String tim, final String sig) {
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setTitle("Hello User");
		if (tim.equals("hour")) {
			builder.setMessage("Set Hour");
		}

		if (tim.equals("minute")) {
			builder.setMessage("Set Minute");
		}

		final NumberPicker np = new NumberPicker(con);
		np.setId(0);
		np.setMinValue(0);

		if (tim.equals("hour")) {
			np.setMaxValue(12);
		}
		if (tim.equals("minute")) {
			np.setMaxValue(59);
		}

		builder.setView(np);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				if (tim.equals("hour")) {

					hour = np.getValue();
					if (sig.equals("green")) {

						edit.putInt("GHour", hour);
					}
					if (sig.equals("yellow")) {

						edit.putInt("YHour", hour);
					}
					if (sig.equals("red")) {

						edit.putInt("RHour", hour);
					}
					if (hour > 0) {
						btnh.setText(hour.toString() + " " + "Hour");
					} else {
						btnh.setText("Set Hour");
					}

				}
				edit.commit();

				if (tim.equals("minute")) {
					minute = np.getValue();
					if (sig.equals("green")) {
						edit.putInt("GMin", minute);
					}
					if (sig.equals("yellow")) {
						edit.putInt("YMin", minute);
					}
					if (sig.equals("red")) {
						edit.putInt("RMin", minute);
					}
					if (minute > 0) {
						btnm.setText(minute.toString() + " " + "Minute");
					} else {
						btnm.setText("Set Minute");
					}
				}

				edit.commit();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (tim.equals("hour")) {
					// hour=0;
					if (sig.equals("green")) {
						hour = asetting.getInt("GHour", 0);
						edit.putInt("GHour", hour);
					}
					if (sig.equals("yellow")) {
						hour = asetting.getInt("YHour", 0);
						edit.putInt("YHour", hour);
					}
					if (sig.equals("red")) {
						hour = asetting.getInt("RHour", 0);
						edit.putInt("RHour", hour);
					}
					// btnh.setText("Set Hour");

				}

				if (tim.equals("minute")) {
					// minute=0;
					if (sig.equals("green")) {
						minute = asetting.getInt("GMin", 0);
						edit.putInt("GMin", minute);
					}
					if (sig.equals("yellow")) {
						minute = asetting.getInt("YMin", 0);
						edit.putInt("YMin", minute);
					}
					if (sig.equals("red")) {
						minute = asetting.getInt("RMin", 0);
						edit.putInt("RMin", minute);
					}

					// btnm.setText("Set Minute");
				}
				edit.commit();
			}
		}).show();

	}

	// Dialog For Set Message
	private void setMessage(String signal) {

		if (signal.equals("green")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(con);
			builder.setTitle("Hello User");
			builder.setMessage("Set Green Signal Message");

			final EditText input = new EditText(con);
			input.setId(0);
			input.setText(asetting.getString("gmsg", ""));
			builder.setView(input);

			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							gvalue = input.getText().toString();
							edit.putString("gmsg", gvalue);
							Log.d("TAG", "User name: " + gvalue);
							edit.commit();
						}
					})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();

		}

		if (signal.equals("yellow")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(con);
			builder.setTitle("Hello User");
			builder.setMessage("Set Yellow Signal Message");

			final EditText input = new EditText(con);
			input.setId(0);
			input.setText(asetting.getString("ymsg", ""));
			builder.setView(input);

			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							yvalue = input.getText().toString();
							edit.putString("ymsg", yvalue);
							Log.d("TAG", "User name: " + yvalue);
							edit.commit();
						}
					})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();

		}

		if (signal.equals("red")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(con);
			builder.setTitle("Hello User");
			builder.setMessage("Set Red Signal Message");

			final EditText input = new EditText(con);
			input.setId(0);
			input.setText(asetting.getString("rmsg", ""));
			builder.setView(input);

			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							rvalue = input.getText().toString();
							edit.putString("rmsg", rvalue);
							Log.d("TAG", "User name: " + rvalue);
							edit.commit();
						}
					})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();

		}
	}

	public void listcall(String signal) {
		Cursor c1 = null;
		strarr = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, strarr);
		lv.setAdapter(adapter);
		Log.v("sur", "com");
		db = new database(con);
		Log.v("sur", "com");
		if (signal.equals("green")) {
			Log.v("sur", "comg");
			c1 = db.getContact("green");
		}
		if (signal.equals("yellow")) {
			c1 = db.getContact("yellow");
		}
		if (signal.equals("red")) {
			c1 = db.getContact("red");
		}
		String s = c1.toString();
		Log.v("c1", s);
		if (c1.getCount() > 0) {
			if (c1.moveToLast()) {
				for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {

					if (Integer.parseInt(c1.getString(0)) > 0) {
						strarr.add(c1.getString(0) + "   " + c1.getString(1));
						adapter.notifyDataSetChanged();
					} else {
						Log.v("list", "not found!");
					}

				}

			}
		} else {
			Log.v("data", "not found");
		}
	}
}
