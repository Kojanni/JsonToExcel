package dadata;

import com.google.gson.annotations.SerializedName;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.CDL;

public class CreatorExcel {
    private static final String SHEET_NAME = "DadataSheet";
    private static final int rowValue = 0;
    private static final int rowUnrestrictedValue = 1;
    private static final int rowData = 2;
    private static final int rowInn = 3;
    private static final int rowKpp = 4;
    private static final int rowOgrn = 5;
    private static final int rowOgrnDate = 6;
    private static final int rowHid = 7;
    private static final int rowType = 8;
    private static final int rowName = 9; //(5)
    private static final int rowOkpo = 15;
    private static final int rowOkved = 16;
    private static final int rowOkvedType = 17;
    private static final int rowOpf = 18; //4
    private static final int rowManagement = 23; //2
    private static final int rowBranchType = 26;
    private static final int rowBranchCount = 27;
    private static final int rowAddress = 28; //3+2
    private static final int rowState = 35; //5
    private static final int rowEmployeeCount = 41;
    private static int rowAuthorities = 42;
    private static int rowFounder = 68; //8+4
    private static int rowManager = 77;
    private static int rowFinance = 84;
    private static int rowCapital = 89;
    private static int rowDocuments = 91;
    private static int rowLicense = 120;

    private static int columNum = 0;
    private static int rowNum = 0;
    private static int columNumLast = 0;
    private static int rowNumLast = 0;

    public void createExcelFromDadata(Suggestions dadataList) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(SHEET_NAME);
        sheet.setDefaultColumnWidth(25);


        Cell cell;
        Row row;

        createMainCellForDadataFields(workbook);

        for (Dadata dadata : dadataList.getSuggestions()) {
            createCellForDadata(workbook, dadata);
        }


        //save
        File file = new File("data/dadata.xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());
    }

    private void createMainCellForDadataFields(HSSFWorkbook workbook) {
        HSSFSheet sheet = workbook.getSheet(SHEET_NAME);
        HSSFCellStyle style = createStyleForTitle(workbook);

        Cell cell;
        Field[] dadataFields = Dadata.class.getDeclaredFields();
        Field[] dataFields = DadataData.class.getDeclaredFields();
        Field[] opfFields = Opf.class.getDeclaredFields();
        Field[] managementFields = Management.class.getDeclaredFields();
        Field[] addressFields = Address.class.getDeclaredFields();
        Field[] stateFields = State.class.getDeclaredFields();
        Field[] authoritiesFields = Authorities.class.getDeclaredFields();
        Field[] foundersFields = Founder.class.getDeclaredFields();
        Field[] managersFields = Manager.class.getDeclaredFields();
        Field[] financeFields = Finance.class.getDeclaredFields();
        Field[] capitalFields = Capital.class.getDeclaredFields();
        Field[] documentsFields = Documents.class.getDeclaredFields();
        Field[] licensesFields = License.class.getDeclaredFields();
        Field[] nameFields = Name.class.getDeclaredFields();

        createCellForFieldOfClass(sheet, dadataFields);

//data:
        for (Field dataField : dataFields) {
            sheet.createRow(rowNum);
            cell = sheet.getRow(rowNum).createCell(columNum, CellType.STRING);
            cell.setCellValue(dataField.getName());
            cell.setCellStyle(style);

            rowNum++;
            if (dataField.getName().equalsIgnoreCase("opf")) {
                createCellForFieldOfClass(sheet, opfFields);
            } else if (dataField.getName().equalsIgnoreCase("management")) {
                createCellForFieldOfClass(sheet, managementFields);
            } else if (dataField.getName().equalsIgnoreCase("address")) {
                createCellForFieldOfClass(sheet, addressFields);
                Field[] addressDataFields = Address.DataAddress.class.getDeclaredFields();
                createCellForFieldOfClass(sheet, addressDataFields);
            } else if (dataField.getName().equalsIgnoreCase("state")) {
                createCellForFieldOfClass(sheet, stateFields);
            } else if (dataField.getName().equalsIgnoreCase("authorities")) {
                Field[] authoritiesRegistrationFields = Authorities.Registration.class.getDeclaredFields();

                for (Field authoritiesField : authoritiesFields) {
                    sheet.createRow(rowNum);
                    cell = sheet.getRow(rowNum).createCell(columNum, CellType.STRING);
                    cell.setCellValue(authoritiesField.getName());
                    rowNum++;
                    createCellForFieldOfClass(sheet, authoritiesRegistrationFields);
                }

            } else if (dataField.getName().equalsIgnoreCase("founders")) {
                createCellForFieldOfClass(sheet, foundersFields);
            } else if (dataField.getName().equalsIgnoreCase("managers")) {
                createCellForFieldOfClass(sheet, managersFields);
            } else if (dataField.getName().equalsIgnoreCase("finance")) {
                createCellForFieldOfClass(sheet, financeFields);
            } else if (dataField.getName().equalsIgnoreCase("capital")) {
                createCellForFieldOfClass(sheet, capitalFields);
            } else if (dataField.getName().equalsIgnoreCase("documents")) {
                Field[] documentsRegistrationFields = Documents.Registration.class.getDeclaredFields();
                Field[] documentsSmbFields = Documents.Smb.class.getDeclaredFields();

                for (Field documentsField : documentsFields) {
                    sheet.createRow(rowNum);
                    cell = sheet.getRow(rowNum).createCell(columNum, CellType.STRING);
                    cell.setCellValue(documentsField.getName());
                    rowNum++;
                    if (documentsField.getName().contains("Registration")) {
                        createCellForFieldOfClass(sheet, documentsRegistrationFields);
                    }
                    if (documentsField.getName().equalsIgnoreCase("smb")) {
                        createCellForFieldOfClass(sheet, documentsSmbFields);
                    }
                }

            } else if (dataField.getName().equalsIgnoreCase("licenses")) {
                createCellForFieldOfClass(sheet, licensesFields);
            } else if (dataField.getName().equalsIgnoreCase("name")) {
                createCellForFieldOfClass(sheet, nameFields);
            }
        }
    }

    private void createCellForFieldOfClass(HSSFSheet sheet, Field[] fields) {
        Cell cell;
        for (Field field : fields) {
            sheet.createRow(rowNum);
            cell = sheet.getRow(rowNum).createCell(columNum, CellType.STRING);
            cell.setCellValue(field.getName());
            rowNum++;
        }

    }

    private void createCellForDadata(HSSFWorkbook workbook, Dadata dadata) {
        columNum++;
        columNumLast = columNum;
        Cell cell;

        HSSFSheet sheet = workbook.getSheet(SHEET_NAME);


        HSSFCellStyle styleForData = createStyleForData(workbook);
        HSSFCellStyle styleForHeader = createStyleForHeader(workbook);
        HSSFCellStyle styleForDataValue = createStyleForDataValue(workbook);

        // Value
        cell = sheet.getRow(rowValue).createCell(columNum, CellType.STRING);
        cell.setCellValue(dadata.getValue());

        cell.setCellStyle(styleForDataValue);

        // unrestricted_value
        cell = sheet.getRow(rowUnrestrictedValue).createCell(columNum, CellType.STRING);
        cell.setCellValue(dadata.getUnrestrictedValue());
        cell.setCellStyle(styleForData);

        DadataData data = dadata.getData();

        //inn
        cell = sheet.getRow(rowInn).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getInn());
        cell.setCellStyle(styleForData);

        //kpp
        cell = sheet.getRow(rowKpp).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getKpp());
        cell.setCellStyle(styleForData);

//ogrn
        cell = sheet.getRow(rowOgrn).createCell(columNum, CellType.NUMERIC);
        cell.setCellValue(data.getOgrn());
        cell.setCellStyle(styleForData);

//ogrnDate
        cell = sheet.getRow(rowOgrnDate).createCell(columNum, CellType.NUMERIC);
        cell.setCellValue(data.getOgrnDate() != null ? String.valueOf(data.getOgrnDate()) : "null");
        cell.setCellStyle(styleForData);
//hid
        cell = sheet.getRow(rowHid).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getHid());
        cell.setCellStyle(styleForData);
//type
        cell = sheet.getRow(rowType).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getType());
        cell.setCellStyle(styleForData);
//name
        cell = sheet.getRow(rowName).createCell(columNum, CellType.STRING);

        cell.setCellValue("");
        cell.setCellStyle(styleForHeader);

        cell = sheet.getRow(rowName + 1).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getName().getFullWithOpf());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowName + 2).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getName().getShortWithOpf());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowName + 3).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getName().getLatin());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowName + 4).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getName().getFullWithoutOpf());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowName + 5).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getName().getShortWithoutOpf());
        cell.setCellStyle(styleForData);

//okpo
        cell = sheet.getRow(rowOkpo).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getOkpo());
        cell.setCellStyle(styleForData);
//okved
        cell = sheet.getRow(rowOkved).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getOkved());
        cell.setCellStyle(styleForData);
//okvedType
        cell = sheet.getRow(rowOkvedType).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getOkvedType());
        cell.setCellStyle(styleForData);

//opf
        cell = sheet.getRow(rowOpf).createCell(columNum, CellType.STRING);

        cell.setCellValue("");
        cell.setCellStyle(styleForHeader);

        cell = sheet.getRow(rowOpf + 1).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getOpf().getCode());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowOpf + 2).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getOpf().getFullName());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowOpf + 3).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getOpf().getShortName());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowOpf + 4).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getOpf().getType());
        cell.setCellStyle(styleForData);

//management
        if (data.getManagement() != null) {
            cell = sheet.getRow(rowManagement).createCell(columNum, CellType.STRING);

            cell.setCellValue("");
            cell.setCellStyle(styleForHeader);

            cell = sheet.getRow(rowManagement + 1).createCell(columNum, CellType.STRING);
            cell.setCellValue(data.getManagement().getName());
            cell.setCellStyle(styleForData);

            cell = sheet.getRow(rowManagement + 2).createCell(columNum, CellType.STRING);
            cell.setCellValue(data.getManagement().getPost());
            cell.setCellStyle(styleForData);
        }
//branchType
        cell = sheet.getRow(rowBranchType).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getBranchType());
        cell.setCellStyle(styleForData);

//branchCount
        cell = sheet.getRow(rowBranchCount).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getBranchCount());
        cell.setCellStyle(styleForData);

//Address
        cell = sheet.getRow(rowAddress).createCell(columNum, CellType.STRING);

        cell.setCellValue("");
        cell.setCellStyle(styleForHeader);

        cell = sheet.getRow(rowAddress + 1).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getAddress().getValue());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowAddress + 2).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getAddress().getUnrestrictedValue());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowAddress + 3).createCell(columNum, CellType.STRING);
        cell.setCellStyle(styleForData);
        cell.setCellValue(data.getAddress().getData().toString());
        //Data
        cell = sheet.getRow(rowAddress + 4).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getAddress().getData().getQc());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowAddress + 5).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getAddress().getData().getSource());
        cell.setCellStyle(styleForData);

        //State
        cell = sheet.getRow(rowState).createCell(columNum, CellType.NUMERIC);

        cell.setCellValue("");
        cell.setCellStyle(styleForHeader);

        cell = sheet.getRow(rowState + 1).createCell(columNum, CellType.NUMERIC);
        cell.setCellValue(data.getState().getActualityDate());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowState + 2).createCell(columNum, CellType.NUMERIC);
        cell.setCellValue(data.getState().getRegistrationDate());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowState + 3).createCell(columNum, CellType.NUMERIC);
        cell.setCellValue(data.getState().getLiquidationDate());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowState + 4).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getState().getStatus());
        cell.setCellStyle(styleForData);

        cell = sheet.getRow(rowState + 5).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getState().getCode());
        cell.setCellStyle(styleForData);

        //EmployeeCount
        cell = sheet.getRow(rowEmployeeCount).createCell(columNum, CellType.STRING);
        cell.setCellValue(data.getEmployeeCount());
        cell.setCellStyle(styleForData);

        //Authorities
        cell = sheet.getRow(rowAuthorities).createCell(columNum, CellType.STRING);
        cell.setCellValue("");
        cell.setCellStyle(styleForHeader);

        if (data.getAuthorities() != null) {
            if (data.getAuthorities().getFtsRegistration() != null) {

                cell = sheet.getRow(rowAuthorities + 2).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getAuthorities().getFtsRegistration().getType() != null ? data.getAuthorities().getFtsRegistration().getType() : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 3).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getFtsRegistration().getCode());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 4).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getFtsRegistration().getName());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 5).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getAuthorities().getFtsRegistration().getAddress());
                cell.setCellStyle(styleForData);
            } else {
                for (int row = rowAuthorities + 3 ; row <= rowAuthorities + 5; row++) {
                    cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                    cell.setCellValue("");
                    cell.setCellStyle(styleForData);
                }
            }
            if (data.getAuthorities().getFtsReport() != null) {
                cell = sheet.getRow(rowAuthorities + 6 + 2).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getAuthorities().getFtsReport().getType());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 6 + 3).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getFtsReport().getCode());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 6 + 4).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getFtsReport().getName());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 6 + 5).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getAuthorities().getFtsReport().getAddress());
                cell.setCellStyle(styleForData);

            } else {
                for (int row = rowAuthorities + 6 + 3 ; row <= rowAuthorities + 6 + 5; row++) {
                    cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                    cell.setCellValue("");
                    cell.setCellStyle(styleForData);
                }
            }
            if (data.getAuthorities().getPf() != null) {
                cell = sheet.getRow(rowAuthorities + 12 + 2).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getPf().getType());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 12 + 3).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getPf().getCode());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 12 + 4).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getPf().getName());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 12 + 5).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getAuthorities().getPf().getAddress());
                cell.setCellStyle(styleForData);

            } else {
                for (int row = rowAuthorities + 12 + 3 ; row <= rowAuthorities + 12 + 5; row++) {
                    cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                    cell.setCellValue("");
                    cell.setCellStyle(styleForData);
                }
            }
            if (data.getAuthorities().getSif() != null) {
                cell = sheet.getRow(rowAuthorities + 18 + 2).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getSif().getType());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 18 + 3).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getSif().getCode());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 18 + 4).createCell(columNum, CellType.NUMERIC);
                cell.setCellValue(data.getAuthorities().getSif().getName());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowAuthorities + 18 + 5).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getAuthorities().getSif().getAddress());
                cell.setCellStyle(styleForData);
            } else {
                for (int row = rowAuthorities + 18 + 3 ; row <= rowAuthorities + 18 + 5; row++) {
                    cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                    cell.setCellValue("");
                    cell.setCellStyle(styleForData);
                }
            }
        } else {
            for (int row = rowAuthorities + 1; row <= rowAuthorities + 18 + 5; row++) {
                cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(styleForData);
            }
        }
        //Founder
        cell = sheet.getRow(rowFounder).createCell(columNum, CellType.STRING);
        cell.setCellValue("");
        cell.setCellStyle(styleForHeader);

        if (data.getFounders() != null) {
            List<Founder> founders = data.getFounders();
            int columnFounder = columNum;
            for (Founder founder : founders) {
                cell = sheet.getRow(rowFounder).createCell(columnFounder, CellType.NUMERIC);
                cell.setCellValue(founder.getOgrn());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowFounder + 1).createCell(columnFounder, CellType.NUMERIC);
                cell.setCellValue(founder.getInn());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowFounder + 2).createCell(columnFounder, CellType.NUMERIC);
                cell.setCellValue(founder.getName());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowFounder + 3).createCell(columnFounder, CellType.STRING);
                cell.setCellValue(founder.getFio() != null ? founder.getFio().toString() : null);
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowFounder + 4).createCell(columnFounder, CellType.STRING);
                cell.setCellValue(founder.getPost());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowFounder + 5).createCell(columnFounder, CellType.STRING);
                cell.setCellValue(founder.getHid());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowFounder + 6).createCell(columnFounder, CellType.STRING);
                cell.setCellValue(founder.getType());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowFounder + 7).createCell(columnFounder, CellType.STRING);
                cell.setCellValue(founder.getShare() != null ? founder.getShare().toString() : null);
                cell.setCellStyle(styleForData);

                columnFounder++;
            }

            columNumLast = columnFounder;

        } else {
            for (int row = rowFounder; row <= rowFounder + 7; row++) {
                cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(styleForData);
            }
        }
        //Manager
        cell = sheet.getRow(rowManager).createCell(columNum, CellType.NUMERIC);
        cell.setCellValue("");
        cell.setCellStyle(styleForHeader);

        if (data.getManagers() != null) {
            List<Manager> managers = data.getManagers();
            int columnManager = columNum;
            for (Manager manager : managers) {
                cell = sheet.getRow(rowManager).createCell(columnManager, CellType.NUMERIC);
                cell.setCellValue(manager.getOgrn());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowManager + 1).createCell(columnManager, CellType.NUMERIC);
                cell.setCellValue(manager.getInn());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowManager + 2).createCell(columnManager, CellType.NUMERIC);
                cell.setCellValue(manager.getName());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowManager + 3).createCell(columnManager, CellType.STRING);
                cell.setCellValue(manager.getFio() != null ? manager.getFio().toString() : null);
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowManager + 4).createCell(columnManager, CellType.STRING);
                cell.setCellValue(manager.getPost());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowManager + 5).createCell(columnManager, CellType.STRING);
                cell.setCellValue(manager.getHid());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowManager + 6).createCell(columnManager, CellType.STRING);
                cell.setCellValue(manager.getType());
                cell.setCellStyle(styleForData);

                columnManager++;
            }
            if (columNumLast < columnManager) {
                columNumLast = columnManager;
            }
        } else {
            for (int row = rowManager; row <= rowManager + 6; row++) {
                cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(styleForData);
            }
        }
        //Finance
        cell = sheet.getRow(rowFinance ).createCell(columNum, CellType.STRING);
        cell.setCellValue("");
        cell.setCellStyle(styleForHeader);

        if (data.getFinance() != null) {
            cell = sheet.getRow(rowFinance + 1).createCell(columNum, CellType.STRING);
            cell.setCellValue(data.getFinance().getDebt() != null ? String.valueOf(data.getFinance().getDebt()) : "null");
            cell.setCellStyle(styleForData);

            cell = sheet.getRow(rowFinance + 2).createCell(columNum, CellType.STRING);
            cell.setCellValue(data.getFinance().getExpense() != null ? String.valueOf(data.getFinance().getExpense()) : "null");
            cell.setCellStyle(styleForData);

            cell = sheet.getRow(rowFinance + 3).createCell(columNum, CellType.STRING);
            cell.setCellValue(data.getFinance().getIncome() != null ? String.valueOf(data.getFinance().getIncome()) : "null");
            cell.setCellStyle(styleForData);

            cell = sheet.getRow(rowFinance + 4).createCell(columNum, CellType.STRING);
            cell.setCellValue(data.getFinance().getPenalty() != null ? String.valueOf(data.getFinance().getPenalty()) : "null");
            cell.setCellStyle(styleForData);

        } else {
            for (int row = rowFinance + 2; row <= rowFinance + 4; row++) {
                cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(styleForData);
            }
        }

        //Capital
        if (data.getCapital() != null) {
            cell = sheet.getRow(rowCapital + 1).createCell(columNum, CellType.STRING);
            cell.setCellValue(data.getCapital().getType() != null ? data.getCapital().getType() : "null");
            cell.setCellStyle(styleForData);

            cell = sheet.getRow(rowCapital + 2).createCell(columNum, CellType.STRING);
            cell.setCellValue(data.getCapital().getValue() != null ? String.valueOf(data.getCapital().getValue()) : "null");
            cell.setCellStyle(styleForData);

        } else {
            for (int row = rowCapital; row <= rowCapital + 2; row++) {
                cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(styleForData);
            }
        }
        //Documents
        if (data.getDocuments() != null) {

            cell = sheet.getRow(rowDocuments + 2).createCell(columNum, CellType.STRING);
            cell.setCellValue("");
            cell.setCellStyle(styleForHeader);
            if (data.getDocuments().getFtsRegistration() != null) {
                cell = sheet.getRow(rowDocuments + 3).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getFtsRegistration().getType() != null ? data.getDocuments().getFtsRegistration().getType() : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 4).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getFtsRegistration().getSeries() != null ? String.valueOf(data.getDocuments().getFtsRegistration().getSeries()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 5).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getFtsRegistration().getNumber() != null ? String.valueOf(data.getDocuments().getFtsRegistration().getNumber()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 6).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getFtsRegistration().getIssueDate() != null ? String.valueOf(data.getDocuments().getFtsRegistration().getIssueDate()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 7).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getFtsRegistration().getIssueAuthority());
            } else {
                for (int row = rowDocuments + 3; row <= rowDocuments + 7; row++) {
                    cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                    cell.setCellValue("");
                    cell.setCellStyle(styleForData);
                }
            }

            cell = sheet.getRow(rowDocuments + 7 + 2).createCell(columNum, CellType.STRING);
            cell.setCellValue("");
            cell.setCellStyle(styleForHeader);
            if (data.getDocuments().getPfRegistration() != null) {


                cell = sheet.getRow(rowDocuments + 7 + 3).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getPfRegistration().getType());
                cell = sheet.getRow(rowDocuments + 7 + 4).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getPfRegistration().getSeries() != null ? String.valueOf(data.getDocuments().getPfRegistration().getSeries()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 7 + 5).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getPfRegistration().getNumber() != null ? String.valueOf(data.getDocuments().getPfRegistration().getNumber()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 7 + 6).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getPfRegistration().getIssueDate() != null ? String.valueOf(data.getDocuments().getPfRegistration().getIssueDate()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 7 + 7).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getPfRegistration().getIssueAuthority());
                cell.setCellStyle(styleForData);

            } else {
                for (int row = rowDocuments +7 + 3; row <= rowDocuments + 7 + 7; row++) {
                    cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                    cell.setCellValue("");
                    cell.setCellStyle(styleForData);
                }
            }

            cell = sheet.getRow(rowDocuments + 14 + 2).createCell(columNum, CellType.STRING);
            cell.setCellValue("");
            cell.setCellStyle(styleForHeader);
            if (data.getDocuments().getSifRegistration() != null) {

                cell = sheet.getRow(rowDocuments + 14 + 3).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getSifRegistration().getType());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 14 + 4).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getSifRegistration().getSeries() != null ? String.valueOf(data.getDocuments().getSifRegistration().getSeries()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 14 + 5).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getSifRegistration().getNumber() != null ? String.valueOf(data.getDocuments().getSifRegistration().getNumber()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 14 + 6).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getSifRegistration().getIssueDate() != null ? String.valueOf(data.getDocuments().getSifRegistration().getIssueDate()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 14 + 7).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getSifRegistration().getIssueAuthority());
            } else {
                for (int row = rowDocuments + 14 + 3; row <= rowDocuments + 14 + 7; row++) {
                    cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                    cell.setCellValue("");
                    cell.setCellStyle(styleForData);
                }
            }

            cell = sheet.getRow(rowDocuments + 21 + 2).createCell(columNum, CellType.STRING);
            cell.setCellValue("");
            cell.setCellStyle(styleForHeader);
            if (data.getDocuments().getSmb() != null) {
                cell = sheet.getRow(rowDocuments + 21 + 3).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getSmb().getType() != null ? data.getDocuments().getSmb().getType() : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 21 + 4).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getSmb().getCategory());
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowDocuments + 21 + 5).createCell(columNum, CellType.STRING);
                cell.setCellValue(data.getDocuments().getSmb().getIssueDate() != null ? String.valueOf(data.getDocuments().getSmb().getIssueDate()) : "null");
                cell.setCellStyle(styleForData);
            }  else {
                for (int row = rowDocuments + 21 + 3; row <= rowDocuments + 21 + 7; row++) {
                    cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                    cell.setCellValue("");
                    cell.setCellStyle(styleForData);
                }
            }

        } else {
            for (int row = rowDocuments + 2; row <= rowDocuments + 21 + 5; row++) {
                cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(styleForData);
            }
        }
//License
        if (data.getLicenses() != null) {
            List<License> licenses = data.getLicenses();
            int columnLicense = columNum;
            for (License licens : licenses) {
                cell = sheet.getRow(rowLicense - 1).createCell(columnLicense, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(styleForHeader);

                License license = licens;
                cell = sheet.getRow(rowLicense).createCell(columnLicense, CellType.STRING);
                cell.setCellValue(license.getSeries() != null ? String.valueOf(license.getSeries()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowLicense + 1).createCell(columnLicense, CellType.STRING);
                cell.setCellValue(license.getNumber() != null ? String.valueOf(license.getNumber()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowLicense + 2).createCell(columnLicense, CellType.STRING);
                cell.setCellValue(license.getIssueDate() != null ? String.valueOf(license.getIssueDate()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowLicense + 3).createCell(columnLicense, CellType.STRING);
                cell.setCellValue(license.getIssueAuthority() != null ? String.valueOf(license.getIssueAuthority()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowLicense + 4).createCell(columnLicense, CellType.STRING);
                cell.setCellValue(license.getSuspendDate() != null ? String.valueOf(license.getSuspendDate()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowLicense + 5).createCell(columnLicense, CellType.STRING);
                cell.setCellValue(license.getSuspendAuthority() != null ? String.valueOf(license.getSuspendAuthority()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowLicense + 6).createCell(columnLicense, CellType.STRING);
                cell.setCellValue(license.getValidFrom() != null ? String.valueOf(license.getValidFrom()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowLicense + 7).createCell(columnLicense, CellType.STRING);
                cell.setCellValue(license.getValidTo() != null ? String.valueOf(license.getValidTo()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowLicense + 8).createCell(columnLicense, CellType.STRING);
                cell.setCellValue(license.getActivities() != null ? String.valueOf(license.getActivities()) : "null");
                cell.setCellStyle(styleForData);

                cell = sheet.getRow(rowLicense + 9).createCell(columnLicense, CellType.STRING);
                cell.setCellStyle(styleForData);
                if (license.getAddresses() != null) {
                    cell.setCellValue(license.getAddresses().toString());
                } else {
                    cell.setCellValue("");
                }
                columnLicense++;
            }
            if (columNumLast < columnLicense) {
                columNumLast = columnLicense;
            }

        } else {
            for (int row = rowLicense; row <= rowLicense + 9; row++) {
                cell = sheet.getRow(row).createCell(columNum, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(styleForData);
            }
        }

        columNum = columNumLast - 1;
    }

    private HSSFCellStyle createStyleForDataValue(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        //font.setItalic(true);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.valueOf((short) 1));
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.valueOf((short) 1));
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.valueOf((short) 1));
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.valueOf((short) 1));
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }

    private void createCsvFromDadata(String jsonString) {

        JSONObject output;
        try {
            output = new JSONObject(jsonString);
            JSONArray docs = output.getJSONArray("infile");
            File file = new File("/data/fromJSON.csv");
            String csv = CDL.toString(docs);
            FileUtils.writeStringToFile(file, csv);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();

        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setFont(font);

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());


        style.setBorderBottom(BorderStyle.valueOf((short) 1));
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.valueOf((short) 1));
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.valueOf((short) 1));
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.valueOf((short) 1));
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }

    private static HSSFCellStyle createStyleForData(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        //font.setItalic(true);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);


        style.setBorderBottom(BorderStyle.valueOf((short) 1));
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.valueOf((short) 1));
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.valueOf((short) 1));
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.valueOf((short) 1));
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }

    private static HSSFCellStyle createStyleForHeader(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setItalic(true);
        style.setFont(font);

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());


        style.setBorderBottom(BorderStyle.valueOf((short) 1));
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.valueOf((short) 1));
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.valueOf((short) 1));
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.valueOf((short) 13));
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }
}
