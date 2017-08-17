package demo.util.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 定点发送右键
 * Created by MoSon on 2017/8/16.
 */
public class TimingSend {

    public static void send(String time) throws Exception {
        TimingSend ts = new TimingSend();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date parse = sdf.parse("2017-08-16 15:35:00");
        Date parse = sdf.parse(time);
        ts.run(parse);

    }


    public void run(Date runTime) throws Exception {
        Logger log = LoggerFactory.getLogger(TimingSend.class);
        log.info("------- Initializing ----------------------");

        // 定义调度器
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();



        // 定义job
        // 在quartz中，有组的概念，组+job名称 唯一的
        JobDetail job = newJob(EmailJob.class).withIdentity("job1", "group1").build();

        // 定义触发器，在指定时间启动
//        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

        // 将job注册到调度器
        sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + runTime);

        // 启动调度器
        sched.start();


        try {
            long waitingTime = runTime.getTime() - System.currentTimeMillis() + 10000;
            if (waitingTime >0) {
                // 等到相应的时间执行调度器
                Thread.sleep(waitingTime);
            }
            // executing... 执行
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 关闭调度器
        sched.shutdown(true);
        log.info("------- Shutdown Complete -----------------");
    }


}
