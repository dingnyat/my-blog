package ding.nyat.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {

    private PersistentTokenRepository tokenRepository;

    public CustomPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
        return super.processAutoLoginCookie(cookieTokens, request, response);
    }

    @Override
    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        super.onLoginSuccess(request, response, successfulAuthentication);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        this.cancelCookie(request, response);
        if (authentication != null) {
            String rememberMeCookie = this.extractRememberMeCookie(request);
            if (rememberMeCookie != null && !rememberMeCookie.isEmpty()) {
                String[] cookieTokens = this.decodeCookie(rememberMeCookie);
                if (cookieTokens.length == 2) {
                    String series = cookieTokens[0];
                    this.tokenRepository.removeUserTokens(series);
                }
            }
        }
        super.logout(request, response, authentication);
    }

    @Override
    protected String generateSeriesData() {
        return super.generateSeriesData();
    }

    @Override
    protected String generateTokenData() {
        return super.generateTokenData();
    }

    @Override
    public void setSeriesLength(int seriesLength) {
        super.setSeriesLength(seriesLength);
    }

    @Override
    public void setTokenLength(int tokenLength) {
        super.setTokenLength(tokenLength);
    }

    @Override
    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        super.setTokenValiditySeconds(tokenValiditySeconds);
    }
}
