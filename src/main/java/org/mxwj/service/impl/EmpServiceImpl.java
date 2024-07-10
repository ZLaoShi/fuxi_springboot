package org.mxwj.service.impl;

import org.mxwj.dao.EmpDAO;
import org.mxwj.entity.Emp;
import org.mxwj.service.EmpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {
    @Resource
    private EmpDAO empDAO;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Emp> findAll() {
        return empDAO.findAll();
    }

    @Override
    public void save(Emp emp) {
        empDAO.save(emp);
    }

    @Override
    public void delete(String id) {
        empDAO.delete(id);
    }

    @Override
    public Emp findOne(String id) {

        return empDAO.findOne(id);
    }

    @Override
    public void upDate(Emp emp) {
        empDAO.upDate(emp);
    }


}
