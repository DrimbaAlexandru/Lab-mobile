package service;

import java.util.ArrayList;

/**
 * Created by Xoxii on 20-Mar-17.
 */
public interface IService<ID, T> {
    Integer size();
    void save(T entity);
    void delete(ID id);
    void update(ID id, T entity);
    T findOne(ID id);
    ArrayList<T> findAll();
}
