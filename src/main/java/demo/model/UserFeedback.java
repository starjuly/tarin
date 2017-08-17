package demo.model;

import leap.orm.annotation.Id;
import leap.orm.model.Model;

/**
 * 用户填写课程反馈记录表
 * Created by MoSon on 2017/8/17.
 */
public class UserFeedback extends Model {
    @Id
    private Integer userFeedbackId;
    private Integer userId;
    private Integer courseId;

    public Integer getUserFeedbackId() {
        return userFeedbackId;
    }

    public void setUserFeedbackId(Integer userFeedbackId) {
        this.userFeedbackId = userFeedbackId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
