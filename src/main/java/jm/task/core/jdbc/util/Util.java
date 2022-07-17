package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class Util {

    public SessionFactory getSession() {
        return createSession();
    }

    private SessionFactory createSession() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(setting()).build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Metadata metadata = metadataSources.buildMetadata();
        return  metadata.getSessionFactoryBuilder().build();
    }

    private Map<String, String> setting() {
        Map<String, String> map = new HashMap<>();
        map.put("connection.driver_class", "com.mysql.cj.jdbc.Driver");
        map.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/user");
        map.put("hibernate.connection.username", "root");
        map.put("hibernate.connection.password", "root");
        map.put("hibernate.show_sql", "true");
        map.put("hibernate.format_sql", "true");
        map.put("hibernate.current_session_context_class", "thread");
        map.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
        return map;
    }
}
