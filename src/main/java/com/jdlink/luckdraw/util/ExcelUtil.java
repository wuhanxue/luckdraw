package com.jdlink.luckdraw.util;

import com.jdlink.luckdraw.pojo.Seat;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    // 人员模板文件
    private static final String TEMPLATE_FILE_NAME = "C:\\Users\\Matt\\Desktop\\抽奖\\1.xlsx";
    // 兑奖券模板文件
    private static final String IN_FILE_NAME = "C:\\Users\\Matt\\Desktop\\抽奖\\兑奖券2019.xlsx";
    // 兑奖券导出文件
    private static final String OUT_FILE_NAME = "C:\\Users\\Matt\\Desktop\\抽奖\\兑奖券2019_Out.xlsx";

    private List<Object[][]> getSeatData() {
        // 定义一维数组，存放Excel表里的每一行的各个列的数据
        Object[] obj;
        Object[][] param = null;
        List<Object[][]> list = new ArrayList<>();//存储每一个页码中的内容
        InputStream is = null;
        try {
            //定义文本输入流
            is = new FileInputStream(TEMPLATE_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Excel2003
        if (TEMPLATE_FILE_NAME.endsWith("xls")) {
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
        else if (TEMPLATE_FILE_NAME.endsWith("xlsx")) {
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

    private void writeExcelPOI(Map<Integer, List<Seat>> tableSeats) {
        try {
            FileInputStream excelFileInputStream = new FileInputStream(IN_FILE_NAME);
            XSSFWorkbook xwb = new XSSFWorkbook(excelFileInputStream);
            excelFileInputStream.close();

            // 迭代map赋值, 获取迭代器
            Iterator<Map.Entry<Integer, List<Seat>>> entries2 = tableSeats.entrySet().iterator();
            while (entries2.hasNext()) {
                Map.Entry<Integer, List<Seat>> entry2 = entries2.next();
                XSSFSheet xSheet = xwb.getSheetAt(entry2.getKey());
                try {
                    xwb.setSheetName(entry2.getKey()-1, "桌" + entry2.getKey().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    xwb.setSheetName(entry2.getKey()-1, "桌" + entry2.getKey().toString() + "_" + RandomUtils.nextInt());
                }
                List<Seat> seatList = entry2.getValue();
                Map<String, Object> hashMap = new HashMap<>();
                // 第一行
                hashMap.put("A3", "桌号：" + seatList.get(0).getTableId());
                hashMap.put("A4", "座位号：" + seatList.get(0).getLocationId());
                hashMap.put("A5", "部门：" + (seatList.get(0).getDepartment().equals("null") ? "" : seatList.get(0).getDepartment()));
                hashMap.put("B6", (seatList.get(0).getName().equals("null") ? "" : seatList.get(0).getName()));
                hashMap.put("H3", "桌号：" + seatList.get(1).getTableId());
                hashMap.put("H4", "座位号：" + seatList.get(1).getLocationId());
                hashMap.put("H5", "部门：" + (seatList.get(1).getDepartment().equals("null") ? "" : seatList.get(1).getDepartment()));
                hashMap.put("I6", (seatList.get(1).getName().equals("null") ? "" : seatList.get(1).getName()));
                // 第二行
                hashMap.put("A12", "桌号：" + seatList.get(2).getTableId());
                hashMap.put("A13", "座位号：" + seatList.get(2).getLocationId());
                hashMap.put("A14", "部门：" + (seatList.get(2).getDepartment().equals("null") ? "" : seatList.get(2).getDepartment()));
                hashMap.put("B15", (seatList.get(2).getName().equals("null") ? "" : seatList.get(2).getName()));
                hashMap.put("H12", "桌号：" + seatList.get(3).getTableId());
                hashMap.put("H13", "座位号：" + seatList.get(3).getLocationId());
                hashMap.put("H14", "部门：" + (seatList.get(3).getDepartment().equals("null") ? "" : seatList.get(3).getDepartment()));
                hashMap.put("I15", (seatList.get(3).getName().equals("null") ? "" : seatList.get(3).getName()));
                // 第三行
                hashMap.put("A21", "桌号：" + seatList.get(4).getTableId());
                hashMap.put("A22", "座位号：" + seatList.get(4).getLocationId());
                hashMap.put("A23", "部门：" + (seatList.get(4).getDepartment().equals("null") ? "" : seatList.get(4).getDepartment()));
                hashMap.put("B24", (seatList.get(4).getName().equals("null") ? "" : seatList.get(4).getName()));
                hashMap.put("H21", "桌号：" + seatList.get(5).getTableId());
                hashMap.put("H22", "座位号：" + seatList.get(5).getLocationId());
                hashMap.put("H23", "部门：" + (seatList.get(5).getDepartment().equals("null") ? "" : seatList.get(5).getDepartment()));
                hashMap.put("I24", (seatList.get(5).getName().equals("null") ? "" : seatList.get(5).getName()));
                // 第四行
                hashMap.put("A30", "桌号：" + seatList.get(6).getTableId());
                hashMap.put("A31", "座位号：" + seatList.get(6).getLocationId());
                hashMap.put("A32", "部门：" + (seatList.get(6).getDepartment().equals("null") ? "" : seatList.get(6).getDepartment()));
                hashMap.put("B33", (seatList.get(6).getName().equals("null") ? "" : seatList.get(6).getName()));
                hashMap.put("H30", "桌号：" + seatList.get(7).getTableId());
                hashMap.put("H31", "座位号：" + seatList.get(7).getLocationId());
                hashMap.put("H32", "部门：" + (seatList.get(7).getDepartment().equals("null") ? "" : seatList.get(7).getDepartment()));
                hashMap.put("I33", (seatList.get(7).getName().equals("null") ? "" : seatList.get(7).getName()));
                // 第五行
                hashMap.put("A39", "桌号：" + seatList.get(8).getTableId());
                hashMap.put("A40", "座位号：" + seatList.get(8).getLocationId());
                hashMap.put("A41", "部门：" + (seatList.get(8).getDepartment().equals("null") ? "" : seatList.get(8).getDepartment()));
                hashMap.put("B42", (seatList.get(8).getName().equals("null") ? "" : seatList.get(8).getName()));
                hashMap.put("H39", "桌号：" + seatList.get(9).getTableId());
                hashMap.put("H40", "座位号：" + seatList.get(9).getLocationId());
                hashMap.put("H41", "部门：" + (seatList.get(9).getDepartment().equals("null") ? "" : seatList.get(9).getDepartment()));
                hashMap.put("I42", (seatList.get(9).getName().equals("null") ? "" : seatList.get(9).getName()));
                // 迭代map赋值, 获取迭代器
                Iterator<Map.Entry<String, Object>> entries = hashMap.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, Object> entry = entries.next();
                    // 定义单元格的位置
                    CellAddress cellAddress = new CellAddress(entry.getKey());
                    // 获取行
                    XSSFRow row = xSheet.getRow(cellAddress.getRow());
                    // 获取单元格
                    XSSFCell cell = row.getCell(cellAddress.getColumn());
                    if (entry.getValue() != null) cell.setCellValue(entry.getValue().toString());
                    else cell.setCellValue("");
                }
            }
            // 写入文件
            FileOutputStream excelFileOutPutStream = new FileOutputStream(OUT_FILE_NAME);//写数据到这个路径上		workbook.write(excelFileOutPutStream);		excelFileOutPutStream.flush();		excelFileOutPutStream.close();
            xwb.write(excelFileOutPutStream);
            excelFileOutPutStream.flush();
            excelFileOutPutStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ExcelUtil excelUtil = new ExcelUtil();
        // 获取人员名单
        List<Seat> seatList = new ArrayList<>();
        Map<Integer, List<Seat>> tableSeats = new HashMap<>();
        List<Object[][]> seatData = excelUtil.getSeatData();
        for (Object[][] seatDatum : seatData) {
            for (Object[] objects : seatDatum) {
                if (objects[0].toString().contains("桌号")) continue;
                Seat seat = new Seat();
                if (objects[0] != null) {
                    Integer tableId;
                    if (objects[0].toString().contains(".")) {
                        String[] array = objects[0].toString().split("\\.");
                        tableId = Integer.parseInt(array[0]);
                    }
                    else tableId = Integer.parseInt(objects[0].toString());
                    seat.setTableId(tableId);
                }
                if (objects[1] != null) {
                    Integer locationId;
                    if (objects[1].toString().contains(".")) {
                        String[] array = objects[1].toString().split("\\.");
                        locationId = Integer.parseInt(array[0]);
                    }
                    else locationId = Integer.parseInt(objects[1].toString());
                    seat.setLocationId(locationId);
                }
                seat.setDepartment(objects[2].toString());
                seat.setName(objects[3].toString());
                seatList.add(seat);
            }
        }
        // 按桌分类
        for (Seat seat : seatList) {
            if (!tableSeats.keySet().contains(seat.getTableId())) {
                List<Seat> seatList1 = new ArrayList<>();
                seatList1.add(seat);
                tableSeats.put(seat.getTableId(), seatList1);
            } else {
                tableSeats.get(seat.getTableId()).add(seat);
            }
        }

        excelUtil.writeExcelPOI(tableSeats);



        System.out.println("Process Successfully!");
    }
}
