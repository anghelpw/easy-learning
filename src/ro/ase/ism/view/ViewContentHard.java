package ro.ase.ism.view;

import ro.ase.ism.R;
import ro.ase.ism.view.quiz.QuizHard;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewContentHard extends Activity {

	Button takeQuiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_content_hard);

		takeQuiz = (Button) findViewById(R.id.btnTakeQuizHard);
		takeQuiz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent takeQuizHard = new Intent(getApplicationContext(),
						QuizHard.class);
				startActivityForResult(takeQuizHard, 0);
				finish();
			}
		});
	}

}