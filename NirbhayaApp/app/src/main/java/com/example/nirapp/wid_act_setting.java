package com.example.nirapp;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class wid_act_setting extends FragmentActivity implements
		ActionBar.TabListener {

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	ViewPager mViewPager;
	static Context con;
	static Intent intent;
	static Intent ig;
	static Intent iy;
	static Intent ir;
	static ImageButton ibg;
	static ImageButton iby;
	static ImageButton ibr;
	static TextView tg, ty, tr;
	static SharedPreferences mode;
	static SharedPreferences.Editor editor;
	public static final String PREFS_NAME = "MyPrefs";

	static WidgetReceiver wm = new WidgetReceiver();
	// static SharedPreferences settings;

	static getlocation gl = new getlocation();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// settings = getSharedPreferences("set_signal", 0);

		mode = getSharedPreferences(PREFS_NAME, 0);
		editor = mode.edit();
		try {
			Intent intent = new Intent(getApplicationContext(), audiorec.class);
			// add infos for the service which file to download and where to
			// store
			intent.putExtra("audio", "loc");

			startService(intent);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "not found",
					Toast.LENGTH_SHORT).show();
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Hello User");
		builder.setMessage(R.string.help);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		}).show();

		con = this;
		intent = getIntent();

		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager());

		final ActionBar actionBar = getActionBar();

		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {

						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {

			actionBar.addTab(actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					// .setIcon(R.drawable.ic_launcher)
					.setTabListener(this));
		}

		ig = new Intent(wid_act_setting.this, WidgetReceiver.class);
		ig.setAction("CHANGE_PICTUREG");

		iy = new Intent(wid_act_setting.this, WidgetReceiver.class);
		iy.setAction("CHANGE_PICTUREY");

		ir = new Intent(wid_act_setting.this, WidgetReceiver.class);
		ir.setAction("CHANGE_PICTURER");

	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:

				return new greenFragment();
			case 1:

				return new yellowFragment();

			case 2:

				return new redFragment();

			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String title = null;
			if (position == 0) {
				title = "GREEN";
			}
			if (position == 1) {
				title = "YELLOW";
			}
			if (position == 2) {
				title = "RED";
			}
			return title;
		}
	}

	/**
	 * A fragment that launches other parts of the demo application.
	 */
	public static class greenFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			final View rootView = inflater.inflate(R.layout.fragment_green,
					container, false);
			tg = (TextView) rootView.findViewById(R.id.txtgreenabt);
			tg.setVisibility(rootView.INVISIBLE);
			ibg = (ImageButton) rootView.findViewById(R.id.imggreen);
			

			if (!mode.getBoolean("gimage", false)) {
				ibg.setBackgroundResource(R.drawable.greenicon);

			} else {
				ibg.setBackgroundResource(R.drawable.pausegreen);

			}

			rootView.findViewById(R.id.imggreen).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {

							if (mode.getBoolean("gimage", false)) {
								Log.v("in App image green true",
										"" + mode.getBoolean("gimage", false));

								ibg.setBackgroundResource(R.drawable.greenicon);

							} else {

								if (mode.getBoolean("yimage", false)) {

									iby.setBackgroundResource(R.drawable.yellowicon);

								}
								if (mode.getBoolean("rimage", false)) {
									ibr.setBackgroundResource(R.drawable.redicon);
								}

								ibg.setBackgroundResource(R.drawable.pausegreen);
							}
							wm.onReceive(con, ig);
						}

					});

			rootView.findViewById(R.id.btnabtgrn).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(con, "abt", Toast.LENGTH_SHORT)
									.show();
							tg.setVisibility(rootView.VISIBLE);
							ibg.setVisibility(rootView.INVISIBLE);
						}
					});

			rootView.findViewById(R.id.btnhomegrn).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(con, "home", Toast.LENGTH_SHORT)
									.show();
							tg.setVisibility(rootView.INVISIBLE);
							ibg.setVisibility(rootView.VISIBLE);
						}
					});

			rootView.findViewById(R.id.btnsettinggrn).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(con, "setting", Toast.LENGTH_SHORT)
									.show();
							Intent i = new Intent(con, act_setting.class);
							i.putExtra("signal", "green");
							startActivity(i);
						}
					});
			return rootView;
		}
	}

	public static class yellowFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_yellow,
					container, false);
			ty = (TextView) rootView.findViewById(R.id.txtyellowabt);
			ty.setVisibility(rootView.INVISIBLE);
			iby = (ImageButton) rootView.findViewById(R.id.imgylw);
			if (!mode.getBoolean("yimage", false)) {
				iby.setBackgroundResource(R.drawable.yellowicon);

			} else {
				iby.setBackgroundResource(R.drawable.pauseyellow);

			}

			// Demonstration of a collection-browsing activity.
			rootView.findViewById(R.id.imgylw).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {

							if (mode.getBoolean("yimage", false)) {

								iby.setBackgroundResource(R.drawable.yellowicon);
							} else {

								Log.v("in App image yellow false",
										"" + mode.getBoolean("yimage", false));
								if (mode.getBoolean("gimage", false)) {

									ibg.setBackgroundResource(R.drawable.greenicon);

								}
								if (mode.getBoolean("rimage", false)) {
									ibr.setBackgroundResource(R.drawable.redicon);
								}

								iby.setBackgroundResource(R.drawable.pauseyellow);
							}
							wm.onReceive(con, iy);
						}

					});

			rootView.findViewById(R.id.btnabtylw).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(con, "abt", Toast.LENGTH_SHORT)
									.show();
							ty.setVisibility(rootView.VISIBLE);
							iby.setVisibility(rootView.INVISIBLE);
							tg.setVisibility(rootView.INVISIBLE);
							tr.setVisibility(rootView.INVISIBLE);
						}
					});

			rootView.findViewById(R.id.btnhomeylw).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(con, "home", Toast.LENGTH_SHORT)
									.show();
							ty.setVisibility(rootView.INVISIBLE);
							iby.setVisibility(rootView.VISIBLE);
						}
					});

			rootView.findViewById(R.id.btnsettingylw).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(con, "setting", Toast.LENGTH_SHORT)
									.show();
							Intent i = new Intent(con, act_setting.class);
							i.putExtra("signal", "yellow");
							startActivity(i);

						}
					});

			return rootView;
		}
	}

	public static class redFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_red,
					container, false);
			tr = (TextView) rootView.findViewById(R.id.txtredabt);
			tr.setVisibility(rootView.INVISIBLE);
			ibr = (ImageButton) rootView.findViewById(R.id.imgred);
			if (!mode.getBoolean("rimage", false)) {
				ibr.setBackgroundResource(R.drawable.redicon);

			} else {
				ibr.setBackgroundResource(R.drawable.pausered);

			}

			// Demonstration of a collection-browsing activity.
			rootView.findViewById(R.id.imgred).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (mode.getBoolean("rimage", false)) {
								Log.v("in App image red true",
										"" + mode.getBoolean("rimage", false));

								ibr.setBackgroundResource(R.drawable.redicon);
							} else {

								if (mode.getBoolean("yimage", false)) {

									iby.setBackgroundResource(R.drawable.yellowicon);

								}
								if (mode.getBoolean("gimage", false)) {
									ibg.setBackgroundResource(R.drawable.greenicon);
								}

								ibr.setBackgroundResource(R.drawable.pausered);
							}
							wm.onReceive(con, ir);
						}
					});

			rootView.findViewById(R.id.btnabtred).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(con, "abt", Toast.LENGTH_SHORT)
									.show();
							tr.setVisibility(rootView.VISIBLE);
							ibr.setVisibility(rootView.INVISIBLE);
							tg.setVisibility(rootView.INVISIBLE);
							//ty.setVisibility(rootView.INVISIBLE);
						}
					});

			rootView.findViewById(R.id.btnhomered).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(con, "home", Toast.LENGTH_SHORT)
									.show();
							tr.setVisibility(rootView.INVISIBLE);
							ibr.setVisibility(rootView.VISIBLE);
						}
					});

			rootView.findViewById(R.id.btnsettingred).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(con, "setting", Toast.LENGTH_SHORT)
									.show();
							Intent i = new Intent(con, act_setting.class);
							i.putExtra("signal", "red");
							startActivity(i);
						}
					});
			return rootView;
		}
	}

}
