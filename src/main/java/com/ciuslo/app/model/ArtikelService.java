/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ciuslo.app.model;

import java.util.ArrayList;

/**
 *
 * @author HP
 * @param <T>
 */
public interface ArtikelService<T> {
    public Boolean create(T entity);
    public T readOne(int id);
    public ArrayList<T> readAll();
    public Boolean update(int id, String title, String summary, String content);
    public Boolean delete(int id);
}
