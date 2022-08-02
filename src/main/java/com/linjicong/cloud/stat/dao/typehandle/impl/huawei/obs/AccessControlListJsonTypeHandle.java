package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.obs;

import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;
import com.obs.services.model.AccessControlList;
import com.obs.services.model.Owner;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class AccessControlListJsonTypeHandle extends JsonTypeHandler<AccessControlList> {
    public AccessControlListJsonTypeHandle() {
        super(AccessControlList.class);
    }
}
