import com.cyh.mail.MailApplication;
import com.cyh.mail.service.MailService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * @author Chenyuhua
 * @date 2020/5/20 22:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MailApplication.class)
public class MailTest {

    @Autowired
    MailService mailService;

    @Autowired
    private WebApplicationContext wac;

    public MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Ignore
    @Test
    public void test() {
    }

    @Test
    public void sendMail(){
        mailService.sendMail("c854815059@qq.com","验证码234432，请于十分钟内填写");
    }
}
