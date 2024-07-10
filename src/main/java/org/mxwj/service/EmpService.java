package org.mxwj.service;

import org.mxwj.entity.Emp;

import java.util.List;

public interface EmpService {

    List<Emp> findAll();

    void save(Emp emp);

    void delete(String id);

    Emp findOne(String id);

    void upDate(Emp emp);
}
