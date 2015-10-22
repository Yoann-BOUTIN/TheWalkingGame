package twg.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import twg.dao.AnswerDao;
import twg.entities.Answer;

public class AnswerDaoJPA2Impl implements AnswerDao {
	
	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	public List<Answer> getAnswerByQuestionId(int id){
		String qlString = "SELECT a FROM Answer a WHERE a.questionId = ?1";
		TypedQuery<Answer> query = entityManager.createQuery(qlString, Answer.class);

		query.setParameter(1, id);
		return query.getResultList();
	}

	public void deleteAnswerByQuestionId(int id) {
		Query query = entityManager.createNativeQuery("DELETE FROM answer WHERE question_id = ?1");
		query.setParameter(1, id);
		query.executeUpdate();	
	}

}
