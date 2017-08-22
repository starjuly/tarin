package demo.service.impl;

import demo.model.Email;
import demo.service.EmailService;
import leap.orm.query.CriteriaQuery;

/**
 * Created by MoSon on 2017/8/19.
 */

public class EmailServiceImpl implements EmailService{

    public void create(Email email) {
       email.create();
    }
}
