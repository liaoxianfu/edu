package com.liao.edu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.edu.common.entity.Student;
import com.liao.edu.common.entity.query.StudentQuery;
import com.liao.edu.common.entity.vo.StudentVo;
import com.liao.edu.service.mapper.StudentMapper;
import com.liao.edu.service.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liao
 * @since 2020/2/25 12:27
 */
@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Override
    public StudentVo getStudentListByPageQuery(Page<Student> page, Integer current, Integer size, StudentQuery query) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>().orderByDesc(Student.COL_GMT_MODIFIED);
        String studentName = query.getStudentName();
        String studentId = query.getStudentId();
        Date startTime = query.getStartTime();
        Date endTime = query.getEndTime();
        if (!StringUtils.isEmpty(studentName)) {
            queryWrapper = queryWrapper.like(Student.COL_STUDENT_NAME, studentName);
        }
        if (!StringUtils.isEmpty(studentId)) {
            queryWrapper = queryWrapper.eq(Student.COL_STUDENT_ID, studentId);
        }
        if (startTime != null) {
            queryWrapper = queryWrapper.ge(Student.COL_GMT_MODIFIED, startTime);
        }
        if (endTime != null) {
            queryWrapper = queryWrapper.le(Student.COL_GMT_MODIFIED, endTime);
        }

        IPage<Student> studentPage = this.page(page, queryWrapper);
        StudentVo studentVo = new StudentVo();
        studentVo.setTotal(studentPage.getTotal());
        studentVo.setStudentList(studentPage.getRecords());
        return studentVo;
    }

    @Override
    public List<String> batchAddStudent(InputStream inputStream) {
        // 默认头像地址
        String avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
        List<String> list = new ArrayList<>();
        HSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            log.info("文件行数{}", lastRowNum);
            if (lastRowNum < 1) {
                list.add("数据为空");
            }
            for (int i = 1; i <= lastRowNum; i++) {
                HSSFRow row = sheet.getRow(i);
                List<String> info = getInfo(row);
                log.info("info{}", info);
                if (!StringUtils.isEmpty(info.get(0))) {
                    Student student = new Student();
                    student.setStudentId(info.get(0));
                    student.setStudentName(info.get(1));
                    student.setPassword(info.get(2));
                    student.setStudentAvatar(avatar);
                    log.info("student:{}", student);
                    List<String> stringList = addStudent(student);
                    list.addAll(stringList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            list.add("添加文件出错");
        }
        return list;
    }

    private List<String> getInfo(HSSFRow row) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String val = row.getCell(i).getStringCellValue();
            list.add(val);
        }
        return list;
    }

    private List<String> addStudent(Student student) {
        // 查询判断是否存在原有的student
        ArrayList<String> list = new ArrayList<>();
        // 通过学生学号判断是否存在该学生
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>()
                .eq(Student.COL_STUDENT_ID, student.getStudentId());
        List<Student> students = studentMapper.selectList(queryWrapper);
        if (students.size() == 0) {
            this.save(student);
        } else {
            list.add("学号" + student.getStudentId() + "已经存在");
        }
        return list;
    }
}
