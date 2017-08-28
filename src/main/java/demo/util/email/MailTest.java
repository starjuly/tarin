package demo.util.email;

import java.util.ArrayList;
import java.util.List;


public class MailTest {

    /**
     * yanzf@bingosoft.net
     * helr@bingosoft.net
     * wangshizhan@bingosoft.net
     * wanglx@bingosoft.net
     */
    public static void main(String[] args) {

        SimpleMailSender ss = new SimpleMailSender();
        List<String> tos = new ArrayList<String>();
        tos.add("moxj@bingosoft.net");
        //tos.add("13572812375@163.com");   
        ss.createMail();
        ss.setSubject("测试的邮件");
        //ss.setTo(tos);  
        ss.addContent("测试内容");
//        ss.addImage(new File("H:\\JavaDev\\Bingo\\linkVoteApp\\data\\旅游投票.png"));  
//        ss.addAttach(new File("H:\\JavaDev\\Bingo\\linkVoteApp\\data\\vote.txt"));  
        ss.sendMail(tos);
    }

    public static void send(String emailAddress,String subject,String content){
        SimpleMailSender ss = new SimpleMailSender();
        List<String> tos = new ArrayList<String>();
        tos.add(emailAddress);
        ss.createMail();
        ss.setSubject(subject);
//        ss.addContent(content);
        ss.addHtml(content+"<h4>如有其他协助，可以邮件回复。</h4>");
        ss.sendMail(tos);

    }
}

