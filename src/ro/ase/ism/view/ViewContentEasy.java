package ro.ase.ism.view;

import ro.ase.ism.R;
import ro.ase.ism.view.quiz.QuizEasy;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewContentEasy extends Activity {

	Button takeQuiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_content_easy);

		takeQuiz = (Button) findViewById(R.id.btnTakeQuizEasy);
		takeQuiz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent takeQuizEasy = new Intent(getApplicationContext(),
						QuizEasy.class);
				startActivityForResult(takeQuizEasy, 0);
				finish();
			}
		});
	}

}
