package demo.service.impl;

import demo.model.EntryBlank;
import demo.service.EntryBlankService;
import leap.orm.query.CriteriaQuery;

import java.util.List;

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
}
