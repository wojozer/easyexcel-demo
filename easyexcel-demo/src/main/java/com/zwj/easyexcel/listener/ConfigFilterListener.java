package com.zwj.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;

import com.alibaba.excel.event.AnalysisEventListener;
import com.zwj.easyexcel.dao.ConfigFilterDao;
import com.zwj.easyexcel.data.ConfigFilterImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhengwj
 * @Description:    特殊号码导入监听类
 * @Date: 2020/4/1 16:30
 * @Version: 1.0
 */
// 不能被spring管理
public class ConfigFilterListener  extends AnalysisEventListener<ConfigFilterImport> {

    private static final Logger logger = LoggerFactory.getLogger(ConfigFilterListener.class);

    private static final int BATCH_COUNT = 10000;

    List<ConfigFilterImport> list = new ArrayList<>();

    private ConfigFilterDao configFilterDao;
    public ConfigFilterListener(ConfigFilterDao configFilterDao){
        this.configFilterDao = configFilterDao;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param configFilter
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param analysisContext
     */
    @Override
    public void invoke(ConfigFilterImport configFilter, AnalysisContext analysisContext) {
        list.add(configFilter);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        logger.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        logger.info("{}条数据，开始存储数据库！", list.size());
        configFilterDao.save(list);
        logger.info("存储数据库成功！");
    }
}
