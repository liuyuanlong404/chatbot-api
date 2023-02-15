package cn.lakers.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * Created on 2023/2/15 17:40
 *
 * @author lakers
 */
public interface IOpenAI {

    String doChatGPT(String question) throws IOException;
}
