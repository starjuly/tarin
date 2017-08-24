package demo.controller;

import demo.model.User;
import demo.service.CourseService;
import demo.service.impl.courseServiceImpl;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 课程
 * Created by liangwenhui on 2017/7/31.
 */
public class CourseController {
    courseServiceImpl courseService = new CourseService();

    //查询角色
    public String queryRole(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        return user.getRole().toString();
    }

    //创建课程
    public String createCourse(String courseJson) throws Exception {
        return courseService.createCourse(courseJson).toString();
    }

    //查询教师 coursedetail
    public User queryTeacher(String name) {
        User us = courseService.queryTeacher(name);
        return us;
    }


    //查询课程
    public Map<String, String> queryCourse(HttpServletRequest req) {
        String courseName = req.getSession().getAttribute("courseName").toString();
        return courseService.queryCourse(courseName);
    }

    //查询所有课程
    public List<Map<String, String>> queryAllCourse(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        return courseService.queryAllCourse(user.getUserId());

    }

    //课程报名
    public Map<String, Boolean> courseEnroll(String courseName, HttpServletRequest req) {
        Map<String, Boolean> ma = new HashMap<String, Boolean>();
        User user = (User) req.getSession().getAttribute("user");
        ma.put("status", courseService.courseEnroll(courseName, user.getUserId()));
        return ma;
    }


    //查询报名了的课程
    public List<Map<String,String>> querySignCourse(HttpServletRequest req){
        User user=(User)req.getSession().getAttribute("user");
        Integer userId=user.getUserId();
//        return courseService.queryEnrollCourse(userId);
        return null;
    }

    public void onJump(String courseName, HttpServletRequest req) {
        req.getSession().setAttribute("courseName", courseName);

    }

    //取消报名
    public void unSign(String courseName, HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        Integer userId = user.getUserId();
        courseService.courseUnEntityBlank(courseName, userId);
    }

    //更新
    public Boolean updateCourse(String courseJson){
        Boolean status= courseService.updateCourse(courseJson);
        return status;
    }


}