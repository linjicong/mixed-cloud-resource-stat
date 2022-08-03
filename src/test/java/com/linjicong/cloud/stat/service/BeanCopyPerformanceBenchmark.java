package com.linjicong.cloud.stat.service;

import cn.hutool.core.bean.BeanUtil;
import com.huaweicloud.sdk.ecs.v2.model.ServerDetail;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudEcs;
import com.linjicong.cloud.stat.util.ReflectAsmUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * 结论:
 * set>cglib>asm>Apache BeanUtils>hutool BeanUtil
 * Benchmark                                     Mode  Cnt   Score    Error  Units
 * BeanCopyPerformanceBenchmark.asm              avgt    5   0.001 ±  0.001  ms/op
 * BeanCopyPerformanceBenchmark.cglibBeanCopier  avgt    5  ≈ 10⁻⁴           ms/op
 * BeanCopyPerformanceBenchmark.hutool           avgt    5   0.069 ±  0.015  ms/op
 * BeanCopyPerformanceBenchmark.self             avgt    5  ≈ 10⁻⁴           ms/op
 * BeanCopyPerformanceBenchmark.setter           avgt    5  ≈ 10⁻⁵           ms/op
 * BeanCopyPerformanceBenchmark.springBeanUtils  avgt    5   0.033 ±  0.001  ms/op
 */
@BenchmarkMode(Mode.AverageTime) // 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 输出结果的时间粒度为微秒
@State(Scope.Thread)
public class BeanCopyPerformanceBenchmark {

    private ServerDetail model = new ServerDetail().withAccessIPv4("now");
    //private BeanCopier beanCopier = BeanCopier.create(ServerDetail.class, HCloudEcs.class,false);

    @Benchmark
    public HCloudEcs setter() {
        HCloudEcs vo = new HCloudEcs();
        vo.setAccessIPv4("now");
        return vo;
    }

    @Benchmark
    public HCloudEcs springBeanUtils() throws InvocationTargetException, IllegalAccessException {
        HCloudEcs vo = new HCloudEcs();
        BeanUtils.copyProperties(this.model,vo);
        return vo;
    }

    @Benchmark
    public HCloudEcs cglibBeanCopier() {
        BeanCopier beanCopier = BeanCopier.create(ServerDetail.class, HCloudEcs.class,false);
        HCloudEcs vo = new HCloudEcs();
        beanCopier.copy(this.model,vo,null);
        return vo;
    }

    @Benchmark
    public HCloudEcs asm() {
        HCloudEcs vo = new HCloudEcs();
        ReflectAsmUtil.copyProperties(this.model,vo);
        return vo;
    }

    @Benchmark
    public HCloudEcs hutool() {
        HCloudEcs vo = new HCloudEcs();
        BeanUtil.copyProperties(this.model,vo);
        return vo;
    }

    @Benchmark
    public HCloudEcs self() {
        HCloudEcs vo = new HCloudEcs();
        com.linjicong.cloud.stat.util.BeanUtils.cgLibCopyBean(this.model,HCloudEcs::new);
        return vo;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BeanCopyPerformanceBenchmark.class.getName()+".*").measurementIterations(5).forks(1).build();
        new Runner(options).run();
    }
}
