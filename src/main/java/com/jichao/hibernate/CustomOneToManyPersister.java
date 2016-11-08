package com.jichao.hibernate;

import org.hibernate.MappingException;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.mapping.Collection;
import org.hibernate.persister.collection.OneToManyPersister;

/**
 * Created by zhangj52 on 11/7/2016.
 */
public class CustomOneToManyPersister extends OneToManyPersister{

    public CustomOneToManyPersister(Collection collection, CollectionRegionAccessStrategy cacheAccessStrategy, Configuration cfg, SessionFactoryImplementor factory) throws MappingException, CacheException {
        super(collection, cacheAccessStrategy, cfg, factory);
    }
}
