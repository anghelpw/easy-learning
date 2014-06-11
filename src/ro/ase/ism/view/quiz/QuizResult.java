package ro.ase.ism.view.quiz;

import ro.ase.ism.R;
import android.os.Bundle;
import android.app.Activity;
import android.widget.RatingBar;
import android.widget.TextView;

public class QuizResult extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_result);

		// Get rating bar object
		RatingBar rbQuizResult = (RatingBar) findViewById(R.id.rbQuizResult);
		rbQuizResult.setNumStars(8);
		rbQuizResult.setStepSize(0.8f);

		// Get text view
		TextView tvQuizResult = (TextView) findViewById(R.id.tvQuizResult);

		// Get score
		Bundle b = getIntent().getExtras();
		int score = b.getInt("score");

		// Display score
		rbQuizResult.setRating(score);
		switch (score) {
		case 1:
		case 2:
			tvQuizResult
					.setText("Claritas est etiam processus dynamicus, qui sequitur mutationem consuetudium lectorum.");
			break;
		case 3:
		case 4:
			tvQuizResult
					.setText("Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat.");
			break;
		case 5:
			tvQuizResult
					.setText("Eodem modo typi, qui nunc nobis videntur parum clari, fiant sollemnes in futurum.");
			break;
		}
	}
}
