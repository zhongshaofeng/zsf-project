package com.zsf.dao;

import com.zsf.entity.TestTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author zhongshao
 * @Date 2020/3/30 9:13
 * @Description
 */
@Mapper
public interface QueryDao {

    List<HashMap<String, Object>> queryForList();

    TestTable queryForList1();

}
