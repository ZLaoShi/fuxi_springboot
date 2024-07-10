package org.mxwj.dao;

import org.apache.ibatis.annotations.Mapper;
import org.mxwj.entity.Emp;

import java.util.List;

@Mapper
public interface EmpDAO {

    List<Emp> findAll();

    void save(Emp emp);

    void delete(String id);

    Emp findOne(String id);

    void upDate(Emp emp);
}
