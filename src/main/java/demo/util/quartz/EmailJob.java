package demo.util.quartz;

import demo.model.Email;
import demo.util.email.MailTest;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by MoSon on 2017/8/16.
 */
public class EmailJob implements Job{

    private static Logger _log = LoggerFactory.getLogger(EmailJob.class);

    public EmailJob() {

    }

    //执行发送邮件的方法
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        MailTest.send(Email.emailAddress,Email.subject,Email.content);

    }
}
