package ro.ase.ism.login;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
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

public class CreateAccount extends Activity {

	/**
	 * JSON Response node names
	 **/

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";

	/**
	 * Defining layout items
	 **/

	EditText registerEmail, registerUsername, registerPassword;
	TextView statusCreateAcc;
	Button createAcc;

	/**
	 * Called when the activity is first created
	 **/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);

		registerEmail = (EditText) findViewById(R.id.etRegisterEmail);
		registerUsername = (EditText) findViewById(R.id.etRegisterUsername);
		registerPassword = (EditText) findViewById(R.id.etRegisterPassword);
		statusCreateAcc = (TextView) findViewById(R.id.tvStatusCreateAcc);
		createAcc = (Button) findViewById(R.id.btnCreateAccount);

		/**
		 * Register Button click event
		 **/

		createAcc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if ((!registerEmail.getText().toString().equals(""))
						&& (!registerUsername.getText().toString().equals(""))
						&& (!registerPassword.getText().toString().equals(""))) {
					 NetAsync(v);
				} else {
					statusCreateAcc.setText("One or more fields are empty!");
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

	private class NetCheck extends AsyncTask<String, String, Boolean> {
		private ProgressDialog nDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			nDialog = new ProgressDialog(CreateAccount.this);
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
				new ProcessCreateAcc().execute();
			} else {
				nDialog.dismiss();
				statusCreateAcc.setText("Error in Network Connection!");
			}
		}
	}

	/**
	 * Async Task to get data through JSON respone
	 **/

	private class ProcessCreateAcc extends
			AsyncTask<String, String, JSONObject> {

		/**
		 * Defining Process dialog
		 **/

		private ProgressDialog pDialog;

		String email, username, password;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			registerEmail = (EditText) findViewById(R.id.etRegisterEmail);
			registerUsername = (EditText) findViewById(R.id.etRegisterUsername);
			registerPassword = (EditText) findViewById(R.id.etRegisterPassword);

			email = registerEmail.getText().toString();
			username = registerUsername.getText().toString();
			password = registerPassword.getText().toString();

			pDialog = new ProgressDialog(CreateAccount.this);
			pDialog.setTitle("Contacting Server");
			pDialog.setMessage("Create Account...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			UserFunctions userFunction = new UserFunctions();
			JSONObject json = userFunction.registerUser(email, username,
					password);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {

			/**
			 * Checks for success message
			 **/

			try {
				if (json.has(KEY_SUCCESS) && json.has(KEY_ERROR)) {
					Boolean result = Boolean.valueOf(json
							.getString(KEY_SUCCESS));

					if (result) {
						pDialog.setTitle("Getting Data");
						pDialog.setMessage("Loading Info...");
						statusCreateAcc.setText("Successfully Registered!");

						/**
						 * Account Details screen
						 **/

						Intent accountDetails = new Intent(
								getApplicationContext(), AccountDetails.class);

						/**
						 * Close all views before launching AccountDetails
						 * screen
						 **/

						accountDetails.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						pDialog.dismiss();
						startActivity(accountDetails);

						/**
						 * Close Create Account Screen
						 **/

						finish();
					} else {
						pDialog.dismiss();
						statusCreateAcc.setText(json.getString("error_msg"));
					}
				} else {
					pDialog.dismiss();
					statusCreateAcc.setText("Error occured in registration!");
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