package ro.ase.ism.login;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import ro.ase.ism.MainActivity;
import ro.ase.ism.R;
import ro.ase.ism.login.db.UserFunctions;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends Activity {

	/**
	 * JSON Response node names
	 **/

	private static String KEY_SUCCESS = "success";

	/**
	 * Defining layout items
	 **/

	EditText signInEmail, signInPass;
	TextView statusSignIn;
	Button resetPass, signIn;

	/**
	 * Called when the activity is first created
	 **/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);

		signInEmail = (EditText) findViewById(R.id.etSignInEmail);
		signInPass = (EditText) findViewById(R.id.etSignInPassword);
		statusSignIn = (TextView) findViewById(R.id.statusSignIn);
		resetPass = (Button) findViewById(R.id.btnForgotPass);
		signIn = (Button) findViewById(R.id.btnSignIn);

		resetPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent passReset = new Intent(v.getContext(),
						ResetPassword.class);
				startActivityForResult(passReset, 0);
				finish();
			}
		});

		/**
		 * SignIn button click event A Toast is set to alert when the Email and
		 * Password field is empty
		 **/

		signIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ((!signInEmail.getText().toString().equals(""))
						&& (!signInPass.getText().toString().equals(""))) {
					NetAsync(v);
				} else {
					statusSignIn.setText("One or more fields are empty!");
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		backButtonHandler();
	}

	private void backButtonHandler() {
		Intent loginActivity = new Intent(getApplicationContext(),
				LoginActivity.class);
		startActivityForResult(loginActivity, 0);
		finish();
	}

	/**
	 * Async Task to check whether Internet connection is working
	 **/

	public class NetCheck extends AsyncTask<String, String, Boolean> {
		private ProgressDialog nDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			nDialog = new ProgressDialog(SignIn.this);
			nDialog.setTitle("Checking Network");
			nDialog.setMessage("Loading...");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		/**
		 * Gets current device state and checks for working internet connection
		 * by trying GDM page
		 **/

		@Override
		protected Boolean doInBackground(String... args) {
			ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = connManager.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnected()) {
				try {
					URL url = new URL("http://www.gdm.ro");
					HttpURLConnection urlConn = (HttpURLConnection) url
							.openConnection();
					urlConn.setConnectTimeout(3000);
					urlConn.connect();
					if (urlConn.getResponseCode() == 200) {
						return true;
					}
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean connectionExist) {
			if (connectionExist == true) {
				nDialog.dismiss();
				new ProcessSignIn().execute();
			} else {
				nDialog.dismiss();
				statusSignIn.setText("Error in Network Connection!");
			}
		}
	}

	/**
	 * Async Task to get data through JSON respone
	 **/

	public class ProcessSignIn extends AsyncTask<String, String, JSONObject> {

		/**
		 * Defining Process dialog
		 **/

		private ProgressDialog pDialog;

		String email, password;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			signInEmail = (EditText) findViewById(R.id.etSignInEmail);
			signInPass = (EditText) findViewById(R.id.etSignInPassword);

			email = signInEmail.getText().toString();
			password = signInPass.getText().toString();

			pDialog = new ProgressDialog(SignIn.this);
			pDialog.setTitle("Contacting Server");
			pDialog.setMessage("Logging in...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			UserFunctions userFunction = new UserFunctions();
			JSONObject json = userFunction.loginUser(email, password);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {

			/**
			 * Checks for success message
			 **/

			try {
				if (json.has(KEY_SUCCESS)) {
					Boolean result = Boolean.valueOf(json
							.getString(KEY_SUCCESS));
					if (result) {
						pDialog.setTitle("Getting Data");
						pDialog.setMessage("Loading User Space...");

						/**
						 * If JSON array details are stored in SQLite it
						 * launches the Main Screen
						 **/

						Intent mainScreen = new Intent(getApplicationContext(),
								MainActivity.class);
						mainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						pDialog.dismiss();
						startActivity(mainScreen);

						/**
						 * Close Sign In Screen
						 **/

						finish();
					} else {

						pDialog.dismiss();
						statusSignIn.setText(json.getString("error_msg"));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void NetAsync(View v) {
		new NetCheck().execute();
	}

}