package com.zsf.service;

import com.zsf.dao.QueryDao;
import com.zsf.entity.TestTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author zhongshao
 * @Date 2020/3/30 9:43
 * @Description
 */
@Service
public class QueryDaoService {

    @Autowired
    private QueryDao queryDaao;  //注入查询dao


    public List<HashMap<String, Object>> query(){
        System.out.println(queryDaao);
        TestTable list = queryDaao.queryForList1();
        return queryDaao.queryForList();
    }
}
