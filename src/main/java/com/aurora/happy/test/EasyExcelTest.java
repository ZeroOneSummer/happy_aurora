package com.aurora.happy.test;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.aurora.happy.pojo.User;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pijiang on 2021/3/25.
 * 阿里easy excel
 */
public class EasyExcelTest {

    public static void main(String[] args) throws Exception{
//        writeExcel();
        writeExcel2();
        System.out.println("导出完成。。。");
    }



    /**
     * 注解表头
     */
    static void writeExcel() throws Exception{
        List<User> list = new ArrayList<User>(){{
            add(new User("李宁", 18));
            add(new User("小花", 20));
        }};

        Sheet sheet = new Sheet(1, 0, User.class);
        sheet.setSheetName("my_sheet");

        OutputStream out = new FileOutputStream("C:\\Users\\v_pijiang\\Desktop\\user.xlsx");
        ExcelWriter writer = EasyExcelFactory.getWriter(out);
        writer.write(list, sheet);
        writer.finish();
        out.close();
    }

    /**
     * 自定义表头
     */
    static void writeExcel2() throws Exception{
        List<List<Object>> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            List<Object> ol = new ArrayList<Object>(){{
                add("填充1-1");
                add("填充1-2");
                add("填充2");
                add("填充3");
                add("填充4");
            }};
            list.add(ol);
        }

        OutputStream out = new FileOutputStream("C:\\Users\\v_pijiang\\Desktop\\用户信息.xlsx");
        ExcelWriter writer = EasyExcelFactory.getWriter(out);

        Sheet sheet = new Sheet(1, 0);
        sheet.setSheetName("组合表头");

        Table table = new Table(1);
        table.setHead(createTestListStringHead());
        table.setTableStyle(createTableStyle());

        writer.write1(list, sheet, table);
        writer.merge(5,6,0,4);  //合并单元格
        writer.finish();
        out.close();
    }


    /**
     * 表头设置
     */
    private static List<List<String>> createTestListStringHead(){
        List<List<String>> head = new ArrayList<List<String>>();

        //每个list为一列，每个元素为一行
        List<String> headCoulumn1 = new ArrayList<String>();
        List<String> headCoulumn2 = new ArrayList<String>();
        List<String> headCoulumn3 = new ArrayList<String>();
        List<String> headCoulumn4 = new ArrayList<String>();
        List<String> headCoulumn5 = new ArrayList<String>();

        //同名行合并
        headCoulumn1.add("1");
        headCoulumn1.add("1-1");
        headCoulumn1.add("1-1");

        //同名行合并，同名列合并
        headCoulumn2.add("1");
        headCoulumn2.add("1-2");
        headCoulumn2.add("1-2");

        headCoulumn3.add("2");
        headCoulumn3.add("2");
        headCoulumn3.add("2");

        headCoulumn4.add("3-1");
        headCoulumn4.add("3-2");
        headCoulumn4.add("3-2");

        headCoulumn5.add("4-1");
        headCoulumn5.add("4-2");
        headCoulumn5.add("4-3");

        head.add(headCoulumn1);
        head.add(headCoulumn2);
        head.add(headCoulumn3);
        head.add(headCoulumn4);
        head.add(headCoulumn5);
        return head;
    }

    private static TableStyle createTableStyle() {
        TableStyle tableStyle = new TableStyle();
        // 设置表头样式
        Font headFont = new Font();
        // 字体是否加粗
        headFont.setBold(true);
        // 字体大小
        headFont.setFontHeightInPoints((short)12);
        // 字体
        headFont.setFontName("楷体");
        tableStyle.setTableHeadFont(headFont);
        // 背景色
        tableStyle.setTableHeadBackGroundColor(IndexedColors.LIGHT_BLUE);

        // 设置表格主体样式
        Font contentFont = new Font();
        contentFont.setBold(true);
        contentFont.setFontHeightInPoints((short)12);
        contentFont.setFontName("黑体");
        tableStyle.setTableContentFont(contentFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.BRIGHT_GREEN);
        return tableStyle;
    }
}