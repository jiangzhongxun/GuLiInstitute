package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现 Excel 写和读的操作
 * @author jiang zhongxun
 * @create 2022-11-17 22:50
 */
public class TestEasyExcel {

    /**
     * 实现 Excel 写的操作
     * 方法一：文件流会自动关闭
     */
    //public static void main(String[] args) {
    //    // 1、设置写入文件夹的地址和 Excel文件名称
    //    String fileName = "/Users/jzx/develop/JavaProject/guli_parent/service/service_edu/src/test/java/com/atguigu/demo/excel/write.xlsx";
    //    // 2、调用 easyExcel里面的方法实现写操作
    //    // 参数一：文件路径名称；参数二：实体类class
    //    EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());
    //}

    /**
     * 实现 Excel 写的操作
     * 方法二：需要手动关闭流
     */
    //public static void main(String[] args) {
    //    String fileName = "/Users/jzx/develop/JavaProject/guli_parent/service/service_edu/src/test/java/com/atguigu/demo/excel/write.xlsx";
    //    ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
    //    WriteSheet writeSheet = EasyExcel.writerSheet("学生列表").build();
    //    excelWriter.write(getData(), writeSheet);
    //    // 千万别忘记finish 会帮忙关闭流
    //    excelWriter.finish();
    //}

    /**
     * 创建方法，返回 list 集合
     * @return {@link List}<{@link DemoData}>
     */
    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("学生" + i);
            list.add(data);
        }
        return list;
    }

    /**
     * 实现 Excel 读的操作
     * 方法一：
     */
    public static void main(String[] args) {
        // 1、设置写入文件夹的地址和 Excel文件名称
        String fileName = "/Users/jzx/develop/JavaProject/guli_parent/service/service_edu/src/test/java/com/atguigu/demo/excel/write.xlsx";
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    /**
     * 实现 Excel 读的操作
     * 方法二：
     */
    //public static void main(String[] args) throws Exception {
    //    BufferedInputStream bufferedInputStream =
    //            new BufferedInputStream(
    //                    new FileInputStream("/Users/jzx/develop/JavaProject/guli_parent/service/service_edu/src/test/java/com/atguigu/demo/excel/write.xlsx"));
    //    ExcelReader excelReader = EasyExcel.read(bufferedInputStream, DemoData.class, new ExcelListener()).build();
    //    ReadSheet readSheet = EasyExcel.readSheet(0).build();
    //    excelReader.read(readSheet);
    //    // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
    //    excelReader.finish();
    //}

}
