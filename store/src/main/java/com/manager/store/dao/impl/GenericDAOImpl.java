package com.manager.store.dao.impl;

import com.manager.store.dao.GenericDAO;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * The implementation of {@link GenericDAO}. Implements the basic entity session methods in one place.
 *
 * @author Piotr
 * @param <E>
 *            type of the entity to work with
 */
public abstract class GenericDAOImpl<E> implements GenericDAO<E> {

    @Resource(name = "sessionFactory")
    protected SessionFactory sessionFactory;

    private final Class<E> typeParameterClass;

    public GenericDAOImpl(Class<E> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public E delete(Long id) {
        Session session = getCurrentSession();
        E entity = get(id);
        if (entity != null) {
            session.delete(entity);
            return entity;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(Long id) {
        return (E) getCurrentSession().get(typeParameterClass, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> getAll() {
        String formula = "FROM " + typeParameterClass.getSimpleName();
        Query query = getCurrentSession().createQuery(formula);

        return query.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E merge(E entity) {
        return (E) getCurrentSession().merge(entity);
    }

    @Override
    public Long save(E entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(E entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void update(E entity) {
        getCurrentSession().update(entity);
    }

    /**
     * @return
     * @throws HibernateException
     */
    protected Session getCurrentSession() throws HibernateException {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<E> search(final String query, final int limit, final String additionalQueryConditions) {
        return getAll();
    }
}
