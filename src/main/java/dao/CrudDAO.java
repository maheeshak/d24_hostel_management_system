package dao;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{

    public List<T> getAll();

    public boolean add(T entity);

    public boolean update(T entity);

    public String generateNewID();

    public boolean delete(String id);

    public T search(String id);


}
