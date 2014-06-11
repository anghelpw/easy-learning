package ro.ase.ism;

import ro.ase.ism.login.LoginActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {

	ImageButton userPanel, closeApp, back, next;
	ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CustomPagerAdapter adapter = new CustomPagerAdapter();
		viewPager = (ViewPager) findViewById(R.id.customViewPager);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);

		back = (ImageButton) findViewById(R.id.btnPreviousView);
		next = (ImageButton) findViewById(R.id.btnNextView);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(getItem(-1), true);
			}

			private int getItem(int i) {
				return viewPager.getCurrentItem() + i;
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(getItem(+1), true);
			}

			private int getItem(int i) {
				return viewPager.getCurrentItem() + i;
			}
		});

		userPanel = (ImageButton) findViewById(R.id.btnUserPanel);
		closeApp = (ImageButton) findViewById(R.id.btnCloseApp);

		userPanel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent userPanel = new Intent(getApplicationContext(),
						UserPanel.class);
				startActivity(userPanel);
			}
		});

		closeApp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
				login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(login);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		// Display alert message when back button has been pressed
		backButtonHandler();
		return;
	}

	public void backButtonHandler() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MainActivity.this);
		alertDialog.setTitle("Leave Easy Learning");
		alertDialog
				.setMessage("Are you sure you want to leave the application?");
		alertDialog.setIcon(R.drawable.camera);
		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();
	}

}
