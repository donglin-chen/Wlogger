package com.wottui.wlogger.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/30
 * @Time: 19:12
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    /**
     * Set the ThreadPoolExecutor's core pool size.
     */
    private int corePoolSize = 32;
    /**
     * Set the ThreadPoolExecutor's maximum pool size.
     */
    private int maxPoolSize = 200;
    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private int queueCapacity = 100000;

    @Bean
    public Executor mySimpleAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("MySimpleExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor myAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
