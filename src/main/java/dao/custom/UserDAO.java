package dao.custom;

import dao.CrudDAO;
import entity.User;

public interface UserDAO extends CrudDAO<User> {

    boolean valid(User user);
}
