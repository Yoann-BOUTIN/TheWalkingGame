package twg.dao;

import java.util.List;

import twg.entities.Answer;

public interface AnswerDao {
	public List<Answer> getAnswerByQuestionId(int id);
	
	public void deleteAnswerByQuestionId(int id);
	

}
