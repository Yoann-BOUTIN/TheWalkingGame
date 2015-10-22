package database;

public class Answer {
	
	private Integer answerId;
	private String answerText;
	private boolean correctAnswer;
	private Integer questionId;

	public Answer(){}
	public Answer(String answerText, boolean correctAnswer, Integer questionId) {
		this.answerText = answerText;
		this.correctAnswer = correctAnswer;
		this.questionId = questionId;
	}
	
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer idAnswer) {
		this.answerId = idAnswer;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public boolean getCorrectAnswer(){
		return this.correctAnswer;
	}
	public void setCorrectAnswer(boolean correctAnswer){
		this.correctAnswer = correctAnswer;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
}
