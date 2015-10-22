package twg.dao.impl;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import twg.dao.QuestionDao;
import twg.entities.Question;

public class QuestionDaoJPA2Impl implements QuestionDao {
	
	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;
	
	public Question getQuestionById(int id) {
		try {
			String qlString = "SELECT q FROM Question q WHERE q.id = ?1";
			TypedQuery<Question> query = entityManager.createQuery(qlString, Question.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Question> getAllQuestions() {
		try {
			String qlString = "SELECT q FROM Question q";
			TypedQuery<Question> query = entityManager.createQuery(qlString, Question.class);		

			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public int getNumberQuiz(){
		return getAllQuestions().size();
	}

	@OneToMany(cascade = CascadeType.ALL)
	public void deleteQuestionId(int id) {
		Query query = entityManager.createNativeQuery("DELETE FROM question WHERE question_id = ?1");
		query.setParameter(1, id);
		query.executeUpdate();	
		
	}


}
