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
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
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

    @Resource(name = "taskExecutor")
    private AsyncTaskExecutor taskExecutor;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void run() {
        try {
            if (new Random().nextBoolean()) {
                logger.info("随机打烊中。。。");
                return;
            }

            int hour = LocalDateTime.now().getHour();
            if (hour > 22 || hour < 7) {
                logger.info("AI下班打烊中。。。");
                return;
            }
            // 1.请求问题
            Root questionList = zsxqApi.getQuestionList(groupId, cookie);
            List<Topics> topics = questionList.getResp_data().getTopics();
            if (CollectionUtil.isEmpty(topics)) {
                logger.info("本次检索未查询到待回答的问题！");
                return;
            }
            logger.info("拉取所有问题：");
            topics.forEach(t -> logger.info("topicId：{}, 问题：{}", t.getTopic_id(), JSON.toJSONString(t.getTalk().getText())));

            topics = topics.stream().filter(t -> CollectionUtil.isEmpty(t.getShow_comments())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(topics)) {
                logger.info("本次检索未查询到待回答的问题！所有问题都已回答！");
                return;
            }
            logger.info("未回答的问题：");
            topics.forEach(t -> logger.info("topicId：{}, 问题：{}", t.getTopic_id(), JSON.toJSONString(t.getTalk().getText())));

            // 2.请求GPT 回答问题
            topics.forEach(topic -> taskExecutor.execute(() -> {
                logger.info("等待GPT回复中。。。,topicId：{}, 问题：{}", topic.getTopic_id(), JSON.toJSONString(topic.getTalk().getText()));
                String answer;
                boolean status;
                try {
                    answer = openAI.doChatGPT(topic.getTalk().getText().trim());
                    status = zsxqApi.answer(groupId, cookie, topic.getTopic_id().toString(), answer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                logger.info("编号：{} 问题:{} 回答:{} 状态:{}", topic.getTopic_id(), topic.getTalk().getText(), answer, status);
            }));
        } catch (Exception e) {
            logger.error("自动回答问题异常", e);
        }
    }
}
