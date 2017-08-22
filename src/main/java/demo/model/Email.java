package demo.model;

import leap.orm.annotation.Id;
import leap.orm.model.Model;

import java.util.Date;

/**
 * Created by MoSon on 2017/8/16.
 */
public class Email extends Model {

    //定义全局静态变量
    public static String emailAdd;  //邮箱地址
    public static String sub;  //邮箱主题
    public static String cont;  //内容
    public static Integer admId;  //管理员id
    public static Integer lecId;  //讲师id
    public static String lecName;  //讲师id
    public static Date sendTime;  //发送时间

    @Id
    private Integer emailId;
    private Integer userId;
    private Integer lecturerId;
    private String emailAddress;
    private String subject;
    private String content;
    private String lecturerName;
    private Date time;
    private Date createdAt;

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
