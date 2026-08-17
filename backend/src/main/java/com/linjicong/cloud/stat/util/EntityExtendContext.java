package com.linjicong.cloud.stat.util;

import com.linjicong.cloud.stat.dao.entity.BasicEntityExtend;
import java.lang.ScopedValue;

// Java 25 Preview: JEP-487 (Scoped Values)
// 使用 ScopedValue 替代 ThreadLocal 实现线程上下文传递。
public final class EntityExtendContext {

    private EntityExtendContext() {}

    private static final ScopedValue<BasicEntityExtend> ENTITY_EXTEND = ScopedValue.newInstance();

    public static void runWith(BasicEntityExtend extend, Runnable action) {
        ScopedValue.where(ENTITY_EXTEND, extend).run(action);
    }

    public static BasicEntityExtend current() {
        return ENTITY_EXTEND.get();
    }

    public static BasicEntityExtend currentOrNull() {
        return ENTITY_EXTEND.orElse(null);
    }
}
