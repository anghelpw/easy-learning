package ro.ase.ism.view;

import ro.ase.ism.R;
import ro.ase.ism.view.quiz.QuizMedium;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewContentMedium extends Activity {

	Button takeQuiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_content_medium);

		takeQuiz = (Button) findViewById(R.id.btnTakeQuizMedium);
		takeQuiz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent takeQuizMedium = new Intent(getApplicationContext(),
						QuizMedium.class);
				startActivityForResult(takeQuizMedium, 0);
				finish();
			}
		});
	}

}