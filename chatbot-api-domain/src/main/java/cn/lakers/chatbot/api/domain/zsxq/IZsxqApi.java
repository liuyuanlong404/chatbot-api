package cn.lakers.chatbot.api.domain.zsxq;

import cn.lakers.chatbot.api.domain.zsxq.model.vo.Root;

import java.io.IOException;

/**
 * Created on 2023/2/15 15:16
 *
 * @author lakers
 */
public interface IZsxqApi {

    Root getQuestionList(String groupId, String cookie) throws IOException;

    boolean answer(String groupId, String cookie, String topicId, String text) throws IOException;
}
