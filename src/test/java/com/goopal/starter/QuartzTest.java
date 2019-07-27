package com.goopal.starter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;

import java.util.Date;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

@Slf4j
public class QuartzTest {
    @Test
    public void testScheduler() throws SchedulerException, InterruptedException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(6)
                        .repeatForever())
                .build();
        scheduler.getListenerManager()
                .addJobListener(new HelloJobListener("我的HelloJob监听器"),
                        KeyMatcher.keyEquals(JobKey.jobKey("job1", "group1")));
        log.info("执行触发器规则开始，每10秒执行一次");
        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
        Thread.sleep(30000);
        log.info("执行新的触发器规则开始，每5秒执行一次，共执行3次......");

        // update trigger with new Trigger
        TriggerBuilder oldTriggerBuilder = trigger.getTriggerBuilder();
        Trigger newTrigger = oldTriggerBuilder

                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(5)
                        .withRepeatCount(2))
//                .startAt(new Date(System.currentTimeMillis() + 1000))
                .build();
        scheduler.rescheduleJob(TriggerKey.triggerKey("trigger1", "group1"), newTrigger);
        Thread.sleep(30000);
        scheduler.shutdown();
    }
}
