package demo.controller;

import demo.model.User;
import demo.util.MD5Utils;
import demo.util.quartz.SimpleExample;
import leap.orm.query.CriteriaQuery;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liangwenhui on 2017/7/28.
 */
public class UserModelController {
    public List<User> query(String name, Integer age, String loginId){
        if(name == null && age == null && loginId == null){
            return User.all();
        }
        if(name == null){
            name = "";
        }
        if(loginId == null){
            loginId = "";
        }
        CriteriaQuery<User> cq = User.<User>query();
        cq.where("name like ? and age like ? and loginId like ?",
                "%"+name+"%",age==null?"%%":"%"+age+"%","%"+loginId+"%");
        return cq.list();
    }


    /**
     * 登录
     * @param request
     * @param response
     * @param userName  用户名
     * @param password  密码
     * @return
     */
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response,
                                    String userName, String password){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
                CriteriaQuery<User> cq = User.<User>query();
                cq.where("user_name = ?", userName);
                List<User> list = cq.list();
                if (list == null) {
                    result.put("success", false);
                    result.put("msg", "用户名不存在");
                } else {
                    User user = list.get(0);
                    String pw = user.getPassword();
                    //md5加密
                    String md5Pw = MD5Utils.string2MD5(password);
                    if (pw.equals(md5Pw)) {
                        request.getSession().setAttribute("user",user);
                        result.put("success", true);
                        result.put("msg", "登录成功");
                    }
                }
            }
        }catch (Exception e){
            result.put("success",false);
            result.put("msg","登录失败");
        }
        return result;
    }


    /**
     * 查询当前登录的用户
     * @param request
     * @param response
     * @return
     */
    public User queryUser(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        return user;
    }

    /**
     * 查询讲师姓名
     * @return
     */
    public List<User> queryLecturer(){
        CriteriaQuery<User> cq = User.<User>query();
        cq.where("role = ?",2);
        List<User> list = cq.list();
        return list;
    }



}
