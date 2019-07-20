package dou.ding.nyat.blog;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import java.util.EnumSet;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        // these configs of cookie/session also can be set in application.properties (spring boot)
        // these are spring mvc methods
        servletContext.setSessionTimeout(60 * 60 * 24 * 7);
        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE)); // default value
        servletContext.getSessionCookieConfig().setName("DIT_ME_MAY");
        servletContext.getSessionCookieConfig().setHttpOnly(true); //default value // browser scripts can't access the cookie
        servletContext.getSessionCookieConfig().setSecure(false); // default value // cookie will be sent only over https
    }
}
