package cn.lakers.chatbot.api.domain.zsxq.service;

import cn.lakers.chatbot.api.domain.zsxq.IZsxqApi;
import cn.lakers.chatbot.api.domain.zsxq.model.req.AnswerReq;
import cn.lakers.chatbot.api.domain.zsxq.model.req.Req_data;
import cn.lakers.chatbot.api.domain.zsxq.model.res.AnswerRes;
import cn.lakers.chatbot.api.domain.zsxq.model.vo.Root;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

/**
 * Created on 2023/2/15 15:40
 *
 * @author lakers
 */
@Service
public class ZsxqApi implements IZsxqApi {

    private final Logger log = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public Root getQuestionList(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=all&count=20");
        httpGet.addHeader("cookie", cookie);
        httpGet.addHeader("content-type", "application/json; charset=UTF-8");
        httpGet.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 Edg/109.0.1518.78");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String json = EntityUtils.toString(response.getEntity());
            log.info("拉去提问数据。groupId:{} jsonStr:{}", groupId, json);
            return JSON.parseObject(json, Root.class);
        } else {
            throw new RuntimeException("请求失败！");
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/comments");
        httpPost.addHeader("cookie", cookie);
        httpPost.addHeader("content-type", "application/json; charset=UTF-8");
        httpPost.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 Edg/109.0.1518.78");

        AnswerReq answerReq = new AnswerReq(new Req_data(text, Collections.emptyList(), Collections.emptyList()));
        String json = JSONObject.fromObject(answerReq).toString();
        StringEntity entity = new StringEntity(json, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            log.info("回答问题数据。groupId:{} topicId:{} jsonStr:{}", groupId, topicId, res);
            AnswerRes answerRes = JSON.parseObject(res, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("回答失败！");
        }

    }
}
