package demo.controller;

import demo.model.Email;
import demo.model.User;
import demo.util.quartz.TimingSend;
import leap.orm.query.CriteriaQuery;

import java.util.List;


/**
 * 发送邮件
 * Created by MoSon on 2017/8/16.
 */
public class EmailController {

    public void sendEmailByTime(String userId, String time, String subject,String content)  {
        try {
            CriteriaQuery<User> cq = User.<User>query();
            cq.where("userId = ?",userId);
            List<User> list = cq.list();
            User user = list.get(0);
            String emailAddress = user.getMail();
//            String emailAddress = "moxj@bingosoft.net";
            String sub = "培训提醒";
            String con = "XX您好，请记得备好课，课程将于后天给上午9点上。";
            Email.emailAddress = emailAddress;
            Email.subject = subject;
            Email.content = content;
//            time = "2017-08-16 20:43:50";
            TimingSend.send(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
