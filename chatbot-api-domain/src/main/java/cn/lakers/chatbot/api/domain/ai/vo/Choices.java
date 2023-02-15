package cn.lakers.chatbot.api.domain.ai.vo;

/**
 * Created on 2023/2/15 17:41
 *
 * @author lakers
 */
public class Choices {
    private String text;

    private Long index;

    private String logprobs;

    private String finish_reason;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getIndex() {
        return this.index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getLogprobs() {
        return this.logprobs;
    }

    public void setLogprobs(String logprobs) {
        this.logprobs = logprobs;
    }

    public String getFinish_reason() {
        return this.finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }
}
