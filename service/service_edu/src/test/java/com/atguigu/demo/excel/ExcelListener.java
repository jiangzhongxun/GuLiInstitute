package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel 读操作的监听器
 * @author jiang zhongxun
 * @create 2022-11-18 14:32
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //创建list集合封装最终的数据
    List<DemoData> list = new ArrayList<>();

    /**
     * 一行一行的读取 Excel 内容
     */
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("======内容：" + demoData);
        list.add(demoData);
    }

    /**
     * 读取表头内容
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("======表头：" + headMap);
    }

    /**
     * 读取完成之后的操作
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

}
