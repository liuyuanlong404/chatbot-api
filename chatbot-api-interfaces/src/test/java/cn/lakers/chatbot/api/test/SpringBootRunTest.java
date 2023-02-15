package cn.lakers.chatbot.api.test;

import cn.lakers.chatbot.api.domain.ai.IOpenAI;
import cn.lakers.chatbot.api.domain.zsxq.IZsxqApi;
import cn.lakers.chatbot.api.domain.zsxq.model.vo.Root;
import cn.lakers.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created on 2023/2/15 16:23
 *
 * @author lakers
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);
    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    @Test
    public void test_zsxqApi() throws IOException {
        Root questionList = zsxqApi.getQuestionList(groupId, cookie);
        logger.info("测试结果：{}", JSON.toJSONString(questionList));

        List<Topics> topics = questionList.getResp_data().getTopics();
        topics.forEach(topic -> {
            Long topicId = topic.getTopic_id();
            String text = topic.getTalk().getText();
            logger.info("topicId {}, text {}", topicId, text);
            try {
                zsxqApi.answer(groupId, cookie, topicId.toString(), "6666");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void test_openAi() throws IOException {
        String doChatGPT = openAI.doChatGPT("帮我写一个java冒泡排序！");
        logger.info("测试回答{}", doChatGPT);
    }
}


