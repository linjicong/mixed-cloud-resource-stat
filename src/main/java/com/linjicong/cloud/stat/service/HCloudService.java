package com.linjicong.cloud.stat.service;

import cn.hutool.core.date.DateUtil;
import com.linjicong.cloud.stat.client.HCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudEcs;
import com.linjicong.cloud.stat.dao.mapper.HCloudEcsMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Service
public class HCloudService implements CloudService{

    @Resource
    private HCloudEcsMapper hCloudEcsMapper;

    @Override
    public int syncEcs(CloudConf cloudConf) {
        HCloudClient hCloudClient = new HCloudClient(cloudConf);
        List<HCloudEcs> hCloudEcs = hCloudClient.listEcs();
        hCloudEcsMapper.deleteByStatDate(DateUtil.today());
        return hCloudEcsMapper.insertList(hCloudEcs);
    }
}
