package com.xxxlin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by qyf on 2017/11/28.
 */
public class CommonRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new CommonRepositoryFactory(em);
    }

    public CommonRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    //创建一个内部类，该类不用在外部访问
    private static class CommonRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        private final EntityManager em;

        public CommonRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }



        //设置具体的实现类是CommonRepositoryImpl
        // 旧版
//        @Override
//        protected Object getTargetRepository(RepositoryInformation information) {
//            return new CommonRepositoryImpl<T, I>((Class<T>) information.getDomainType(), em);
//        }

        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            JpaEntityInformation<?, Serializable> entityInformation = this.getEntityInformation(information.getDomainType());
            Object repository = this.getTargetRepositoryViaReflection(information, new Object[]{entityInformation, entityManager});
            Assert.isInstanceOf(CommonRepositoryImpl.class, repository);
            return (JpaRepositoryImplementation)repository;
        }

        //设置具体的实现类的class
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return CommonRepositoryImpl.class;
        }
    }
}
