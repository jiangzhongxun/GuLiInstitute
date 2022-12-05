package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author jiang zhongxun
 * @create 2022-11-17 22:46
 */
@Data
public class DemoData {

    // @ExcelProperty 用来设置 excel 表头名称 \ 设置列对应的属性
    // ( value属性用来设置写操作的表头；index属性用来设置读操作的列的索引 ）
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;

}
