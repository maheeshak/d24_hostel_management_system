package dao.custom.impl;

import dao.custom.UserDAO;
import entity.Student;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> getAll() {


        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM User");
        nativeQuery.addEntity(User.class);
        List<User> users = nativeQuery.list();


        transaction.commit();
        session.close();

        return users;
    }

    @Override
    public boolean add(User user) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(user);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(User user) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(user);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public String generateNewID() {
        return null;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, id);
        session.remove(user);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public User search(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, id);


        transaction.commit();
        session.close();

        return user;
    }

    @Override
    public boolean valid(User user) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        User current_user = session.get(User.class, user.getId());
        if (current_user.getPassword().equals(user.getPassword())) {
            return true;
        }

        transaction.commit();
        session.close();

        return false;
    }
}
