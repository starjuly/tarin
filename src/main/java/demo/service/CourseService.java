package demo.service;

import demo.model.EntryBlank;
import demo.model.SignIn;
import demo.model.TrainingCourse;
import demo.model.User;
import demo.service.impl.courseServiceImpl;
import demo.util.jsonUitl;
import leap.orm.query.CriteriaQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liangwenhui on 2017/8/2.
 */
public class CourseService implements courseServiceImpl {
    public Boolean createCourse(String courseJson) {
        Map<String, Object> courseMap=jsonUitl.jsonToMap(courseJson);
        if (courseMap.get("courseName")==null) return false;
        try{
            TrainingCourse.create(courseMap);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User queryTeacher(String name) {
        CriteriaQuery<User> cq = User.<User>query();
        cq.where("user_name like ? ",
                "%"+name);
        return cq.first();
    }

    public User queryTeacherById(Integer id) {
        return User.find(id);
    }

    public Map<String, String> queryCourse(String courseName) {
        Map<String,String> ma=new HashMap<String, String>();
        CriteriaQuery<TrainingCourse> cq = TrainingCourse.query();
        cq.where("courseName like ?" ,
                "%"+courseName);
        TrainingCourse tr=cq.first();
        User us=queryTeacherById(tr.getLecturerId());
        ma.put("courseName",tr.getCourseName());
        ma.put("teacherName",us.getUserName());
        ma.put("departmentName",us.getDepartmentName());
        ma.put("phone",us.getTel());
        ma.put("email",us.getMail());
        ma.put("courseIntroduce",tr.getCourseIntroduction());
        ma.put("trainingTime",tr.getTrainingTime().toString());
        ma.put("trainingLast",tr.getTrainLast());
        //用于页面获取跳转的重要参数
        ma.put("trainingPlace",tr.getTrainingPlace());
        ma.put("courseId",String.valueOf(tr.getCourseId()));
        return ma;
    }

    //课程报名
    public Boolean courseEnroll(String courseName, Integer userId) {
        CriteriaQuery<TrainingCourse> cq = TrainingCourse.query();
        cq.where("courseName like ?" ,
                "%"+courseName);
//        Integer uid=Integer.valueOf(userId);
        Integer courseId=cq.first().getCourseId();
        EntryBlank eb=new EntryBlank();
        eb.setUserId(userId);
        eb.setCourseId(courseId);
        try {
            eb.create();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //查询所有课程
    public List<Map<String,String>> queryAllCourse(Integer userId) {
        CriteriaQuery<EntryBlank> cq = EntryBlank.query();
        cq.where("userId like ? ",
                "%"+userId);
        List<EntryBlank> leb=cq.list();
        List<Map<String,String>> lm=new ArrayList<Map<String, String>>();
        List<TrainingCourse> ls=TrainingCourse.findAll();
        //查询所有课程并从中找到已经报名项
        for (TrainingCourse tr:ls
                ) {
            Map<String,String> ma=new HashMap<String, String>();
            User te=queryTeacherById(tr.getLecturerId());
            ma.put("teacherName",te.getUserName());
            ma.put("courseName",tr.getCourseName());
            ma.put("trainingTime",tr.getTrainingTime().toString().substring(0,16));
            ma.put("courseId",tr.getCourseId().toString());
            //判断是否已经报名课程
            String eb="报名";
            for(EntryBlank eeb:leb){
                if (eeb.getCourseId().equals(tr.getCourseId())){
                    eb="取消报名";
                    break;
                }
            }
            ma.put("signed",eb);
            lm.add(ma);
        }
        return lm;
    }





    // 取消签到
    public void courseUnEntityBlank(String courseName,Integer userId) {
        CriteriaQuery<TrainingCourse> cq = TrainingCourse.query();
        cq.where("courseName like ?" ,
                "%"+courseName);
        Integer courseId=cq.first().getCourseId();
        EntryBlank.deleteAll("courseId=? and userId=?",courseId,userId);
    }


    //更新方法
    public Boolean updateCourse(String courseJson) {
        Map<String, Object> courseMap=jsonUitl.jsonToMap(courseJson);
        String teacherName=courseMap.get("lecturerId").toString();
        Integer userId=queryTeacher(teacherName).getUserId();
        courseMap.put("lecturerId",userId);
        try {
            TrainingCourse.update(courseMap.get("courseId").toString(),courseMap);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
