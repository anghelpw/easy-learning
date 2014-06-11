package ro.ase.ism.view.quiz.db;

public class Question {
	private int ID;
	private String QUESTNUMBER;
	private String QUESTION;
	private String OPTIONA;
	private String OPTIONB;
	private String OPTIONC;
	private String OPTIOND;
	private String ANSWER;

	public Question() {
		ID = 0;
		QUESTNUMBER = "";
		QUESTION = "";
		OPTIONA = "";
		OPTIONB = "";
		OPTIONC = "";
		OPTIOND = "";
		ANSWER = "";
	}

	public Question(String questNumber, String question, String optionA,
			String optionB, String optionC, String optionD, String answer) {

		QUESTNUMBER = questNumber;
		QUESTION = question;
		OPTIONA = optionA;
		OPTIONB = optionB;
		OPTIONC = optionC;
		OPTIOND = optionD;
		ANSWER = answer;
	}

	public int getID() {
		return ID;
	}

	public String getQuestNumber() {
		return QUESTNUMBER;
	}

	public String getQuestion() {
		return QUESTION;
	}

	public String getOptionA() {
		return OPTIONA;
	}

	public String getOptionB() {
		return OPTIONB;
	}

	public String getOptionC() {
		return OPTIONC;
	}

	public String getOptionD() {
		return OPTIOND;
	}

	public String getAnswer() {
		return ANSWER;
	}

	public void setID(int id) {
		ID = id;
	}

	public void setQuestNumber(String questNumber) {
		QUESTNUMBER = questNumber;
	}

	public void setQuestion(String question) {
		QUESTION = question;
	}

	public void setOptionA(String optionA) {
		OPTIONA = optionA;
	}

	public void setOptionB(String optionB) {
		OPTIONB = optionB;
	}

	public void setOptionC(String optionC) {
		OPTIONC = optionC;
	}

	public void setOptionD(String optionD) {
		OPTIOND = optionD;
	}

	public void setAnswer(String answer) {
		ANSWER = answer;
	}

}