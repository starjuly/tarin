package demo.controller;

import demo.model.FeedbackObjectives;
import demo.model.SignIn;
import demo.model.TrainingCourse;
import demo.model.User;
import leap.orm.model.Model;
import leap.orm.query.CriteriaQuery;
import net.sf.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;


/**
 * 签到表控制器
 * Created by MoSon on 2017/7/30.
 */
public class SignInModelController {

    /**
     * 保存签到
     * @param courseId
     * @return
     */
    public String save(HttpServletRequest request, HttpServletResponse response, String courseId){
        try {


            //userId从session中获取
            User user = (User)request.getSession().getAttribute("user");
            Integer userId = user.getUserId();
            SignIn signIn = new SignIn();
            signIn.setCourseId(Integer.parseInt(courseId));
            signIn.setUserId(userId);
            signIn.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            signIn.create();
            //获取时分秒时间
            String create = (signIn.getCreatedAt().toString().split(" "))[1];
            String time = create.substring(0, create.length() - 4);
            return time;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询
     * @param courseId 课程id
     * @return
     */
    public List<LinkedHashMap<String,Object>> query(HttpServletRequest request, HttpServletResponse response,String courseId){
        //从session中获取user
    /*    User user = (User)request.getSession().getAttribute("user");
        Integer userId = user.getUserId();*/
//        int userId = 1;
        CriteriaQuery<SignIn> cq = SignIn.<SignIn>query();
//        CriteriaQuery<SignIn> created_at = cq.select("created_at").where("course_id = ? and user_id=?", courseId,userId);
        //根据课程id查询签到的人
        cq.where("course_id = ? ", courseId).orderBy("created_at");
        List<SignIn> list = cq.list();
        List<LinkedHashMap<String,Object>> listMap = new ArrayList<LinkedHashMap<String, Object>>();
        for(SignIn signIn : list){
            LinkedHashMap<String,Object> link = new LinkedHashMap<String,Object>();
            Integer id = signIn.getUserId();
            CriteriaQuery<User> cqUser = User.<User>query();
            cqUser.where("user_id = ?",id);
            List<User> userList = cqUser.list();
            User user1 = userList.get(0);
            //***修改
            String name = user1.getUserName();
            String departmentName = user1.getDepartmentName();
            //获取时分秒时间
            String create = (signIn.getCreatedAt().toString().split(" "))[1];
            String time = create.substring(0, create.length() - 2);
            //保存进map中
            link.put("name",name);
            link.put("departmentName",departmentName);
            link.put("time",time);
            listMap.add(link);
        }
        return listMap;

    }

    /**
     * 根据userId查询用户已学课程
     * @param request
     * @param response
     * @return
     */
    public List<TrainingCourse> queryByUserId(HttpServletRequest request, HttpServletResponse response){
        User user = (User)request.getSession().getAttribute("user");
        Integer userId = user.getUserId();
        CriteriaQuery<SignIn> cq = SignIn.<SignIn>query();
        cq.where("user_id = ?",userId);
        List<SignIn> list = cq.list();
        List<TrainingCourse> result = new ArrayList<TrainingCourse>();
        for (SignIn signIn : list){
            Integer courseId = signIn.getCourseId();
            CriteriaQuery<TrainingCourse> tc = TrainingCourse.<TrainingCourse>query();
            tc.where("course_id = ?",courseId);
            List<TrainingCourse> trainingCourses = tc.list();
            TrainingCourse trainingCourse = trainingCourses.get(0);
            result.add(trainingCourse);
        }
        return result;
    }


    /**
     * 判断是否学习过该课程
     * @param request
     * @param courseId
     * @return
     */
    public boolean judge(HttpServletRequest request,String courseId){
        User user = (User)request.getSession().getAttribute("user");
        Integer userId = user.getUserId();
        CriteriaQuery<SignIn> cq = SignIn.<SignIn>query();
        cq.where("user_id = ? and course_id = ?",userId,courseId);
        List<SignIn> list = cq.list();
        //判断是否学习过该课程，学习过为true，否则为false
        if(list.size() != 0){
            return true;
        }
        return false;
    }


    /**
     * 根据课程id查询反馈人数
     * @param courseId
     * @return
     */
    public Map<String,String> count(String courseId){
        CriteriaQuery<SignIn> cq = SignIn.<SignIn>query();
        cq.where("course_id = ?",courseId);
        List<SignIn> list = cq.list();
        Integer size = list.size();
        Map<String,String> map = new HashMap<String, String>();
        map.put("count",size.toString());
        return map;
    }

    public static void main(String[] args) {
        String string = "{ \"userId\" : 1,\"courseId\" : 1}";
        JSONObject object = JSONObject.fromObject(string);
        Object userId = object.get("userId");
        Object courseId = object.get("courseId");
        System.out.println(userId);
        System.out.println(courseId);

    }
}
