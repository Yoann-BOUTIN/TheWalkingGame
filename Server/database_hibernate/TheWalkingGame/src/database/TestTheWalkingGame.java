
// TO CREATE AND INSERT A USER IN DATABASE

package database;
//
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//
public class TestTheWalkingGame {
//
	public static void main(String args[]) throws Exception
	{
		SessionFactory sessionFac = HibernateUtil.getSessionFactory();
		Session session = sessionFac.openSession();

		Transaction transaction = null; 
		try { 
			transaction = session.beginTransaction(); 
			Answer ans = new Answer("test_question id 2", true, 2);
			session.save(ans);
			session.flush() ;
			transaction.commit();
		} catch (Exception e) {
			if (transaction!= null) {
				transaction.rollback();
			}
			throw e;
		} finally { 
			session.close(); 
		}
		sessionFac.close();
	}
}