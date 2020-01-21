package com.liao.edu.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.edu.common.entity.Subject;
import com.liao.edu.common.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程科目的service层
 *
 * @author liao
 * @since 2019-11-28
 */
public interface SubjectService extends IService<Subject> {
    /**
     * 通过Excel文件批量添加数据
     *
     * @param file 文件
     * @return list
     */
    List<String> batchImportByExcelFile(MultipartFile file);


    /**
     * 获取所有的数据信息
     *
     * @return map
     */
    List<SubjectVo> getAllSubject();

    /**
     * 通过id删除数据
     *
     * @return bool
     */
    boolean deleteSubjectById(String id);

    /**
     * 添加标题
     *
     * @param subject 标题
     * @return 是否添加成功
     */
    boolean addSubject(Subject subject);

    /**
     * 查询所有的一级标题
     *
     * @return list
     */
    List<Subject> findAllParentSubject();

    /**
     * 查询对应一级标题的二级标题
     *
     * @param id 一级标题id
     * @return list
     */
    List<Subject> findSecondSubjectById(String id);

    /**
     * 通过二级标题查询一级标题
     *
     * @param id 二级标题id
     * @return subject对象
     */
    Subject findFirstSubjectBySecondSubjectId(String id);

}
