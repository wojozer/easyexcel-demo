package com.zwj.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zwj.easyexcel.dao.ConfigFilterDao;
import com.zwj.easyexcel.data.ConfigFilterExport;
import com.zwj.easyexcel.data.ConfigFilterImport;
import com.zwj.easyexcel.listener.ConfigFilterListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据量: 多
 读: 多
 写: 多
 *            注：对于大批量数据前端使用ajax异步请求
 * @author zwj
 * @since 2020-03-31 14:57:45
 */
@RestController
@RequestMapping("/excel")
public class EasyExcelController  {

    private static final Logger logger = LoggerFactory.getLogger(EasyExcelController.class);

    @Resource
    private ConfigFilterDao configFilterDao;
    /**
     * 获取特殊号码导入模板
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getConfigFilterTemplate")
    public void getConfigFilterTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode防止中文乱码
        String fileName = URLEncoder.encode("特殊号码导入模板", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<ConfigFilterImport> list = new ArrayList<>();
        EasyExcel.write(response.getOutputStream(), ConfigFilterImport.class).sheet("模板").doWrite(list);
    }

    /**
     * 特殊号码excel导入
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = { "/uploadConfigFilterExcel" }, method = { RequestMethod.POST })
    public String uploadConfigFilterExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ConfigFilterImport.class, new ConfigFilterListener(configFilterDao)).sheet().doRead();
        return "success";
    }

    /**
     * 特殊号码excel导出
     * @param param
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = { "/downloadConfigFilter" }, method = { RequestMethod.GET })
    public void download(@RequestParam Map<String, Object> param, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        String fileName = "ConfigFilter" + System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), ConfigFilterExport.class).build();
        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet("特殊号码").build();

        int pageNumber = 1;
        int pageSize = 10000;
        int dataLength = pageSize;
        List<ConfigFilterExport> data = null;
        while (dataLength == pageSize){
     //       int startIndex = (pageNumber - 1) * pageSize;
            param.put("pageNo", pageNumber);
            param.put("pageSize", pageSize);
            data=configFilterDao.selectConfigFilterPage(param);   //分页查询
            excelWriter.write(data, writeSheet);
            if(null == data || data.isEmpty()){
                break;
            }
            dataLength = data.size();
            pageNumber++;
            //写数据
            excelWriter.write(data, writeSheet);
        }
        excelWriter.finish();
    }

}
