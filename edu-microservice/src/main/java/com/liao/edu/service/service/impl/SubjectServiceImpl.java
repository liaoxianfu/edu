package com.liao.edu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liao.edu.service.entity.Subject;
import com.liao.edu.service.mapper.SubjectMapper;
import com.liao.edu.service.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liao
 * @since 2019-11-28
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    /**
     * 批量添加Excel文件的内容 这个内容是二级标题
     *
     * @param file 文件
     * @return list
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> batchImportByExcelFile(MultipartFile file) {
        // 读取文件中的内容
        //  定义一个map 存放一级标题和对应的二级标题
        List<String> list = new ArrayList<>();
        HSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                list.add("数据为空");
            }
            for (int i = 1; i <= lastRowNum; i++) {
                // 获取行
                HSSFRow row = sheet.getRow(i);
                HSSFCell cell = row.getCell(0);
                String value = cell.getStringCellValue();
                if (value == null || "".equals(value)) {
                    list.add(String.format("第%d行第1列不合法", i));
                    continue;
                }
                // 判断是否存在数据 不存在的话就添加数据
                Subject subject = subjectMapper.selectOne(new QueryWrapper<Subject>().eq("title", value));
                // 不存在就添加一级标题
                if (subject == null) {
                    // 添加一级菜单
                    subject = new Subject().setTitle(value);
                    subjectMapper.insert(subject);
                }
                // 二级菜单
                HSSFCell secondCell = row.getCell(1);
                String secondValue = secondCell.getStringCellValue();
                // 如果不存在就添加
                if (secondValue != null && (!"".equals(secondValue))) {
                    Subject subject1 = new Subject();
                    subject1.setParentId(subject.getId()).setTitle(secondValue);
                    subjectMapper.insert(subject1);
                } else {
                    list.add(String.format("第%d行第2列不合法", i));
                }
            }
        } catch (IOException e) {
            log.error("创建workbook出错");
            e.printStackTrace();
        } finally {
            if (hssfWorkbook != null) {
                try {
                    hssfWorkbook.close();
                } catch (IOException e) {
                    log.error("workbook关闭出错");
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    @Override
    public Map<String, List<String>> getAllSubject() {
        // 获取所有的一级标题 存储在set里
        List<Subject> subjectList = subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id", 0));
        // 获取所有的主题
        // 二级标题
        Map<String, List<String>> map = new HashMap<>();
        subjectList.forEach(subject -> {
            String pId = subject.getId();
            List<String> list = new ArrayList<>();
            // 获取对应id的子标题
            List<Subject> secSubList = subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id", pId));
            secSubList.forEach(secSub -> {
                list.add(secSub.getTitle());
            });
            // 将父标题以及对应的子标题列表添加到map中去
            map.put(subject.getTitle(), list);
        });
        return map;
    }
}
