package cn.lakers.chatbot.api.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池的配置
 * EnableAsync 启动异步线程，
 * 使自定义的 AsyncTaskExecutor 线程池
 * 关联至异步注解 @Async
 * 和 AsyncTaskExecutor Bean 上面
 *
 * @author lakers
 */
@Configuration(proxyBeanMethods = false)
@EnableAsync
public class AsyncTaskConfig {

    @Bean("taskExecutor")
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        // 此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        // 设置核心线程数
        asyncTaskExecutor.setCorePoolSize(core);
        // 设置最大线程数
        asyncTaskExecutor.setMaxPoolSize(core * 2 + 1);
        // 除核心线程外的线程存活时间
        asyncTaskExecutor.setKeepAliveSeconds(300);
        // 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        asyncTaskExecutor.setQueueCapacity(40);
        asyncTaskExecutor.setThreadNamePrefix("async-task-");
        // 设置拒绝策略
        asyncTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 优雅停机，等待所有任务结束后再关闭线程池
        asyncTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // （默认为0，此时立即停止），并没等待xx秒后强制停止
        asyncTaskExecutor.setAwaitTerminationSeconds(50);
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

}
