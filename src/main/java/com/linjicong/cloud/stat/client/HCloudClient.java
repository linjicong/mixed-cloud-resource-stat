package com.linjicong.cloud.stat.client;

import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.dcs.v2.DcsClient;
import com.huaweicloud.sdk.dcs.v2.region.DcsRegion;
import com.huaweicloud.sdk.dds.v3.DdsClient;
import com.huaweicloud.sdk.dds.v3.region.DdsRegion;
import com.huaweicloud.sdk.ecs.v2.EcsClient;
import com.huaweicloud.sdk.ecs.v2.model.ListServersDetailsRequest;
import com.huaweicloud.sdk.ecs.v2.region.EcsRegion;
import com.huaweicloud.sdk.rds.v3.RdsClient;
import com.huaweicloud.sdk.rds.v3.model.ListInstancesRequest;
import com.huaweicloud.sdk.rds.v3.region.RdsRegion;
import com.huaweicloud.sdk.sfsturbo.v1.SFSTurboClient;
import com.huaweicloud.sdk.sfsturbo.v1.model.ListSharesRequest;
import com.huaweicloud.sdk.sfsturbo.v1.region.SFSTurboRegion;
import com.linjicong.cloud.stat.dao.constant.hcloud.ObsEndpoint;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.*;
import com.obs.services.ObsClient;
import com.obs.services.model.ListBucketsRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class HCloudClient{

    private final ICredential auth;

    private final ObsClient obsClient;

    public HCloudClient(CloudConf cloudConf) {
        String accessKey = cloudConf.getAccessKey();
        String secretKey = cloudConf.getSecretKey();
        this.auth = new BasicCredentials()
                .withAk(accessKey)
                .withSk(secretKey);
        this.obsClient = new ObsClient(accessKey, secretKey, ObsEndpoint.CN_SOUTH_1);
    }

    public List<HCloudEcs> listEcs() {
        EcsClient client = EcsClient.newBuilder()
                .withCredential(auth)
                .withRegion(EcsRegion.CN_SOUTH_1)
                .build();

        return client.listServersDetails(new ListServersDetailsRequest().withLimit(1000))
                .getServers().parallelStream()
                .map(HCloudEcs::fromServerDetail)
                .collect(Collectors.toList());
    }

    public List<HCloudRds> listRds() {
        RdsClient client = RdsClient.newBuilder()
                .withCredential(auth)
                .withRegion(RdsRegion.CN_SOUTH_1)
                .build();

        return client.listInstances(new ListInstancesRequest().withLimit(100))
                .getInstances().parallelStream()
                .map(HCloudRds::fromInstanceResponse)
                .collect(Collectors.toList());
    }

    public List<HCloudDcs> listDcs() {
        DcsClient client = DcsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DcsRegion.CN_SOUTH_1)
                .build();

        return client.listInstances(new com.huaweicloud.sdk.dcs.v2.model.ListInstancesRequest().withLimit(1000))
                .getInstances().parallelStream()
                .map(HCloudDcs::fromInstanceListInfo)
                .collect(Collectors.toList());
    }

    public List<HCloudDds> listDds() {
        DdsClient client = DdsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DdsRegion.CN_SOUTH_1)
                .build();

        return client.listInstances(new com.huaweicloud.sdk.dds.v3.model.ListInstancesRequest().withLimit(100))
                .getInstances().parallelStream()
                .map(HCloudDds::fromQueryInstanceResponse)
                .collect(Collectors.toList());
    }

    public List<HCloudObs> listObs() {
        return obsClient.listBucketsV2(new ListBucketsRequest())
                .getBuckets().parallelStream()
                .map(HCloudObs::fromObsBucket)
                .collect(Collectors.toList());
    }

    public List<HCloudSfs> listSfs() {
        SFSTurboClient client = SFSTurboClient.newBuilder()
                .withCredential(auth)
                .withRegion(SFSTurboRegion.CN_SOUTH_1)
                .build();

        return client.listShares(new ListSharesRequest().withLimit(200))
                .getShares().parallelStream()
                .map(HCloudSfs::fromShares)
                .collect(Collectors.toList());
    }
}
