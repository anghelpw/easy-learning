package ro.ase.ism.view.quiz.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandlerQuiz extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;

	// Database name
	private static final String DATABASE_NAME = "EasyLearningQuiz";

	// Tasks table name
	private static final String TABLE_QUEST = "quest";

	// Tasks table columns names
	private static final String KEY_ID = "id";
	private static final String KEY_QUESTNUMBER = "questNumber";
	private static final String KEY_QUESTION = "question";
	private static final String KEY_ANSWER = "answer"; // Correct option
	private static final String KEY_OPTIONA = "optionA"; // Option A
	private static final String KEY_OPTIONB = "optionB"; // Option B
	private static final String KEY_OPTIONC = "optionC"; // Option C
	private static final String KEY_OPTIOND = "optionD"; // Option d
	private SQLiteDatabase dbase;

	public DbHandlerQuiz(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		dbase = db;
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_QUESTNUMBER + " TEXT, " + KEY_QUESTION + " TEXT, "
				+ KEY_ANSWER + " TEXT, " + KEY_OPTIONA + " TEXT, "
				+ KEY_OPTIONB + " TEXT, " + KEY_OPTIONC + " TEXT, "
				+ KEY_OPTIOND + " TEXT)";
		db.execSQL(sql);
		addQuestions();
		// db.close();
	}

	private void addQuestions() {
		Question q1 = new Question("Question number: #1",
				"Which company bought Android?", "Apple", "Google", "Samsung",
				"Nokia", "Google");
		this.addQuestion(q1);
		Question q2 = new Question("Question number: #2", "What is Android?",
				"Database", "Mobile Operating System", "Programming Language",
				"Desktop Operating System", "Mobile Operating System");
		this.addQuestion(q2);
		Question q3 = new Question("Question number: #3",
				"Which features are supported in Android?", "Multitasking",
				"Bluetooth", "Video calling", "All of these", "All of these");
		this.addQuestion(q3);
		Question q4 = new Question("Question number: #4",
				"Which format is not supported in Android?", "MIDI", "MPEG",
				"AVI", "MP4", "MIDI");
		this.addQuestion(q4);
		Question q5 = new Question("Question number: #5",
				"Android is based on which kernel?", "Hybrid Kernel",
				"Linux kernel", "MAC kernel", "Windows kernel", "Linux kernel");
		this.addQuestion(q5);
		Question q6 = new Question("Question number: #6",
				"Android is based on which language?", "C++", "Java", "Ruby",
				"HTML", "Java");
		this.addQuestion(q6);
		Question q7 = new Question("Question number: #7",
				"Which is the latest mobile version of Android?",
				"4.1 (JellyBean)", "3.0 (Honeycomb)", "2.3 (Gingerbread)",
				"4.4", "4.4");
		this.addQuestion(q7);
		Question q8 = new Question("Question number: #8",
				"Web browser available in Android is based on?", "Chrome",
				"Firefox", "Open-source Webkit", "Opera", "Chrome");
		this.addQuestion(q8);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
		// Create tables again
		onCreate(db);
	}

	// Adding new question
	public void addQuestion(Question quest) {
		// SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_QUESTNUMBER, quest.getQuestNumber());
		values.put(KEY_QUESTION, quest.getQuestion());
		values.put(KEY_ANSWER, quest.getAnswer());
		values.put(KEY_OPTIONA, quest.getOptionA());
		values.put(KEY_OPTIONB, quest.getOptionB());
		values.put(KEY_OPTIONC, quest.getOptionC());
		values.put(KEY_OPTIOND, quest.getOptionD());
		// Inserting row
		dbase.insert(TABLE_QUEST, null, values);
	}

	public List<Question> getAllQuestions() {
		List<Question> quesList = new ArrayList<Question>();
		// Select all query
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		dbase = this.getReadableDatabase();
		Cursor cursor = dbase.rawQuery(selectQuery, null);
		// Looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Question quest = new Question();
				quest.setID(cursor.getInt(0));
				quest.setQuestNumber(cursor.getString(1));
				quest.setQuestion(cursor.getString(2));
				quest.setAnswer(cursor.getString(3));
				quest.setOptionA(cursor.getString(4));
				quest.setOptionB(cursor.getString(5));
				quest.setOptionC(cursor.getString(6));
				quest.setOptionD(cursor.getString(7));
				quesList.add(quest);
			} while (cursor.moveToNext());
		}
		// Return quest list
		return quesList;
	}

	public int rowCount() {
		int row = 0;
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		row = cursor.getCount();
		return row;
	}
}
