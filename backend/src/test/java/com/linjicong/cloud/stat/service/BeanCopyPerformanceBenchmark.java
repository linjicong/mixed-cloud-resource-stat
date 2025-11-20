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
 * 测试不同Bean复制工具的性能基准
 * 结论:
 * set > cglib > asm > Apache BeanUtils > hutool BeanUtil
 *
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

    /**
     * 初始化测试数据模型
     */
    private ServerDetail model = new ServerDetail().withAccessIPv4("now");

    /**
     * 使用直接设置属性的方式进行Bean复制
     */
    @Benchmark
    public HCloudEcs setter() {
        HCloudEcs vo = new HCloudEcs();
        vo.setAccessIPv4("now");
        return vo;
    }

    /**
     * 使用Spring提供的BeanUtils工具类进行Bean复制
     */
    @Benchmark
    public HCloudEcs springBeanUtils() throws InvocationTargetException, IllegalAccessException {
        HCloudEcs vo = new HCloudEcs();
        BeanUtils.copyProperties(this.model, vo);
        return vo;
    }

    /**
     * 使用CGLIB库中的BeanCopier进行Bean复制
     */
    @Benchmark
    public HCloudEcs cglibBeanCopier() {
        BeanCopier beanCopier = BeanCopier.create(ServerDetail.class, HCloudEcs.class, false);
        HCloudEcs vo = new HCloudEcs();
        beanCopier.copy(this.model, vo, null);
        return vo;
    }

    /**
     * 使用自定义的ASM工具类进行Bean复制
     */
    @Benchmark
    public HCloudEcs asm() {
        HCloudEcs vo = new HCloudEcs();
        ReflectAsmUtil.copyProperties(this.model, vo);
        return vo;
    }

    /**
     * 使用Hutool库中的BeanUtil工具类进行Bean复制
     */
    @Benchmark
    public HCloudEcs hutool() {
        HCloudEcs vo = new HCloudEcs();
        BeanUtil.copyProperties(this.model, vo);
        return vo;
    }

    /**
     * 使用自定义的cgLibCopyBean方法进行Bean复制
     */
    @Benchmark
    public HCloudEcs self() {
        HCloudEcs vo = new HCloudEcs();
        com.linjicong.cloud.stat.util.BeanUtils.cgLibCopyBean(this.model, HCloudEcs::new);
        return vo;
    }

    /**
     * 主函数，用于启动性能测试
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BeanCopyPerformanceBenchmark.class.getName() + ".*")
                .measurementIterations(5)
                .forks(1)
                .build();
        new Runner(options).run();
    }
}
