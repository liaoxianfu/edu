package com.liao.edu.service;

import com.liao.edu.service.entity.Course;
import com.liao.edu.service.entity.vo.CoursePublishVo;
import com.liao.edu.service.mapper.CourseMapper;
import com.liao.edu.service.mapper.TeacherMapper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liao
 * create at 2019/12/5 10:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelTest {

    // 测试Excel的写入
    @Test
    public void test01() throws IOException {
        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = workbook.createSheet("demo");
        // 创建行
        HSSFRow row = sheet.createRow(0);
        // 创建单元格
        HSSFCell cell = row.createCell(0);
        // 写入数据
//        cell.setCellValue(LocalDateTime.now());
        FileOutputStream fileOutputStream = new FileOutputStream("e:/demo.xls");
        workbook.write(fileOutputStream);
//        workbook.close();
        System.out.println("success");
    }

    // 测试文件读取
    @Test
    public void test02() throws IOException {
        FileInputStream inputStream = new FileInputStream("e:/demo.xls");
        // 创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        // 获取第一张工作表
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 获取行数
        int rowNum = sheet.getLastRowNum();
        System.out.println(rowNum);
        for (int i = 0; i <= rowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            // 获取列数
            short cellNum = row.getLastCellNum();
            for (short j = 0; j < cellNum; j++) {
                // 获取单元格
                HSSFCell cell = row.getCell(j);
                // 输出文件内容
                System.out.print(cell.getStringCellValue() + " ");
            }
        }
        // 关闭文件流
//        workbook.close();
        inputStream.close();
    }

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TeacherMapper mapper;
    @Test
    public void testMybatis(){
//        List<String> list = mapper.findTeacherIdLikeName("刘");
//        System.out.println(list);
        CoursePublishVo coursePublishVo = courseMapper.selectCoursePublishVoByCourseId("18");
        System.out.println(coursePublishVo);
    }


}
