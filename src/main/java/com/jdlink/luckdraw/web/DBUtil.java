package com.jdlink.luckdraw.web;

import java.lang.String;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.*;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 导出用工具类
 */
public class DBUtil {
    public final static String url = "jdbc:mysql://172.16.1.92:3306/lucky_draw"; // 数据库URL
    public final static String user = "root"; // 数据库用户名
    public final static String password = "123456"; // 数据库密码
    public static Connection con;

    public DBUtil() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            con = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出(带表头字段)
     *
     * @param name
     * @param response
     * @param sqlWords
     * @throws IOException
     */
    public void exportExcel2(String name, HttpServletResponse response, String sqlWords, String tableHead) throws IOException {// Table_name 数据库中想要导出的指定表名
        // 创建Excel表。
        org.apache.poi.ss.usermodel.Workbook book = new HSSFWorkbook();
        try {
            // 在当前Excel创建一个子表
            org.apache.poi.ss.usermodel.Sheet sheet = book.createSheet(name);
            Statement st = (Statement) con.createStatement();
            // 创建sql语句，对team进行查询所有数据
            String sql = sqlWords;
            ResultSet rs = st.executeQuery(sql);
            Row row1 = sheet.createRow(0);
            ResultSetMetaData rsmd = rs.getMetaData();
            int colnum = rsmd.getColumnCount();
            //获取表头数组
            String tHead[] = tableHead.split("/");
            //设置表头信息
            for (int i = 1; i <= colnum; i++) {
                org.apache.poi.ss.usermodel.Cell cell = row1.createCell(i - 1);
                // 写入数据
                cell.setCellValue(tHead[i - 1]);
            }
            // 设置表格信息
            int idx = 1;
            // 当下一行非空时执行操作
            while (rs.next()) {
                // 行
                Row row = sheet.createRow(idx++);
                for (int i = 1; i <= colnum; i++) {
                    String str = rs.getString(i);
                    // 单元格
                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(i - 1);
                    // 写入数据
                    cell.setCellValue(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }//.......................

        // 保存
        ByteArrayOutputStream fos = null;
        try {
            fos = new ByteArrayOutputStream();
            book.write(fos);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream os = response.getOutputStream();
        try {
            //为了让各种浏览器可以识别,需要将中文转换成Byte形式,然后通过ISO-8859-1进行编码
            // OutputStream os = response.getOutputStream();
            String file = name;//初始文件名
            name = new String(file.getBytes("gb2312"), "ISO8859-1" );
            response.reset();
            //设置content-disposition响应头控制浏览器以下载的形式打开文件
            //报头用于提供一个推荐的文件名，并强制浏览器显示保存对话框
            //attachment表示以附件方式下载。如果要在页面中打开，则改为 inline
            response.setHeader("Content-Disposition", "attachment; filename=" + name + ".xls");//要保存的文件名
            response.setContentType("application/octet-stream; charset=utf-8");
            book.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 保存  以固定名保存到固定路径
        //book.write(new FileOutputStream("D://" + name + ".xls"));
    }
}