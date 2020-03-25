package com.huiyou.liuzhi.mapper;


import com.huiyou.liuzhi.model.AopLog;

import java.util.List;

/**
 * @author Joe
 * @version 1.0
 * @date 2020/1/18 14:25
 */
public interface AopLogMapper {

    Integer addLog(AopLog aopLog);
    List<AopLog> findAll();
}
