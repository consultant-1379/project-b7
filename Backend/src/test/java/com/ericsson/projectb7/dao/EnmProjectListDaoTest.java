package com.ericsson.projectb7.dao;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EnmProjectListDaoTest {
    @Test
    public void testClass(){
        new EnmProjectListDao();
    }

    private EnmProjectListDao enmProjectListDao = new EnmProjectListDao();

//    @Test
//    public void testEnmExcelFileNotExists(){
//        ReflectionTestUtils.setField(enmProjectListDao, "enmProjectFileName", ResourceUtils.CLASSPATH_URL_PREFIX+"invalidFile.xlsx");
//        try{
//            enmProjectListDao.getEnmProjectFile();
//        }catch(FileNotFoundException fnfe){
//            Assert.isTrue(true, "Exception occured");
//        }
//    }
//
//    @Test
//    public void testEnmFileExists() throws IOException {
//        ReflectionTestUtils.setField(enmProjectListDao, "enmProjectFileName", "/export.xlsx");
//        enmProjectListDao.getEnmProjectFile().read();
//    }

    @Test
    public void testEmptyProjectExportFile() throws IOException {
        ReflectionTestUtils.setField(enmProjectListDao, "enmProjectFileName", "/NoEnmProjectsExport.xlsx");
        try {
            enmProjectListDao.initializeEnmProjects();
            Assert.isTrue(enmProjectListDao.getEnmProjectsList().isEmpty(), "Assert failed has ENM projects");
            Assert.isTrue(enmProjectListDao.getCnaDetails().isEmpty(), "Assert failed has ENM projects");
        }catch (NullPointerException npe){

        }
    }

    @Test
    public void testExportExcelFile() throws IOException {
        ReflectionTestUtils.setField(enmProjectListDao, "enmProjectFileName", "/export.xlsx");
        enmProjectListDao.initializeEnmProjects();
        Assert.isTrue(!enmProjectListDao.getEnmProjectsList().isEmpty(), "Assert failed has ENM projects");
        Assert.isTrue(!enmProjectListDao.getCnaDetails().isEmpty(), "Assert failed has ENM projects");

    }
}
