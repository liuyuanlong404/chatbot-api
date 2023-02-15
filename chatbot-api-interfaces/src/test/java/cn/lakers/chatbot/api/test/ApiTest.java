package cn.lakers.chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created on 2023/2/15 14:29
 *
 * @author lakers
 */
public class ApiTest {


    @Test
    public void questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/51112124222554/topics?scope=all&count=20");
        httpGet.addHeader("cookie", "zsxq_access_token=A6D95223-98C4-695E-7DA8-C7988183B9CB_E07264F662984160; zsxqsessionid=a58e3cf6c2494d4a354b8a48d30b5326; abtest_env=product");
        httpGet.addHeader("content-type", "application/json; charset=UTF-8");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.zsxq.com/v2/topics/584154818118414/comments");
        httpPost.addHeader("cookie", "zsxq_access_token=A6D95223-98C4-695E-7DA8-C7988183B9CB_E07264F662984160; zsxqsessionid=a58e3cf6c2494d4a354b8a48d30b5326; abtest_env=product");
        httpPost.addHeader("content-type", "application/json; charset=UTF-8");
        String json = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"2222\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";
        StringEntity entity = new StringEntity(json, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/completions");
        httpPost.addHeader("Authorization", "Bearer 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");
        httpPost.addHeader("content-type", "application/json");
        String json = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";
        StringEntity entity = new StringEntity(json, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }


}
