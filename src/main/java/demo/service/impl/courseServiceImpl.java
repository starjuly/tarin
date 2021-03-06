package demo.service.impl;

import demo.model.TrainingCourse;
import demo.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by liangwenhui on 2017/8/2.
 */
public interface courseServiceImpl {
    Boolean createCourse(String courseJson);
    User queryTeacher(String name);
    User queryTeacherById(Integer id);
    Map<String,String> queryCourse(String courseName);
    Boolean courseEnroll(String courseName,Integer userId);
    List<Map<String,String>> queryAllCourse(Integer  userId);
    void courseUnEntityBlank(String courseName,Integer userId);
    Boolean updateCourse(String courseJson);
}
