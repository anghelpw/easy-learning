package ro.ase.ism.login;

import ro.ase.ism.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {

	Button linkCreateAcc, linkSignIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		linkCreateAcc = (Button) findViewById(R.id.btnLinkCreateAccount);
		linkSignIn = (Button) findViewById(R.id.btnLinkSignIn);
		linkCreateAcc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent createAcc = new Intent(v.getContext(),
						CreateAccount.class);
				startActivityForResult(createAcc, 0);
				finish();
			}
		});

		linkSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent signIn = new Intent(v.getContext(), SignIn.class);
				startActivityForResult(signIn, 0);
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
				LoginActivity.this);
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