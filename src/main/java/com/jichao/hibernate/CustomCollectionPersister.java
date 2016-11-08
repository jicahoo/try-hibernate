package com.jichao.hibernate;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.QueryException;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.entry.CacheEntryStructure;
import org.hibernate.cfg.Configuration;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SubselectFetch;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.FilterAliasGenerator;
import org.hibernate.loader.collection.CollectionInitializer;
import org.hibernate.mapping.Collection;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.persister.collection.AbstractCollectionPersister;
import org.hibernate.persister.collection.BasicCollectionPersister;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.collection.SQLLoadableCollection;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.Joinable;
import org.hibernate.persister.walking.spi.CollectionElementDefinition;
import org.hibernate.persister.walking.spi.CollectionIndexDefinition;
import org.hibernate.type.CollectionType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangj52 on 11/7/2016.
 */
//public class CustomCollectionPersister implements CollectionMetadata, SQLLoadableCollection {
public class CustomCollectionPersister extends BasicCollectionPersister {

    public CustomCollectionPersister(Collection collection, CollectionRegionAccessStrategy cacheAccessStrategy, Configuration cfg, SessionFactoryImplementor factory) throws MappingException, CacheException {
        super(collection, cacheAccessStrategy, cfg, factory);
    }
    @Override
    protected String generateDeleteString() {
        return "";
    }
}
