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

public class ResetPassword extends Activity {

	/**
	 * JSON Response node names
	 **/

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";

	/**
	 * Defining layout items
	 **/

	EditText userEmail;
	TextView statusResetPass;
	Button linkSignIn, resetPass;

	/**
	 * Called when the activity is first created
	 **/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_password);

		userEmail = (EditText) findViewById(R.id.etUserEmail);
		statusResetPass = (TextView) findViewById(R.id.statusResetPass);
		resetPass = (Button) findViewById(R.id.btnResetPassward);
		linkSignIn = (Button) findViewById(R.id.btnLinkSignIn);

		resetPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NetAsync(v);
			}
		});

		linkSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent backToSignIn = new Intent(v.getContext(), SignIn.class);
				startActivityForResult(backToSignIn, 0);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		backButtonHandler();
	}

	private void backButtonHandler() {
		Intent signInActivity = new Intent(getApplicationContext(),
				SignIn.class);
		startActivityForResult(signInActivity, 0);
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
			nDialog = new ProgressDialog(ResetPassword.this);
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
		protected void onPostExecute(Boolean th) {
			if (th == true) {
				nDialog.dismiss();
				new ProcessReset().execute();
			} else {
				nDialog.dismiss();
				statusResetPass.setText("Error in Network Connection!");
			}
		}
	}

	/**
	 * Async Task to get and send data to MySql database through JSON respone
	 **/

	private class ProcessReset extends AsyncTask<String, String, JSONObject> {

		/**
		 * Defining Process dialog
		 **/

		private ProgressDialog pDialog;

		String forgotPassword;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			forgotPassword = userEmail.getText().toString();

			pDialog = new ProgressDialog(ResetPassword.this);
			pDialog.setTitle("Contacting Server");
			pDialog.setMessage("Getting Data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			UserFunctions userFunction = new UserFunctions();
			JSONObject json = userFunction.forPass(forgotPassword);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {

			/**
			 * Checks if the Password Change Process is sucesss
			 **/

			try {
				if (json.getString(KEY_SUCCESS) != null) {
					statusResetPass.setText("");
					String result = json.getString(KEY_SUCCESS);
					String resultError = json.getString(KEY_ERROR);

					if (Integer.parseInt(result) == 1) {
						pDialog.dismiss();
						statusResetPass
								.setText("A recovery email is sent to you, see it for more details.");
					} else if (Integer.parseInt(resultError) == 2) {
						pDialog.dismiss();
						statusResetPass
								.setText("Your email does not exist in our database!");
					} else {
						pDialog.dismiss();
						statusResetPass
								.setText("Error occured in changing Password!");
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
