package com.ericsson.projectb7.controllers;

import com.ericsson.projectb7.jenkins.JenkinsService;
import com.ericsson.projectb7.pojo.JenkinsTestAlertReport;
import com.ericsson.projectb7.pojo.JenkinsTestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class JenkinsController {

    @Autowired
    private JenkinsService service;

    @GetMapping("/getReport")
     public List<JenkinsTestReport> getReport(@RequestParam(name="startDate") long startDatemillisec, @RequestParam(name="endDate") long endDatemillis,@RequestParam(name="product", defaultValue = "") String productName, @RequestParam(name="cna", defaultValue = "") String cnaName) throws IOException
    {
        if(productName.isEmpty() && cnaName.isEmpty())
            return service.getTestReportsWithinTimePeriod(startDatemillisec, endDatemillis);
        else if(productName.isEmpty() && !cnaName.isEmpty())
             return service.getCnaTestReportWithInTimePeriod(cnaName,startDatemillisec, endDatemillis);
        else if(!productName.isEmpty() && cnaName.isEmpty())
            return service.getProductTestReportsWithInTimePeriod(productName, startDatemillisec, endDatemillis);
        else
            return service.getProductTestReportsWithInTimePeriod(productName, startDatemillisec, endDatemillis);
    }

    @GetMapping("/getHighestFailureRate")
    public List<JenkinsTestReport> getHighestFailureRateCna(@RequestParam(name="threshold", defaultValue = "0.0") double threshold,@RequestParam(name="cna", defaultValue = "") String cna, @RequestParam(name="product", defaultValue = "") String product){
        if(!cna.isEmpty() && !product.isEmpty())
            return service.getProductJenkinsTestReportHighestFailureRate(threshold, product);
        else if(!cna.isEmpty() && product.isEmpty())
            return service.getCnaJenkinsTestReportHighestFailureRate(threshold, cna);
        else if(cna.isEmpty() && !product.isEmpty())
            return service.getProductJenkinsTestReportHighestFailureRate(threshold, product);
        else
            return service.getJenkinsTestReportHighestFailureRate(threshold);
    }

    @GetMapping("/getTestAlerts")
    public List<JenkinsTestAlertReport> getTestAlerts(@RequestParam(name="noOfDays", defaultValue = "7") int noOfDays){
        return service.getTestAlertsThatRanBefore(noOfDays);
    }

}
