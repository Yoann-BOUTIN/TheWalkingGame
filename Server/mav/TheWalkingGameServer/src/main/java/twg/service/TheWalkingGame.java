package twg.service;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import twg.util.CORSResponseFilter;
import twg.util.LoggingResponseFilter;

/**
 * Registers the components to be used by the JAX-RS application  
 * 
 * @author ama
 *
 */
public class TheWalkingGame extends ResourceConfig {

    /**
	* Register JAX-RS application components.
	*/	
	public TheWalkingGame(){
		register(RequestContextFilter.class);
		register(UserRestService.class);
		register(QuizRestService.class);
		register(InvitationRestService.class);
		register(TeamRestService.class);
		register(ReanimerRestService.class);
		register(HeadUpsideDownRestService.class);
		register(FindKeysRestService.class);
		register(ObjectiveRestService.class);
		register(JacksonFeature.class);	
		register(LoggingResponseFilter.class);
		register(CORSResponseFilter.class);
		register(SimonRestService.class);
		register(GameRestService.class);
		register(GameFinishedRestService.class);
	}
}
