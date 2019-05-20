/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service;

import java.util.List;

/**
 *
 * @author rabeh
 */
public interface ModelService<T> {
    
    public boolean insertObject(T object);
    public boolean updateObject(T object);
    public boolean deleteObject(T object);
    public boolean deleteObject(int id);
    public List<T> getObject();
    public List<T> getObjects(int id);
    public T getObject(int id);
    public T getByName(String name);
    public T getByID(int id);
    public List<T> getByNames(String... name);
    public T getByIids(int... id);
    
}
