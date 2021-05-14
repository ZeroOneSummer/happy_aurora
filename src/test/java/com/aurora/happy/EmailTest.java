package com.aurora.happy;

import com.aurora.happy.base.WebAppTest;
import com.aurora.happy.utils.PropertyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;

/**
 * Created by pijiang on 2021/5/14.
 * 邮件发送
 */
@Slf4j
public class EmailTest extends WebAppTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendEMail() {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            // 标题
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(PropertyUtil.getProperty("email.sender"), "BOSS直聘后台系统");
            helper.setTo("799296969@qq.com");
            helper.setSubject("简历投递提醒");

            // 模板数据
            HashMap<String, Object> paramMap = new HashMap<>(16);
            paramMap.put("candidate", "小鱼儿");
            paramMap.put("sex", 2);  //1-男
            paramMap.put("publisher", "江秋白");
            paramMap.put("jobName", "【会计助理-深圳】");
            paramMap.put("icon", "https://pic4.zhimg.com/v2-1907eb21be63d35b077e6ed3cbcbfe13_xll.jpg");
            paramMap.put("school", "湖南财经大学");
            paramMap.put("major", "经济管理");
            paramMap.put("education", "本科");
            paramMap.put("status", "已离职");
            paramMap.put("expectJob", "会计助理");
            paramMap.put("expectCity", "深圳");
            paramMap.put("salaryBegin", 10);
            paramMap.put("salaryEnd", 12);
            paramMap.put("appendixUrl", "https://wise-job.oss-cn-zhangjiakou.aliyuncs.com/wiseJob/1601035929262.pdf");
            paramMap.put("appendixName", "快到碗里来.pdf");

            // 填充页面
            Context context = new Context();
            context.setVariables(paramMap);
            String emailContent = templateEngine.process("email", context);

            // 发送邮件
            helper.setText(emailContent, true);
            javaMailSender.send(message);

            log.info("简历投递提醒发送成功！");
        } catch (Exception e) {
            log.error("简历投递提醒发送失败！", e);
        }
    }
}