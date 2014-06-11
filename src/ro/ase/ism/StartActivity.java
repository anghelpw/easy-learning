package ro.ase.ism;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		Thread startActivity = new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					onPause();
					startActivity(new Intent("ro.ase.ism.login.LoginActivity"));
				}
			}
		};
		startActivity.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}

}