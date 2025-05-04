package com.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            Metadata metadata = new MetadataSources(registry)
                    .addAnnotatedClass(com.example.model.User.class)
                    .getMetadataBuilder()
                    .build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Hibernate", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}