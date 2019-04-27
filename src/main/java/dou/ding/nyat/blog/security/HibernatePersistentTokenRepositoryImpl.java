package dou.ding.nyat.blog.security;

import dou.ding.nyat.blog.entity.PersistentLogin;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Repository(value = "HibernateRepository")
@Transactional
public class HibernatePersistentTokenRepositoryImpl implements PersistentTokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createNewToken(PersistentRememberMeToken persistentRememberMeToken) {
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(persistentRememberMeToken.getUsername());
        persistentLogin.setSeries(persistentRememberMeToken.getSeries());
        persistentLogin.setLast_used(persistentRememberMeToken.getDate());
        persistentLogin.setToken(persistentRememberMeToken.getTokenValue());
        entityManager.persist(persistentLogin);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        PersistentLogin persistentLogin = entityManager.find(PersistentLogin.class, series);
        if (persistentLogin == null) return null;
        return new PersistentRememberMeToken(persistentLogin.getUsername(),
                persistentLogin.getSeries(), persistentLogin.getToken(), persistentLogin.getLast_used());
    }

    @Override
    public void updateToken(String series, String token, Date lastDate) {
        PersistentLogin persistentLogin = entityManager.find(PersistentLogin.class, series);
        persistentLogin.setToken(token);
        persistentLogin.setLast_used(lastDate);
        entityManager.merge(persistentLogin);
    }

    @Override
    public void removeUserTokens(String series) {
        PersistentLogin persistentLogin = entityManager.find(PersistentLogin.class, series);
        if (persistentLogin == null) return;
        entityManager.remove(persistentLogin);
    }
}
