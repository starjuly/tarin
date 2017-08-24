package demo.service.impl;

import demo.model.EntryBlank;
import demo.model.TrainingCourse;
import demo.model.User;
import demo.service.EntryBlankService;
import leap.orm.query.CriteriaQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报名表接口实现类
 * Created by MoSon on 2017/8/22.
 */
public class EntryBlankServiceImpl implements EntryBlankService {

    /**
     * 判断学员是否报名，如果报名了返回true，反之返回false
     * @param userId
     * @param courseId
     * @return
     */
    public Boolean judge(Integer userId, String courseId) {
        CriteriaQuery<EntryBlank> cq = EntryBlank.<EntryBlank>query();
        cq.where("courseId = ? and userId = ?",courseId,userId);
        List<EntryBlank> list = cq.list();
        if(list.size() == 0){
            return false;
        }else {
            return true;
        }
    }


    /**
     * 根据用户id查询已报名的课程
     * @param userId
     * @return
     */
    public List<Map<String, String>> queryEnrollCourse(Integer userId) {
        //获取所参加的课程
        CriteriaQuery<EntryBlank> cq = EntryBlank.query();
        cq.where("userId like ? ",
                "%"+userId);
        List<EntryBlank> leb =cq.list();
        //找到了课程ID想获得下面数据
        List<Map<String,String>> lm=new ArrayList<Map<String, String>>();
        List<TrainingCourse> ls=TrainingCourse.findAll();
        //查询所有报名课程
        for (EntryBlank eb:leb
                ) {
            Map<String,String> ma=new HashMap<String, String>();
            TrainingCourse tc=TrainingCourse.find(eb.getCourseId());
            User te=queryTeacherById(tc.getLecturerId());
            ma.put("teacherName",te.getUserName());
            ma.put("courseName",tc.getCourseName());
            ma.put("trainingTime",tc.getTrainingTime().toString());
            lm.add(ma);
        }
        return lm;
    }

    public User queryTeacherById(Integer id) {
        return User.find(id);
    }
}
