package database;

public class Question {
	
	private Integer questionId;
	private String questionText;
	private boolean multipleAnswers;

	public Question(){}
	public Question(String questionText, Integer answerId,boolean multipleAnswers) {
		this.questionText = questionText;
		this.multipleAnswers = multipleAnswers;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer idQuestion) {
		this.questionId = idQuestion;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public boolean isMultipleAnswers() {
		return multipleAnswers;
	}
	public void setMultipleAnswers(boolean multipleAnswers) {
		this.multipleAnswers = multipleAnswers;
	}
}
