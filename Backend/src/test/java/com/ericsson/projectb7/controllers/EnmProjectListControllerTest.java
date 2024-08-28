package com.ericsson.projectb7.controllers;

import com.ericsson.projectb7.dao.EnmProjectListDao;
import com.ericsson.projectb7.pojo.CnaDetails;
import com.ericsson.projectb7.pojo.EnmProjectDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.mockito.InOrder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class EnmProjectListControllerTest {

    private EnmProjectListController enmProjectListController = new EnmProjectListController();
    private EnmProjectListDao enmProjectListDao;

    @BeforeEach
    public void init(){
        enmProjectListDao = mock(EnmProjectListDao.class);
        ReflectionTestUtils.setField(enmProjectListController, "enmDao",enmProjectListDao);
    }

    @Test
    public void testGetEnmProjectList(){
        List<EnmProjectDetails> enmProjectDetailsList = new ArrayList<>();
        enmProjectDetailsList.add(new EnmProjectDetails("Node Discovery Module", "CXP 903 1393", "kjakdfj/asfkjadskfj/sadfaskfk", "kdjasfklasjfk adksfajsdl"));

        when(enmProjectListDao.getEnmProjectsList()).thenReturn(enmProjectDetailsList);
        enmProjectListController.getEnmProjectsList();

        InOrder inOrder = inOrder(enmProjectListDao);
        inOrder.verify(enmProjectListDao).getEnmProjectsList();

    }

    @Test
    public void testGetCnaNames(){
        List<CnaDetails> cnaDetailsList = new ArrayList<>();
        cnaDetailsList.add(new CnaDetails("Shared Node Mediation", "CNA 903 1393"));

        when(enmProjectListDao.getCnaDetails()).thenReturn(cnaDetailsList);
        List<CnaDetails> resultCnaDetailsList = enmProjectListController.getCnaNames();

        InOrder inOrder = inOrder(enmProjectListDao);
        inOrder.verify(enmProjectListDao).getCnaDetails();
        Assert.isTrue("CNA 903 1393".equals(resultCnaDetailsList.get(0).getCnaNumber()), "CNA number comparision");
    }
}
