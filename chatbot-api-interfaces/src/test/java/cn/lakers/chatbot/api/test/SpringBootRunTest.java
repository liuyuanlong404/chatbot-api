package cn.lakers.chatbot.api.test;

import cn.lakers.chatbot.api.domain.ai.IOpenAI;
import cn.lakers.chatbot.api.domain.zsxq.IZsxqApi;
import cn.lakers.chatbot.api.domain.zsxq.model.vo.Root;
import cn.lakers.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import com.lakers.digest.Digest;
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

    private final Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);
    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    @Resource
    private Digest digest;

    @Test
    public void test_digest() {
        logger.info(digest.digest("123456"));
    }

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
        String doChatGPT = openAI.doChatGPT("CloseableHttpClient httpClient = HttpClientBuilder.create().build();\n" +
                "        HttpGet httpGet = new HttpGet(\"https://api.zsxq.com/v2/groups/\" + groupId + \"/topics?scope=all&count=20\");\n" +
                "        httpGet.addHeader(\"cookie\", cookie);\n" +
                "        httpGet.addHeader(\"content-type\", \"application/json; charset=UTF-8\");\n" +
                "        httpGet.addHeader(\"user-agent\", \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 Edg/109.0.1518.78\");\n" +
                "        CloseableHttpResponse response = httpClient.execute(httpGet);\n" +
                "        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {\n" +
                "            String json = EntityUtils.toString(response.getEntity());\n" +
                "            log.info(\"拉取提问数据。groupId:{} jsonStr:{}\", groupId, json);\n" +
                "            return JSON.parseObject(json, Root.class);\n" +
                "        } else {\n" +
                "            throw new RuntimeException(\"请求失败！\");\n" +
                "        }");
        logger.info("测试回答{}", doChatGPT);
    }
}


