/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao;

import java.util.List;

/**
 *
 * @author rabeh
 */
public interface ModelDao<T> {
    
    public boolean insert(T object);
    public boolean update(T object);
    public boolean delete(int id);
    public boolean delete(T object);
    public T get(int id);
    public List<T> getbyObjet(int id);
    public List<T> get();
    public T getByName(String name);
    public T getByID(int id);
    public List<T> getByNames(String... name);
    public T getByIids(int... id);
    
}
