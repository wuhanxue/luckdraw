package com.jdlink.luckdraw.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CommonUtil {
    private static CommonUtil ourInstance = new CommonUtil();

    public static CommonUtil getInstance() {
        return ourInstance;
    }

    private CommonUtil() {
    }

    /**
     * 获取excel文件的数据
     * @param file excel文件
     * @return 二维数据列表
     */
    public List<Object[][]> getExcelFileData(MultipartFile file) {
        // 定义一维数组，存放Excel表里的每一行的各个列的数据
        Object[] obj;
        Object[][] param = null;
        List<Object[][]> list = new ArrayList<>();//存储每一个页码中的内容
        InputStream is = null;
        String fileName = file.getOriginalFilename();
        try {
            //定义文本输入流
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Excel2003
        if (fileName.endsWith("xls")) {
            try {
                // 打开Workbook
                // rwb = WorkbookFactory.create(is);
                Workbook rwb;
                rwb = Workbook.getWorkbook(is);
                Sheet[] sheets = rwb.getSheets();//获取总页数

                for(int k=0;k<sheets.length;k++){
                    Sheet sht = rwb.getSheet(k);// 得到第一个表d
                    int col = sht.getColumns(); // 获得Excel列
                    int row = sht.getRows();    // 获得Excel行
                    Cell c1;
                    param = new Object[row][col];
                    for (int i = 0; i < row; i++) {
                        obj = new Object[col];
                        for (int j = 0; j < col; j++) {
                            c1 = sht.getCell(j, i);
                            obj[j] = c1.getContents();
                            //System.out.println(obj[j]+"==>");
                            if (obj[j]==""||obj[j]==null)
                                obj[j] = "null";
                            param[i][j] = obj[j];
                        }
                    }
                    list.add(param);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 获取Excel表的Sheet1区域的数据
        // 2007
        else if (fileName.endsWith("xlsx")) {
            try {
                XSSFWorkbook xwb = new XSSFWorkbook(is);
                int sheets = xwb.getNumberOfSheets();//获取总页数
//                System.out.println(sheets+"1233");

                for(int k=0;k<sheets;k++){
                    XSSFSheet xSheet = xwb.getSheetAt(k);
                    // 原来为：int row = xSheet.getLastRowNum();
                    // 修改为 获得总行数
                    int row = xSheet.getPhysicalNumberOfRows();
                    // 获得总列数
                    if (xSheet.getRow(0) == null) break;
                    // int col = xSheet.getRow(1).getPhysicalNumberOfCells();
                    List<Integer> numberList=new ArrayList<>();//存放列数的列表
                    for(int x=0;x<row;x++){
                        if(xSheet.getRow(x)!=null){
                            numberList.add((int) xSheet.getRow(x).getLastCellNum());
                        }

                    }
                    int col = Collections.max(numberList);//获取最大的列数

                    param = new Object[row][col];
                    for (int i = 0; i < row; i++) {
                        XSSFRow row1 = xSheet.getRow(i);
                        if(row1!=null) {
                            obj = new Object[col];
                            for (int j = 0; j < col; j++) {
                                XSSFCell cellStyle = row1.getCell(j);
                                // System.out.println(cellStyle+"====>");
                                //System.out.println(cellStyle+"==>");
                                if (cellStyle != null) {
//                                System.out.println(cellStyle+"++>");

                                    String cat = cellStyle.getCellTypeEnum().toString();
                                    if (cat.equals("NUMERIC")) {
                                        // 不变为数学表达式
                                        DecimalFormat df=new DecimalFormat("0");
                                        obj[j] = df.format(cellStyle.getNumericCellValue());
//                                        obj[j] = cellStyle.getNumericCellValue();
                                        int style = cellStyle.getCellStyle().getDataFormat();
                                        if (HSSFDateUtil.isCellDateFormatted(cellStyle)) {
                                            Date date = cellStyle.getDateCellValue();
                                            switch (style) {
                                                case 178:
                                                    obj[j] = new SimpleDateFormat("yyyy'年'M'月'd'日'").format(date);
                                                    break;
                                                case 14:
                                                    obj[j] = new SimpleDateFormat("yyyy/MM/dd").format(date);
                                                    break;
                                                case 179:
                                                    obj[j] = new SimpleDateFormat("yyyy/MM/dd").format(date);
                                                    break;
                                                case 181:
                                                    obj[j] = new SimpleDateFormat("yyyy/MM/dd").format(date);
                                                    break;
                                                case 22:
                                                    obj[j] = new SimpleDateFormat(" yyyy/MM/dd").format(date);
                                                    break;
                                                default:
                                                    break;
                                            }

                                        }
                                        // 如果是公式类型
                                    } else if (cat.equals("FORMULA")) {
                                        try {
                                            obj[j] = String.valueOf(cellStyle.getNumericCellValue());
                                        } catch (IllegalStateException e) {
                                            obj[j] = String.valueOf(cellStyle.getRichStringCellValue());
                                        }
//                                        obj[j] = cellStyle.getStringCellValue();
                                    } else if (cat.equals("STRING")) {
                                        obj[j] = cellStyle.getStringCellValue();
                                    }
                                } else {
                                    obj[j] = "";
                                }
                                // 如果单元格为空时的操作
                                if (cellStyle==null||cellStyle.equals("")||cellStyle.getCellTypeEnum()==CellType.BLANK)
                                    obj[j] = "null";
                                param[i][j] = obj[j];
                            }
                        }
                    }
                    list.add(param);
                }
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
