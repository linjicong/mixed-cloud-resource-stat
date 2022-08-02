package com.linjicong.cloud.stat.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.huaweicloud.sdk.ecs.v2.model.ServerDetail;
import com.linjicong.cloud.stat.client.HCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.*;
import com.linjicong.cloud.stat.dao.mapper.*;
import com.obs.services.model.BucketTypeEnum;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class HCloudServiceTest {

    @Resource
    private HCloudConfMapper hCloudConfMapper;
    @Resource
    private HCloudEcsMapper hCloudEcsMapper;
    @Resource
    private HCloudRdsMapper hCloudRdsMapper;
    @Resource
    private HCloudDcsMapper hCloudDcsMapper;
    @Resource
    private HCloudDdsMapper hCloudDdsMapper;
    @Resource
    private HCloudObsMapper hCloudObsMapper;
    @Resource
    private HCloudSfsMapper hCloudSfsMapper;

    HCloudClient hCloudClient;

    @BeforeEach
    public void init(){
        CloudConf cloudConf = hCloudConfMapper.selectByPrimaryKey(1);
        hCloudClient = new HCloudClient(cloudConf);
    }

    @Test
    void syncEcs() {
        List<HCloudEcs> hCloudEcs = hCloudClient.listEcs();
        hCloudEcsMapper.insertList(hCloudEcs);
    }

    @Test
    void syncRds() {
        List<HCloudRds> hCloudRds = hCloudClient.listRds();
        hCloudRdsMapper.insertList(hCloudRds);
    }

    @Test
    void syncDcs() {
        List<HCloudDcs> hCloudDcs = hCloudClient.listDcs();
        hCloudDcsMapper.insertList(hCloudDcs);
    }

    @Test
    void syncDds() {
        List<HCloudDds> hCloudDds = hCloudClient.listDds();
        hCloudDdsMapper.insertList(hCloudDds);
    }

    @Test
    void syncObs() {
        List<HCloudObs> hCloudObs = hCloudClient.listObs();
        hCloudObsMapper.insertList(hCloudObs);
    }

    @Test
    void syncSfs() {
        List<HCloudSfs> hCloudSfs = hCloudClient.listSfs();
        hCloudSfsMapper.insertList(hCloudSfs);
    }

    @Test
    void selectRds() {
        HCloudEcs hCloudEcs = hCloudEcsMapper.selectByPrimaryKey(1);
        ServerDetail serverDetail = new ServerDetail();
        BeanUtil.copyProperties(hCloudEcs, serverDetail);
        System.out.println(serverDetail);
    }
    @Test
    void insertObs() {
        HCloudObs hCloudObs = new HCloudObs();
        hCloudObs.setBucketTypeEnum(BucketTypeEnum.OBJECT);
        hCloudObsMapper.insert(hCloudObs);
    }
    @Test
    void deleteDcs() {
        hCloudDcsMapper.deleteByStatDate(DateUtil.today());
    }
}
