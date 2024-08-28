package com.ericsson.projectb7.dao;

import com.ericsson.projectb7.pojo.CnaDetails;
import com.ericsson.projectb7.pojo.EnmProjectDetails;
import com.ericsson.projectb7.pojo.ExcelRecord;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Repository
public class EnmProjectListDao {

    @Value("${projects.file.name}")
    private String enmProjectFileName;

    private Map<String, List<ExcelRecord>> cnaENMProjects = new HashMap<>();
    private List<EnmProjectDetails> enmProjectsList = new ArrayList<>();
    private List<CnaDetails> cnaDetailsList = new ArrayList<>();
    private Map<String, String> cxpNameNumberInfo = new HashMap<>();
    private Map<String, String> cxpNumberCnaInfo = new HashMap<>();
    private Map<String, String> rpmCxpNumberInfo = new HashMap<>();

    public Map<String, String> getRpmCxpNumberInfo(){
        return rpmCxpNumberInfo;
    }

    private void addCxpNameNumberDetails(String cxpName, String cxpNumber){
        cxpNameNumberInfo.put(cxpName, cxpNumber);
    }

    private void addCxpNumberCnaName(String cxpNumber, String cnaName){
        cxpNumberCnaInfo.put(cxpNumber, cnaName);
    }

    public Map<String, String> getCxpNameNumberInfoDetails(){
        return cxpNameNumberInfo;
    }

    public Map<String , String> getCxpNumberCnaInfoDetails(){return cxpNumberCnaInfo;}

    private void updateCnaList(String cnaName, String cnaNumber){
        cnaDetailsList.add(new CnaDetails(cnaName, cnaNumber));
    }

    private void updateEnmProjectList(String cxp, String number, String rpm, String cnaName){
        if(cxp.trim().length() != 0 && number.trim().length() != 0)
            enmProjectsList.add(new EnmProjectDetails(cxp, number, rpm, cnaName));
    }

    public List<ExcelRecord> getProjectDetails(String cnaName){
        return cnaENMProjects.get(cnaName);
    }

    public List<EnmProjectDetails> getEnmProjectsList(){
        return enmProjectsList;
    }

    public List<CnaDetails> getCnaDetails(){
        return cnaDetailsList;
    }

    public void initializeEnmProjects() throws IOException {
        try(InputStream inputStream =  getClass().getResourceAsStream(enmProjectFileName);
            Workbook workbook = WorkbookFactory.create(inputStream);){

            // Getting the Sheet at index zero
            Sheet sheet = workbook.getSheetAt(0);

            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            // 1. You can obtain a rowIterator and columnIterator and iterate over them
            Iterator<Row> rowIterator = sheet.rowIterator();

            // Ignore first record. column names only exists in first record.
            rowIterator.next();

            if (rowIterator.hasNext()) {
                ExcelRecord cnaRecord = null;
                String cnaName = null;
                do {

                    if (isCnaRecord(cnaRecord) && isEnmProject(cnaRecord)) {
                        updateCnaList(cnaRecord.getCnaName(), cnaRecord.getNumber());
                        List<ExcelRecord> excelRecordList = new ArrayList<>();

                        cnaName = cnaRecord.getCnaName();

                        if (rowIterator.hasNext()) {
                            do {
                                cnaRecord = getNextEnmRecord(rowIterator, dataFormatter);

                                excelRecordList.add(cnaRecord);
                            } while (!isCnaRecord(cnaRecord) && rowIterator.hasNext());
                        }
                        excelRecordList.remove(excelRecordList.size() - 1);

                        for(ExcelRecord excelRecord: excelRecordList) {
                            addCxpNameNumberDetails(excelRecord.getCxp(), excelRecord.getNumber());
                            addCxpNumberCnaName(excelRecord.getNumber(), cnaName);
                            if (!excelRecord.getRpm().isEmpty() && excelRecord.getRpm().lastIndexOf("/") != -1) {
                                rpmCxpNumberInfo.put(excelRecord.getRpm().substring(excelRecord.getRpm().lastIndexOf("/") + 1).trim(), excelRecord.getNumber());
                                updateEnmProjectList(excelRecord.getCxp(), excelRecord.getNumber(), excelRecord.getRpm().substring(excelRecord.getRpm().lastIndexOf("/") + 1).trim(), cnaName);
                            }else{
                                updateEnmProjectList(excelRecord.getCxp(), excelRecord.getNumber(), excelRecord.getRpm(), cnaName);
                            }
                        }
                        cnaENMProjects.put(cnaName, excelRecordList);

                    } else {
                        cnaRecord = getNextEnmRecord(rowIterator, dataFormatter);
                    }
                } while (rowIterator.hasNext());
            }
        }catch(NullPointerException npe){
            throw new FileNotFoundException("File not found:");
        }
    }

    private ExcelRecord getNextEnmRecord(Iterator<Row> rowIterator, DataFormatter dataFormatter){
        Row row = rowIterator.next();
        return getEnmProjectRecord(row, dataFormatter);
    }

    private ExcelRecord getEnmProjectRecord(Row row, DataFormatter dataFormatter){
        // Now let's iterate over the columns of the current row
        Iterator<Cell> cellIterator = row.cellIterator();

        int column = 0;

        ExcelRecord record = new ExcelRecord();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);

            switch (column){
                case 0:
                    record.setCsa(cellValue);
                    break;
                case 1:
                    record.setCra(cellValue);
                    break;
                case 2:
                    record.setCnaName(cellValue);
                    break;
                case 3:
                    record.setCxp(cellValue);
                    break;
                case 4:
                    record.setNumber(cellValue);
                    break;
                case 5:
                    record.setResponsibleLineManager(cellValue);
                    break;
                case 6:
                    record.setSarLineManager(cellValue);
                    break;
                case 7:
                    record.setSar(cellValue);
                    break;
                case 8:
                    record.setSsr(cellValue);
                    break;
                case 9:
                    record.setAssosciateRa(cellValue);
                    break;
                case 10:
                    record.setMhoName(cellValue);
                    break;
                case 11:
                    record.setCnaFunctionResp(cellValue);
                    break;
                case 12:
                    record.setModules(cellValue);
                    break;
                case 13:
                    record.setDelivered(cellValue);
                    break;
                case 14:
                    record.setIssueRelease(cellValue);
                    break;
                case 15:
                    record.setRepo(cellValue);
                    break;
                case 16:
                    record.setRpm(cellValue);
                    break;
                case 17:
                    record.setTaf(cellValue);
                    break;
                case 18:
                    record.setTicketNumber(cellValue);
                    break;
                case 19:
                    record.setComments(cellValue);
                    break;
                case 20:
                    record.setCxpResponsible(cellValue);
                    break;
                case 21:
                    record.setLineManager(cellValue);
                    break;
                case 22:
                    record.setIsObsolete(cellValue);
                    break;
                case 23:
                    record.setThreePP(cellValue);
                    break;
                default:
                    break;
            }
            column++;
        }
        return record;
    }

    private boolean isCnaRecord(ExcelRecord excelRecord){
        return excelRecord != null && excelRecord.getCnaName().trim().length()>0;
    }

    private boolean isEnmProject(ExcelRecord excelRecord){
        return (excelRecord.getMhoName().trim().startsWith("ENM") || excelRecord.getMhoName().trim().startsWith("enm"));
    }

}
