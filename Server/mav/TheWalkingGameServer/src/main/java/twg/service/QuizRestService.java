package twg.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import twg.dao.AnswerDao;
import twg.dao.QuestionDao;
import twg.entities.Question;

@Component
@Path("/quiz")
public class QuizRestService {
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private AnswerDao answerDao;

	
	
	@GET
	@Path("/id")
	@Produces({ MediaType.APPLICATION_JSON})
	public Map<String, Object> findQuizById(@QueryParam("id") int id) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question",questionDao.getQuestionById(id));
		map.put("answers", answerDao.getAnswerByQuestionId(id));
		return map;
		
	}
	
	@GET
	@Path("/countQuiz")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Map<String,Integer> getNumberOfQuiz() throws JsonGenerationException, JsonMappingException, IOException
	{
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("numberQuiz", questionDao.getNumberQuiz());
		return map;
	}
	
	@GET
	@Path("/all")
	@Produces({ MediaType.APPLICATION_JSON})
	public Map<String, List<Map<String, Object>>> findAllQuiz() throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(Question q : questionDao.getAllQuestions()){
			list.add(findQuizById(q.getId()));
		}	
		map.put("quiz", list);
		return map;
	}
	
	@DELETE
	@Path("/delete")
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Transactional
	public void deleteQuizById(@QueryParam("id") int id) {
		answerDao.deleteAnswerByQuestionId(id);
		questionDao.deleteQuestionId(id);
	}
	

}
