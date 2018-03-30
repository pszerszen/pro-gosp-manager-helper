package com.manager.store.dao;

import org.hibernate.Session;

import java.util.List;

/**
 * The interface for Generic DAO class. Contains the definitions of methods needed in every DAO-type class (like saving, updating, deleting).
 *
 * @author Piotr
 * @param <E>
 *            the type of the entity a DAO is to work with
 */
public interface GenericDAO<E> {

    /**
     * Removes the DB record referred by entity object with given id. Calls {@link Session#delete(Object)}
     *
     * @param id
     *            the id of the record to delete
     * @return removed entity or null if not found
     */
    E delete(Long id);

    /**
     * Gives the entity of type E with given id from DB. Calls {@link Session#get(Class, java.io.Serializable)}
     *
     * @param id
     *            the id of record to get
     * @return searched entity object or null if not found
     */
    E get(Long id);

    /**
     * Gives a full list of the entities in DB.
     *
     * @return every E in DB.
     */
    List<E> getAll();

    List<E> search(String query, int limit, final String additionalQueryConditions);

    /**
     * Performs an update of the entity or saves as new DB record. Calls {@link Session#merge(Object)}
     *
     * @param entity
     *            modified to update in DB
     * @return updated entity object
     */
    E merge(E entity);

    /**
     * Saves the new record of type E in DB. Calls {@link Session#save(Object)}
     *
     * @param entity
     *            to store as a record in DB
     * @return the id of newly stored entity
     */
    Long save(E entity);

    /**
     * Calls {@link Session#saveOrUpdate(Object)} which gives access to {@link Session#update(Object)} in case it would be needed with future system
     * improving.
     *
     * @param entity
     *            to save or update
     */
    void saveOrUpdate(E entity);

    void update(E entity);
}
