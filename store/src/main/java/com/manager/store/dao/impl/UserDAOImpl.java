package com.manager.store.dao.impl;

import com.manager.store.dao.UserDAO;
import com.manager.store.entities.Staff;
import com.manager.store.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation for UserDAO.
 *
 * @author Piotr
 */
@Repository("userDAO")
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO {

    private static final String MAIL_PARAM = "mail";

    private static final String GET_USER_BY_MAIL = new StringBuilder("SELECT u")
            .append("   FROM User u")
            .append("   WHERE u.mail = :").append(MAIL_PARAM)
            .toString();

    public UserDAOImpl() {
        this(User.class);
    }

    public UserDAOImpl(Class<User> typeParameterClass) {
        super(typeParameterClass);
    }

    @Override
    public Long save(final User entity) {
        Staff details = entity.getDetails();
        if(details != null){
            getCurrentSession().save(details);
        }
        return super.save(entity);
    }

    @Override
    public User merge(final User entity) {
        Staff details = entity.getDetails();
        if(details != null){
            getCurrentSession().merge(details);
        }
        return super.merge(entity);
    }

    @Override
    public void saveOrUpdate(final User entity) {
        Staff details = entity.getDetails();
        if(details != null){
            getCurrentSession().saveOrUpdate(details);
        }
        super.saveOrUpdate(entity);
    }

    @Override
    public void update(final User entity) {
        Staff details = entity.getDetails();
        if(details != null){
            getCurrentSession().update(details);
        }
        super.update(entity);
    }

    @Override
    public User delete(final Long id) {
        User user = get(id);
        if (user == null) {
            return null;
        }
        user.setActive(false);

        return merge(user);
    }

    @Override
    public User getByMail(String mail, boolean activeUsersOnly) {
        String query = activeUsersOnly ? GET_USER_BY_MAIL + " AND u.active = true" : GET_USER_BY_MAIL;
        return (User) getCurrentSession()
                .createQuery(query)
                .setParameter(MAIL_PARAM, mail)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> search(final String term, final int limit, final String additionalQueryConditions) {
        if(StringUtils.isBlank(term) ){
            return getAll();
        }

        String formula = "FROM User u " +
                "WHERE u.id IN (SELECT u1.id " +
                "                   FROM User u1 " +
                "                   WHERE u1.mail LIKE :term) " +
                "OR u.id IN (SELECT u2.id " +
                "                   FROM User u2 JOIN u2.details s " +
                "                   WHERE s.firstName LIKE :term OR s.lastName LIKE :term)";
        Query sqlQuery = getCurrentSession().createQuery(formula)
                .setParameter("term", "%" + term + "%")
                .setMaxResults(limit);
        return sqlQuery.list();
    }

    public List<User> getWorkers(){
        String formula = "FROM User u WHERE u.details.id > 0";
        Query query = getCurrentSession().createQuery(formula);
        return query.list();
    }
}
