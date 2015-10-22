package twg.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="answer")
public class Answer implements Serializable{
	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the question */
	@Id
	@GeneratedValue
	@Column(name="answer_id")
	private int id;
	
	/** text of the question */
	@Column(name="answer_text")
	private String answerText;
		
	/** int represent if is correct */
	@Column(name="correct_answer")
	private int correctAnswer;
	
	@JoinColumn(name="question_id")
	/** question id of the answer */
	@Column(name="question_id")
	private int questionId;
	
	
	public Answer(){};
	
	public Answer(String answerText, int correctAnswer, int question_id){
		this.answerText = answerText;
		this.correctAnswer = correctAnswer;
		this.questionId = question_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	

}
