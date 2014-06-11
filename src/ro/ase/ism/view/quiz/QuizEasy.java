package ro.ase.ism.view.quiz;

import java.util.List;

import ro.ase.ism.R;
import ro.ase.ism.view.quiz.db.DbHandlerQuiz;
import ro.ase.ism.view.quiz.db.Question;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizEasy extends Activity {
	List<Question> questionList;
	int score = 0;
	int questionId = 0;
	Question currentQuestion;
	TextView tvQuestionNumber, tvQuestion;
	RadioButton rbQuestionA, rbQuestionB, rbQuestionC, rbQuestionD;
	Button btnNextQuestion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_easy);

		DbHandlerQuiz db = new DbHandlerQuiz(this);
		questionList = db.getAllQuestions();
		currentQuestion = questionList.get(questionId);

		tvQuestionNumber = (TextView) findViewById(R.id.tvQuestionNumber);
		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		rbQuestionA = (RadioButton) findViewById(R.id.rbAnswerA);
		rbQuestionB = (RadioButton) findViewById(R.id.rbAnswerB);
		rbQuestionC = (RadioButton) findViewById(R.id.rbAnswerC);
		rbQuestionD = (RadioButton) findViewById(R.id.rbAnswerD);

		btnNextQuestion = (Button) findViewById(R.id.btnNextQuestion);
		setQuestionView();

		btnNextQuestion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup rgQuestion = (RadioGroup) findViewById(R.id.rgQuestion);
				RadioButton answer = (RadioButton) findViewById(rgQuestion
						.getCheckedRadioButtonId());
				if (currentQuestion.getAnswer().equals(answer.getText())) {
					score++;
				}
				if (questionId < 8) {
					currentQuestion = questionList.get(questionId);
					setQuestionView();
				} else {
					Intent quizResult = new Intent(QuizEasy.this,
							QuizResult.class);
					Bundle b = new Bundle();
					b.putInt("score", score); // Your score
					quizResult.putExtras(b); // Put your score to next Intent
					startActivity(quizResult);
					finish();
				}
			}
		});
	}

	private void setQuestionView() {
		tvQuestionNumber.setText(currentQuestion.getQuestNumber());
		tvQuestion.setText(currentQuestion.getQuestion());
		rbQuestionA.setText(currentQuestion.getOptionA());
		rbQuestionB.setText(currentQuestion.getOptionB());
		rbQuestionC.setText(currentQuestion.getOptionC());
		rbQuestionD.setText(currentQuestion.getOptionD());
		questionId++;
	}
}
