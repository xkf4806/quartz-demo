package com.goopal.starter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Slf4j
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
      log.info("Hello, current time is: {}", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
