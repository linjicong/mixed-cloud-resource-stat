package com.linjicong.cloud.stat.dao.mapper;

import com.linjicong.cloud.stat.dao.entity.CloudConf;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Repository
public interface HCloudConfMapper extends MultiMapper<CloudConf> {

}
