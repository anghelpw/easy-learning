package ro.ase.ism.login;

import ro.ase.ism.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AccountDetails extends Activity {

	Button linkSignIn;
	TextView username, email, time;

	/**
	 * Called when the activity is first created
	 **/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_details);

		/**
		 * Displays the registration details in Text view
		 **/

		linkSignIn = (Button) findViewById(R.id.btnLinkSignIn);
		linkSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent signInActivity = new Intent(v.getContext(), SignIn.class);
				startActivityForResult(signInActivity, 0);
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		backButtonHandler();
	}

	private void backButtonHandler() {
		Intent signInActivity = new Intent(getApplicationContext(), SignIn.class);
		startActivityForResult(signInActivity, 0);
		finish();
	}

}
