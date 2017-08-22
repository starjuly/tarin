package demo.controller;

import demo.model.Email;
import demo.model.User;
import demo.service.EmailService;
import demo.service.impl.EmailServiceImpl;
import demo.util.quartz.TimingSend;
import leap.orm.query.CriteriaQuery;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 发送邮件
 * Created by MoSon on 2017/8/16.
 */
public class EmailController {


    /**
     * @param request
     * @param userId  讲师id
     * @param time    时间
     * @param subject 主题
     * @param content 内容
     */
    public void sendEmailByTime(HttpServletRequest request, String userId,
                                String time, String subject, String content) {
        try {
            User admin = (User) request.getSession().getAttribute("user");
            //管理员id
            Integer adminId = admin.getUserId();
            //讲师
            CriteriaQuery<User> cq = User.<User>query();
            cq.where("userId = ?", userId);
            List<User> list = cq.list();
            User user = list.get(0);
            Integer lecturerId = user.getUserId(); //老师Id
            //获取邮箱
            String emailAddress = user.getMail();
//            String emailAddress = "moxj@bingosoft.net";
//            String sub = "培训提醒";
//            String con = "XX您好，请记得备好课，课程将于后天给上午9点上。";
            Email.emailAdd = emailAddress;
            Email.sub = subject;
            Email.cont = content;
            Email.admId = adminId;
            Email.lecId = lecturerId;
            Email.lecName = user.getUserName(); //获取讲师姓名
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date parse = sdf.parse("2017-08-16 15:35:00");
            Date parseTime = sdf.parse(time);

            Email.sendTime = parseTime;

            //发送邮件
            TimingSend.send(parseTime);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据userId查询管理员提醒的信息
     * @param userId 用户id
     * @return
     */
    public List<Email> queryEmailInfoById(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            CriteriaQuery<Email> cq = Email.<Email>query();
            cq.where("userId = ?", userId);
            List<Email> list = cq.list();
            return list;
        }
        return null;
    }

    /**
     * 根据emailId查询提醒记录
     * @param emailId  主键
     * @return
     */
    public Email queryByEmailId(String emailId) {
        if (StringUtils.isNotBlank(emailId)) {
            CriteriaQuery<Email> cq = Email.<Email>query();
            cq.where("emailId = ?", emailId);
            List<Email> list = cq.list();
            Email email = list.get(0);
            return email;
        }
        return null;
    }
}
