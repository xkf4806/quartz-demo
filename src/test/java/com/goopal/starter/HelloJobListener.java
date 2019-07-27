package com.goopal.starter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class HelloJobListener extends JobListenerSupport {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.warn("任务【{}】执行完成，下次触发时间为：【{}】", context.getJobDetail(), DateFormatUtils.format(context.getNextFireTime(), "yyyy-MM-dd HH:mm:ss"));
    }
}
