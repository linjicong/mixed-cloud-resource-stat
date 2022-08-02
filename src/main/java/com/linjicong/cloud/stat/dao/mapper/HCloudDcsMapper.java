package com.linjicong.cloud.stat.dao.mapper;

import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudDcs;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Repository
public interface HCloudDcsMapper extends CommonMapper<HCloudDcs> {
}
