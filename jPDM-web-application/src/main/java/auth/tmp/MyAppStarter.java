package auth.tmp;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import auth.SecurityConfiguration;
import controllers.MvcConfig;

public class MyAppStarter extends AbstractAnnotationConfigDispatcherServletInitializer {

	// Load database and spring security configurations
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfiguration.class };
	}

	// Load spring web configuration
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { MvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
