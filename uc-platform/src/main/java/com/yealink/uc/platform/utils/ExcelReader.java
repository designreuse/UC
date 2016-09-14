package com.yealink.uc.platform.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

public class ExcelReader {
	
    private MultipartFile multipartFile;
    private int sheetNo;
    private Sheet sheet;
    private List<List<String>> listData;
    private List<Map<String,String>> mapData;
    private boolean flag;

    /**
     * @param multipartFile 上传的文件
     * @param sheetNo 工作簿序号，从0开始
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ExcelReader(MultipartFile multipartFile, int sheetNo) throws InvalidFormatException, IOException {
        this.multipartFile = multipartFile;
        this.sheetNo = sheetNo;
        this.flag = false;
        this.load();
    }
    
    public static boolean validateExcel(MultipartFile multipartFile) {
    	if (multipartFile == null || !(isExcel2003(multipartFile) || isExcel2007(multipartFile))) {
            return false;  
        }
        return true;
    }

    /**
     * 加载excel
     * @throws IOException 
     * @throws InvalidFormatException 
     */
    private void load() throws IOException, InvalidFormatException {
        InputStream inStream = null;
        try {
            inStream = multipartFile.getInputStream();
            Workbook workBook = WorkbookFactory.create(inStream);
            sheet = workBook.getSheetAt(this.sheetNo);
            this.getSheetData();
        } catch (IOException e) {
			throw new IOException();
		} catch (InvalidFormatException e) {
			throw new InvalidFormatException(null);
		} finally{
            try {
                if(inStream!=null){
                    inStream.close();
                }
            } catch (IOException e) {
            	throw new IOException();
            }
        }
    }

    /**
     * 获取指定单元格的值
     * @param cell 单元格
     * @return 单元格字符串内容
     */
    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }

    /**
     * 获取sheet页数据
     */
    private void getSheetData() {
        listData = new ArrayList<>();
        mapData = new ArrayList<>();
        List<String> columnHeaderList = new ArrayList<>();
        int numOfRows = sheet.getLastRowNum() + 1;
        for (int i = 0; i < numOfRows; i++) {
            Row row = sheet.getRow(i);
            Map<String, String> map = new HashMap<>();
            List<String> list = new ArrayList<>();
            if (row != null) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (i == 0){
                        columnHeaderList.add(getCellValue(cell));
                    }
                    else{                        
                        map.put(columnHeaderList.get(j), this.getCellValue(cell));
                    }
                    list.add(this.getCellValue(cell));
                }
            }
            if (i > 0){
                mapData.add(map);
            }
            for(String cellStr : list){
            	if (!StringUtil.isStrEmpty(cellStr)){
            		listData.add(list);
            		break;
            	}
            }
        }
        flag = true;
    }
    
    public String getCellData(int row, int col){
        if(row<=0 || col<=0){
            return null;
        }
        if(!flag){
            this.getSheetData();
        }        
        if(listData.size()>=row && listData.get(row-1).size()>=col){
            return listData.get(row-1).get(col-1);
        }else{
            return null;
        }
    }
    
    public Integer getRowCount(){
    	return listData.size();
    }
    
    public String getCellData(int row, String headerName){
        if(row<=0){
            return null;
        }
        if(!flag){
            this.getSheetData();
        }        
        if(mapData.size()>=row && mapData.get(row-1).containsKey(headerName)){
            return mapData.get(row-1).get(headerName);
        }else{
            return null;
        }
    }
    
    public static boolean isExcel2003(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename().endsWith("xls");  
    }  
  
    public static boolean isExcel2007(MultipartFile multipartFile) {
    	return multipartFile.getOriginalFilename().endsWith("xlsx");
    }
}