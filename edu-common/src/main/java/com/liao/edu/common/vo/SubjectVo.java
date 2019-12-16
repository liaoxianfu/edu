package com.liao.edu.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liao
 * @since 2019/12/7 18:46
 */
@Data
public class SubjectVo {
    private String id;
    private String label;
    private List<SubjectVo> children;
}
