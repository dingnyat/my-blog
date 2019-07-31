package dou.ding.nyat.blog.config;

import com.ckfinder.connector.ConnectorServlet;
import dou.ding.nyat.blog.util.PathConstants;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CKFinderServletConfig {

    private final String STATIC_PATH = "/vendor"; // Chú ý: vendor là đường dẫn static đến folder ckfinder trong static
    // ckfinder.js cần jquery nhá
    @Bean
    public ServletRegistrationBean connectCKFinder() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
                new ConnectorServlet(), STATIC_PATH + "/ckfinder/core/connector/java/connector.java");
        registrationBean.addInitParameter("XMLConfig", "/ckfinder_config.xml");
        registrationBean.addInitParameter("debug", "false");
        registrationBean.addInitParameter("configuration", CKFinderConfig.class.getName());
        registrationBean.addInitParameter("baseDir", PathConstants.UPLOAD_BASE_DIR);
        registrationBean.addInitParameter("baseURL", PathConstants.BASE_URL);
        return registrationBean;
    }

}
