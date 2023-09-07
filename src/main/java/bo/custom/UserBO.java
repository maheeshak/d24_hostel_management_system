package bo.custom;

import bo.SuperBO;

import java.util.List;

public interface UserBO<T> extends SuperBO {
    public List<T> getAllUsers();

    public boolean addUser(T entity);

    public boolean updateUser(T entity);

    public String generateNewUserID();

    public boolean deleteUser(String id);

    public T searchUser(String id);
    public boolean validUser(T id);
}
