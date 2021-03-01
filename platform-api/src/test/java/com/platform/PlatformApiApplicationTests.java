package com.platform;

import com.platform.modules.sys.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformApiApplicationTests {
    @Autowired
    private MailService mailService;

    @Test
    public void testHtmlMail() {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件1111111!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendMail("939961241@qq.com", "test html mail", content);
    }
}
