package cn.lakers.chatbot.api.domain.zsxq.model.req;

/**
 * Created on 2023/2/15 16:10
 *
 * @author lakers
 */
public class AnswerReq {

    private Req_data req_data;

    public AnswerReq(Req_data req_data) {
        this.req_data = req_data;
    }

    public Req_data getReq_data() {
        return req_data;
    }

    public void setReq_data(Req_data req_data) {
        this.req_data = req_data;
    }
}
