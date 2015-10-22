package twg.dao;

import java.util.List;

import twg.entities.Question;

public interface QuestionDao {

	public Question getQuestionById(int id);
	
	public List<Question> getAllQuestions();
	
	public void deleteQuestionId(int id);

	public int getNumberQuiz();
	
}
