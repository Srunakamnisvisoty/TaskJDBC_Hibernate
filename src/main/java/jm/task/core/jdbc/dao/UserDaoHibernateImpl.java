package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final Util util;

    public UserDaoHibernateImpl() {
        this.util = new Util();
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `user` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "  `name` varchar(255) COLLATE 'utf8_general_ci' NOT NULL,\n" +
                "  `lastName` varchar(255) COLLATE 'utf8_general_ci' NOT NULL,\n" +
                "  `age` int NOT NULL\n" +
                ") ENGINE='InnoDB' COLLATE 'utf8_general_ci';";
        try {
            SessionFactory sessionFactory = util.getSession();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery(sql);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try {
            SessionFactory sessionFactory = util.getSession();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery(sql);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?);";
        try {
            SessionFactory sessionFactory = util.getSession();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            NativeQuery<User> nativeQuery = session.createNativeQuery(sql, User.class);
            nativeQuery.setParameter(1, name);
            nativeQuery.setParameter(2, lastName);
            nativeQuery.setParameter(3, age);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            SessionFactory sessionFactory = util.getSession();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            NativeQuery<User> nativeQuery = session.createNativeQuery(sql, User.class);
            nativeQuery.setParameter(1, id);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT id, name, lastName, age FROM user";
        try {
            SessionFactory sessionFactory = util.getSession();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            NativeQuery<User> nativeQuery = session.createNativeQuery(sql, User.class);
            session.getTransaction().commit();
            session.close();
            return nativeQuery.getResultList();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE user";
        try {
            SessionFactory sessionFactory = util.getSession();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createNativeQuery(sql);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }
}
