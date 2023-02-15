package cn.lakers.chatbot.api.application.job;

import cn.hutool.core.collection.CollectionUtil;
import cn.lakers.chatbot.api.domain.ai.IOpenAI;
import cn.lakers.chatbot.api.domain.zsxq.IZsxqApi;
import cn.lakers.chatbot.api.domain.zsxq.model.vo.Root;
import cn.lakers.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created on 2023/2/15 18:07
 *
 * @author lakers
 */
@EnableScheduling
@Configuration
public class ChatBotJob {

    private final Logger logger = LoggerFactory.getLogger(ChatBotJob.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void run() {
        try {
            if (new Random().nextBoolean()) {
                logger.info("随机打烊中。。。。");
                return;
            }

            int hour = LocalDateTime.now().getHour();
            if (hour > 22 || hour < 7) {
                logger.info("AI下班打烊中。。。。");
                return;
            }
            // 请求问题
            Root questionList = zsxqApi.getQuestionList(groupId, cookie);
            logger.info("拉取问题返回：{}", JSON.toJSONString(questionList));
            List<Topics> topics = questionList.getResp_data().getTopics();
            if (CollectionUtil.isEmpty(topics)) {
                logger.info("本次检索未查询到待回答的问题！");
                return;
            }

            topics = topics.stream().filter(t -> CollectionUtil.isEmpty(t.getShow_comments())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(topics)) {
                logger.info("本次检索未查询到待回答的问题！所有问题都已回答！");
                return;
            }

            // 请求GPT
            Topics topic = topics.get(0);
            String answer = openAI.doChatGPT(topic.getTalk().getText().trim());

            // 回答问题
            boolean status = zsxqApi.answer(groupId, cookie, topic.getTopic_id().toString(), answer);
            logger.info("编号：{} 问题:{} 回答:{} 状态:{}", topic.getTopic_id(), topic.getTalk().getText(), answer, status);
        } catch (Exception e) {
            logger.error("自动回答问题异常", e);
        }
    }
}
