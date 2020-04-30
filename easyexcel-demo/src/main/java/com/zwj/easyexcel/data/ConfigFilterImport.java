package com.zwj.easyexcel.data;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: zhengwj
 * @Description:  特殊号码 导入实体(自定义解析列)
 *
 * @Date: 2020/4/1 11:20
 * @Version: 1.0
 */
@Data
public class ConfigFilterImport {

    /**
     * 特殊号码主键
     */
    @ExcelIgnore
    private String filterPk;
    /**
     * 用户号码
     */
    @ExcelProperty("用户号码")
    private String filterNumber;
    /**
     * 用户姓名
     */
    @ExcelProperty("用户姓名")
    private String filterName;
    /**
     * 归属地
     */
    @ExcelProperty("归属地")
    private String filterLocation;
    /**
     * 号码类型
     */
    @ExcelProperty("号码类型")
    private String filterType;
    /**
     * 申请人
     */
    @ExcelIgnore
    private String filterApplicant;
    /**
     * 申请时间
     */
    @ExcelIgnore
    private Date filterApptime;
    /**
     * 申请原因
     */
    @ExcelIgnore
    private String filterReason;
    /**
     * 审核状态
     */
    @ExcelIgnore
    private String filterStatus;
    /**
     * 是否有效
     */
    @ExcelIgnore
    private String filterIsenabled;

}
