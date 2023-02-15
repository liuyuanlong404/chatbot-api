package cn.lakers.chatbot.api.domain.ai.service;

import cn.lakers.chatbot.api.domain.ai.IOpenAI;
import cn.lakers.chatbot.api.domain.ai.vo.AIAnswer;
import cn.lakers.chatbot.api.domain.ai.vo.Choices;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created on 2023/2/15 17:41
 *
 * @author lakers
 */
@Service
public class OpenAi implements IOpenAI {

    private final Logger log = LoggerFactory.getLogger(OpenAi.class);

    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;

    @Override
    public String doChatGPT(String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/completions");
        httpPost.addHeader("Authorization", "Bearer " + openAiKey);
        httpPost.addHeader("content-type", "application/json");
        String json = "{\"model\": \"text-davinci-003\", \"prompt\": \" " + question + "\", \"temperature\": 0, \"max_tokens\": 1024}";
        StringEntity entity = new StringEntity(json, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(entity);
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            throw new RuntimeException("请求openAi失败!");
        }
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            log.info("chatGPT回复：{}", res);
            AIAnswer aiAnswer = JSON.parseObject(res, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            choices.forEach(t -> answers.append(t.getText()));
            return answers.toString();
        } else {
            throw new RuntimeException("请求失败！");
        }
    }
}
