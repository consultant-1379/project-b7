package com.ericsson.projectb7.controllers;

import com.ericsson.projectb7.dao.EnmProjectListDao;
import com.ericsson.projectb7.pojo.CnaDetails;
import com.ericsson.projectb7.pojo.EnmProjectDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin
public class EnmProjectListController {

    @Autowired
    private EnmProjectListDao enmDao;

    @GetMapping("/enmProjectsList")
    public List<EnmProjectDetails> getEnmProjectsList(){
        return enmDao.getEnmProjectsList();
    }

    @GetMapping("/getCnaNames")
    public List<CnaDetails> getCnaNames(){
        return enmDao.getCnaDetails();
    }
}
