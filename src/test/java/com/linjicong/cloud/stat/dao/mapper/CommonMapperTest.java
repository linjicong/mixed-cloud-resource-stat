package com.linjicong.cloud.stat.dao.mapper;

import cn.hutool.core.date.DateUtil;
import com.linjicong.cloud.stat.dao.entity.acloud.ACloudDnsDomain;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudEcs;
import com.linjicong.cloud.stat.dao.mapper.acloud.ACloudDnsDomainMapper;
import com.linjicong.cloud.stat.dao.mapper.hcloud.HCloudEcsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-10-09-11:18
 */
@SpringBootTest
class CommonMapperTest {

    @Autowired
    private ACloudDnsDomainMapper aCloudDnsDomainMapper;

    @Autowired
    private HCloudEcsMapper hCloudEcsMapper;

    @Test
    void deleteByStatDateAndConfName() {
    }

    @Test
    void selectByStatDateA() {
        List<ACloudDnsDomain> aCloudDnsDomains = aCloudDnsDomainMapper.selectByStatDate(DateUtil.today());
        System.out.println(aCloudDnsDomains);
    }

    @Test
    void selectByStatDateH() {
        List<HCloudEcs> hCloudEcs = hCloudEcsMapper.selectByStatDate(DateUtil.today());
        System.out.println(hCloudEcs);
    }


    @Test
    void selectByConfName() {
    }
}
