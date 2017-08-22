package demo.controller;

import demo.model.EntryBlank;
import demo.model.User;
import demo.service.EntryBlankService;
import demo.service.impl.EntryBlankServiceImpl;
import leap.orm.query.CriteriaQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 报名
 * Created by MoSon on 2017/8/22.
 */
public class EntryBlankController {

    EntryBlankService entryBlankService = new EntryBlankServiceImpl();

    /**
     * 判断学员是否报名，如果报名了返回true，反之返回false
     * @param request
     * @param courseId
     * @return
     */
    public Map<String,Boolean> judge(HttpServletRequest request,String courseId){
        User user= (User) request.getSession().getAttribute("user");
        Integer userId = user.getUserId();
        Boolean result = entryBlankService.judge(userId,courseId);
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("result",result);
        return map;
    }
}
