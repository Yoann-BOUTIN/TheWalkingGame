package twg.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="question")
public class Question implements Serializable {
	
	private static final long serialVersionUID = -8039686696076337053L;
	
	/** id of the question */
	@Id
	@GeneratedValue
	@Column(name="question_id")
	private int id;
	
	/** text of the question */
	@Column(name="question_text")
	private String questionText;
		
	/** int represent if they are multiple choice */
	@Column(name="question_multiple_choice")
	private int questionMultipleChoice;
	
	
	public Question(){};
	
	public Question(String questionText, int questionMultipleChoice){
		this.questionText = questionText;
		this.questionMultipleChoice = questionMultipleChoice;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public int getQuestionMultipleChoice() {
		return questionMultipleChoice;
	}

	public void setQuestionMultipleChoice(int questionMultipleChoice) {
		this.questionMultipleChoice = questionMultipleChoice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
