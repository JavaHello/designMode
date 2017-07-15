package com.openfree.service;

import java.util.List;
import java.util.Map;

/**
 * Created by luokai on 17-7-15.
 */
public interface BaseService<T> {
    int addObj(T t);
    int delObj(Long id);
    int updateObj(T t);
    T findById(Long id);
    List<T> findAll(Map<String, Object> queryMap);
}
