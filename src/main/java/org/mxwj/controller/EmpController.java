package org.mxwj.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.mxwj.entity.Emp;
import org.mxwj.service.EmpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("emp")
@CrossOrigin
@Slf4j
public class EmpController {

    @Resource
    private EmpService empService;

    @Value("${photo.dir}")
    private String photoPath;

    //修改员工信息
    @PostMapping("upDate")
    public Map<String, Object> upDate(Emp emp, MultipartFile photo) throws IOException {
        log.info("员工信息:[{}]", emp.toString());

        Map<String, Object> map = new HashMap<>();

        try {
            if(photo != null&&photo.getSize() != 0){
                    log.info("头像信息:[{}]", photo.getOriginalFilename());  //wawTODO  one
                Emp emp1 = empService.findOne(emp.getId());
                //头像保存
                String newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(photo.getOriginalFilename()); //生成新文件名
                photo.transferTo(new File(photoPath, newFileName));
                //设置头像地址
                emp.setPath(newFileName);

                //删除旧头像
                File file = new File(photoPath, emp1.getPath());
                if(file.exists()) {
                    file.delete();
                }
            }

            //保存员工信息
            empService.upDate(emp);
            map.put("state", true);
            map.put("msg", "员工信息更改成功");

        }catch (Exception e){
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", "员工信息更改失败");
        }

        return map;
    }

    //保存员工信息
    @PostMapping("save")
    public Map<String, Object> save(Emp emp, MultipartFile photo) throws IOException {
        log.info("员工信息:[{}]", emp.toString());
        log.info("头像信息:[{}]", photo.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();

        try {
            //头像保存
            String newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(photo.getOriginalFilename()); //生成新文件名
            photo.transferTo(new File(photoPath, newFileName));
            //设置头像地址
            emp.setPath(newFileName);
            //保存员工信息
            empService.save(emp);

            map.put("state", true);
            map.put("msg", "员工信息保存成功");

        }catch (Exception e){
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", "员工信息保存失败");
        }

        return map;
    }

    //获取员工列表
    @GetMapping("findAll")
    public List<Emp> findAll() {
        return empService.findAll();
    }

    //删除员工信息
    @GetMapping("delete")
    public Map<String, Object> delete(String id) {
        log.info("删除员工的id:[{}]", id);
        Map<String, Object> map = new HashMap<>();

        try{
            //删除员工头像
            Emp emp = empService.findOne(id);
            File file = new File(photoPath, emp.getPath());
            if(file.exists()) {
                file.delete();
            }
            //删除员工信息
            empService.delete(id);
            map.put("state", true);
            map.put("msg", "员工删除成功");
        } catch (Exception e){
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", "员工删除失败");
        }

        return map;
    }

    //获取个人信息
    @GetMapping("findOne")
    public Emp findOne(String id) {

        return empService.findOne(id);
    }

}
