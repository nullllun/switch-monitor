package cn.albumenj.switchmonitor.config;

import cn.albumenj.switchmonitor.schedule.*;
import cn.albumenj.switchmonitor.util.WebSocketUtils;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 配置Quartz定时任务
 *
 * @author Albumen
 */
@Configuration
public class ScheduleConfig {
    /**
     *  配置任务
     * @return
     */
    @Bean(name = "updateJob")
    public MethodInvokingJobDetailFactoryBean updateJob(SwitchesUpdate switchesUpdate) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        jobDetail.setConcurrent(true);
        jobDetail.setName("updateJob");
        jobDetail.setGroup("updateJobGroup");
        jobDetail.setTargetObject(switchesUpdate);
        jobDetail.setTargetMethod("execute");

        return jobDetail;
    }

    @Bean(name = "cleanHistoryJob")
    public MethodInvokingJobDetailFactoryBean cleanHistoryJob(HistoryClean historyClean) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        jobDetail.setConcurrent(true);
        jobDetail.setName("cleanHistoryJob");
        jobDetail.setGroup("cleanHistoryJobGroup");
        jobDetail.setTargetObject(historyClean);
        jobDetail.setTargetMethod("clean");

        return jobDetail;
    }

    @Bean(name = "checkReachableJob")
    public MethodInvokingJobDetailFactoryBean checkReachableJob(SwitchesCheckReach switchesCheckReach) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        jobDetail.setConcurrent(false);
        jobDetail.setName("checkReachableJob");
        jobDetail.setGroup("checkReachableJobGroup");
        jobDetail.setTargetObject(switchesCheckReach);
        jobDetail.setTargetMethod("submit");

        return jobDetail;
    }

    @Bean(name = "briefFetchJob")
    public MethodInvokingJobDetailFactoryBean briefFetchJob(SwitchesBriefFetch switchesBriefFetch) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        jobDetail.setConcurrent(false);
        jobDetail.setName("briefFetchJob");
        jobDetail.setGroup("briefFetchGroup");
        jobDetail.setTargetObject(switchesBriefFetch);
        jobDetail.setTargetMethod("refresh");

        return jobDetail;
    }

    @Bean(name = "wechatReachCheckJob")
    public MethodInvokingJobDetailFactoryBean wechatReachCheckJob(WechatPush wechatPush) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        jobDetail.setConcurrent(false);
        jobDetail.setName("wechatReachCheckJob");
        jobDetail.setGroup("wechatReachCheckGroup");
        jobDetail.setTargetObject(wechatPush);
        jobDetail.setTargetMethod("deviceReachable");

        return jobDetail;
    }

    @Bean(name = "closeWebSocketJob")
    public MethodInvokingJobDetailFactoryBean closeWebSocketJob(WebSocketUtils webSocketUtils) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        jobDetail.setConcurrent(false);
        jobDetail.setName("closeWebSocketJob");
        jobDetail.setGroup("closeWebSocketGroup");
        jobDetail.setTargetObject(webSocketUtils);
        jobDetail.setTargetMethod("close");

        return jobDetail;
    }

    /**
     * 定时触发器
     * @param updateJob 任务
     * @return
     */
    @Bean(name = "updateJobTrigger")
    public CronTriggerFactoryBean updateJobTrigger(JobDetail updateJob){

        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(updateJob);

        //cron表达式，每1分钟执行一次
        tigger.setCronExpression("0 0/1 * * * ?");
        tigger.setName("updateTrigger");
        return tigger;
    }

    @Bean(name = "checkReachableJobTrigger")
    public CronTriggerFactoryBean checkReachableJobTrigger(JobDetail checkReachableJob){

        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(checkReachableJob);

        //cron表达式，每1分钟执行一次
        tigger.setCronExpression("30 0/1 * * * ?");
        tigger.setName("checkReachableTrigger");
        return tigger;
    }

    @Bean(name = "briefFetchJobTrigger")
    public CronTriggerFactoryBean briefFetchJobTrigger(JobDetail briefFetchJob){

        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(briefFetchJob);

        //cron表达式，每1分钟执行一次
        tigger.setCronExpression("45 * * * * ?");
        tigger.setName("briefFetchTrigger");
        return tigger;
    }

    @Bean(name = "cleanHistoryJobTrigger")
    public CronTriggerFactoryBean cleanHistoryJobTrigger(JobDetail cleanHistoryJob){

        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(cleanHistoryJob);

        //cron表达式，每1分钟执行一次
        tigger.setCronExpression("30 * * * * ?");
        tigger.setName("cleanHistoryTrigger");
        return tigger;
    }

    @Bean(name = "closeWebSocketJobTrigger")
    public CronTriggerFactoryBean closeWebSocketJobTrigger(JobDetail closeWebSocketJob) {

        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(closeWebSocketJob);

        //cron表达式，每1分钟执行一次
        tigger.setCronExpression("0/1 * * * * ?");
        tigger.setName("closeWebSocketTrigger");
        return tigger;
    }

    @Bean(name = "wechatReachCheckJobTrigger")
    public CronTriggerFactoryBean wechatReachCheckJobTrigger(JobDetail wechatReachCheckJob) {

        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(wechatReachCheckJob);

        //cron表达式，每1分钟执行一次
        tigger.setCronExpression("55 * * * * ?");
        tigger.setName("wechatReachCheckTrigger");
        return tigger;
    }


    /**
     * 调度工厂
     * @param updateJobTrigger 触发器
     * @return
     */
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger updateJobTrigger,
                                                 Trigger cleanHistoryJobTrigger,
                                                 Trigger checkReachableJobTrigger,
                                                 Trigger wechatReachCheckJobTrigger,
                                                 Trigger closeWebSocketJobTrigger) {

        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();

        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        factoryBean.setOverwriteExistingJobs(true);

        // 延时启动，应用启动1秒后
        factoryBean.setStartupDelay(1);

        // 注册触发器
        factoryBean.setTriggers(updateJobTrigger, cleanHistoryJobTrigger,
                checkReachableJobTrigger, wechatReachCheckJobTrigger, closeWebSocketJobTrigger);
        /*factoryBean.setTriggers(checkReachableJobTrigger, briefFetchJobTrigger);*/
        return factoryBean;
    }
}
