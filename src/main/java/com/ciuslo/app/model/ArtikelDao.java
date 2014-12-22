/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ciuslo.app.model;

import com.ciuslo.app.entity.Artikel;
import java.util.ArrayList;

/**
 *
 * @author HP
 * @param <T>
 */
public class ArtikelDao<T extends Artikel> implements ArtikelService<T>{
    ArrayList<T> storage;
    public ArtikelDao() {
        storage = new ArrayList<T>();
    }
    
    @Override
    public Boolean create(T entity) {
        storage.add(entity);
        return null;
    }

    @Override
    public T readOne(int id) {
        return storage.get(id);
    }

    @Override
    public ArrayList<T> readAll() {
        return storage;
    }

    @Override
    public Boolean update(int id, String title, String summary, String content) {
        T entity = storage.get(id);

        entity.setSummary(summary);
        entity.setTitle(title);
        entity.setContent(content);

        return true;
    }

    @Override
    public Boolean delete(int id) {
        storage.get(id).delete();
        return true;
    }
    
}
