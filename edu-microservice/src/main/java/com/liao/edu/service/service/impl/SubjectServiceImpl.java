package com.liao.edu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.edu.common.entity.Subject;
import com.liao.edu.common.vo.SubjectVo;
import com.liao.edu.service.mapper.SubjectMapper;
import com.liao.edu.service.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

/**
 * @author liao
 * @since 2019-11-28
 */
@Slf4j
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
                // 判断获取到的二级菜单标题是否合法
                if (secondValue != null && (!"".equals(secondValue))) {
                    // 检测是否存在该条数据
                    Subject secSubject = subjectMapper.selectOne(new QueryWrapper<Subject>().eq("title", secondValue));
                    // 如果不存在就添加
                    if (secSubject == null) {
                        secSubject = new Subject();
                        secSubject.setParentId(subject.getId()).setTitle(secondValue);
                        subjectMapper.insert(secSubject);
                    } else {
                        list.add(String.format("第%d行第2列数据已经存在", i));
                    }
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
    public List<SubjectVo> getAllSubject() {
        // 获取所有的一级标题 存储在set里
        List<Subject> subjectList = subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id", 0));
        // 获取所有的主题
        // 二级标题
        log.debug("获取到的一级标题{}", subjectList);
        List<SubjectVo> subjectVoList = new ArrayList<>();
        for (Subject subject : subjectList) {
            // 创建一个新的subjectVo 存放一级标题
            SubjectVo vo = new SubjectVo();
            String id = subject.getId();
            vo.setId(id);
            vo.setLabel(subject.getTitle());
            // 获取对应一级标题的二级标题列表
            List<Subject> secondList = subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id", id));
            // 创建一个存放二级标题的列表
            ArrayList<SubjectVo> secVoList = new ArrayList<>();
            secondList.forEach(subject1 -> {
                SubjectVo subjectVo = new SubjectVo();
                subjectVo.setId(subject1.getId());
                subjectVo.setLabel(subject1.getTitle());
                // 添加二级标题
                secVoList.add(subjectVo);
            });
            vo.setChildren(secVoList);
            // 教vo添加到列表中
            subjectVoList.add(vo);
        }
        return subjectVoList;
    }

    @Override
    public boolean deleteSubjectById(String id) {
        // 先判断是否是一级标题 如果是一级标题就删除附带的二级标题
        Subject subject = subjectMapper.selectById(id);
        // 判断是否存在改数据
        if (subject == null) {
            return false;
        }

        String zeroId = "0";
        if (zeroId.equals(subject.getParentId())) {
            List<Subject> subjectList = subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id", id));
            ArrayList<String> list = new ArrayList<>();
            subjectList.forEach(subject1 -> {
                list.add(subject1.getId());
            });
            if (list.size() > 0) {
                subjectMapper.deleteBatchIds(list);
            }
        }
        // 删除一级或者二级标题
        int i = subjectMapper.deleteById(id);
        return i != 0;
    }

    @Override
    public boolean addSubject(Subject subject) {
        String title = subject.getTitle();
        Subject one = subjectMapper.selectOne(new QueryWrapper<Subject>().eq("title", title));
        if (one == null) {
            int insert = subjectMapper.insert(subject);
            return insert != 0;
        } else {
            return false;
        }

    }

    @Override
    public List<Subject> findAllParentSubject() {
        return subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id", 0));
    }

    @Override
    public List<Subject> findSecondSubjectById(String id) {
        return subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id", id));
    }

    @Override
    public Subject findFirstSubjectBySecondSubjectId(String id) {
        return subjectMapper.findFirstSubjectBySecondSubjectId(id);
    }




}
