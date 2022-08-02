package com.linjicong.cloud.stat.task;

import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.mapper.HCloudConfMapper;
import com.linjicong.cloud.stat.dao.mapper.HCloudEcsMapper;
import com.linjicong.cloud.stat.service.CloudFactory;
import com.linjicong.cloud.stat.service.CloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Component
@Slf4j
public class Task {

    @Resource
    private HCloudConfMapper hCloudConfMapper;

    @Scheduled(cron="* * 1 * * ?")
    public void syncEcs(){
        List<CloudConf> cloudConf = hCloudConfMapper.selectAll();
        cloudConf.forEach(conf->{
            CloudService service = CloudFactory.getService(conf);
            int result = service.syncEcs(conf);
            log.info("成功同步ECS: "+result+"");
        });
    }
}
