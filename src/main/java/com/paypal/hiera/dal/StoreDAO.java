package com.paypal.hiera.dal;

import com.paypal.common.exceptions.DalException;
import com.google.common.base.Predicate;
import com.paypal.hiera.models.LocationConfig;
import com.paypal.hiera.models.StoreConfig;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: pderoxas
 * Date: 8/30/13
 * Time: 10:05 AM
 * DAO Implementation for Resource
 */
@Repository
@Qualifier("StoreDAO")
@Transactional

public class StoreDAO implements ResourceDAO<StoreConfig, String> {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTransactionManager transactionManager;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Iterable<StoreConfig> getAll() throws DalException {
        return this.getList(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<StoreConfig> getByIdList(List<String> idList) throws DalException {
        return this.getList(Restrictions.in("id", idList));
    }

    @Override
    public Iterable<StoreConfig> getByPredicate(Predicate<StoreConfig> predicate) throws DalException {
        throw new NotImplementedException("This method is not implemented.");
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<StoreConfig> getList(Criterion criterion) throws DalException {
        Criteria criteria = this.getCriteriaInstance();
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if(criterion != null) criteria.add(criterion);
        return criteria.list();
    }

    @Override
    public Criteria getCriteriaInstance() throws DalException {
        return this.getCurrentSession().createCriteria(StoreConfig.class);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreConfig getById(String id) throws DalException {
        return (StoreConfig) getCurrentSession().get(StoreConfig.class, id);
    }

    @Override
    @Transactional
    public String addResource(StoreConfig storeConfig) throws DalException {
        try {
            getCurrentSession().save(storeConfig);
            return storeConfig.getId();
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new DalException("Failed to add StoreConfig.", e);
        }
    }

    @Override
    @Transactional
    public String updateResource(StoreConfig storeConfig) throws DalException {
        try {
            getCurrentSession().update(storeConfig);
            return storeConfig.getId();
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new DalException("Failed to update StoreConfig.", e);
        }
    }

    @Override
    @Transactional
    public String saveOrUpdateResource(StoreConfig storeConfig) throws DalException {
        try {
            getCurrentSession().saveOrUpdate(storeConfig);
            return storeConfig.getId();
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new DalException("Failed to update StoreConfig.", e);
        }
    }

    @Override
    @Transactional
    public void deleteResource(StoreConfig storeConfig) throws DalException {
        try {
            getCurrentSession().delete(storeConfig);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new DalException("Failed to delete StoreConfig.", e);
        }
    }
}
