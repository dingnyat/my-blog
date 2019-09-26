package ding.nyat.resolver;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class UrlLocaleResolver implements LocaleResolver {

    private final String LOCALE_ATTR_NAME = "LOCAL_ATTR_NAME";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String URI = request.getRequestURI();
        Locale locale = null;
        if (URI.startsWith(request.getServletContext().getContextPath() + "/en/"))
            locale = Locale.US;
        else if (URI.startsWith(request.getServletContext().getContextPath() + "/vi/"))
            locale = new Locale("vi", "VN");

        if (locale != null) request.getSession().setAttribute(LOCALE_ATTR_NAME, locale);
        else {
            locale = (Locale) request.getSession().getAttribute(LOCALE_ATTR_NAME);
            if (locale == null) locale = Locale.US;
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }
}
