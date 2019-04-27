package dou.ding.nyat.blog.config;

import com.ckfinder.connector.ConnectorServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CKFinderServletConfig {

    @Value("${ckeditor.storage.image.path}")
    private String baseDir;
    @Value("${ckeditor.access.image.url}")
    private String baseURL;

    @Bean
    public ServletRegistrationBean connectCKFinder() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new ConnectorServlet(), "/ckfinder/core/connector/java/connector.java");
        registrationBean.addInitParameter("XMLConfig", "/ckfinder_config.xml");
        registrationBean.addInitParameter("debug", "false");
        registrationBean.addInitParameter("configuration", CKFinderConfig.class.getName());
        registrationBean.addInitParameter("baseDir", baseDir);
        registrationBean.addInitParameter("baseURL", baseURL);
        return registrationBean;
    }

}
