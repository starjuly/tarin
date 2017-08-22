package demo.service;

/**
 * 报名表接口
 * Created by MoSon on 2017/8/22.
 */
public interface EntryBlankService {

    /**
     * 判断学员是否报名，如果报名了返回true，反之返回false
     * @param userId
     * @param courseId
     * @return
     */
    Boolean judge(Integer userId, String courseId);
}
