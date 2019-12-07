package com.liao.edu.service.service;

import com.liao.edu.service.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务类
 * </p>
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
    Map<String, List<String>> getAllSubject();
}
