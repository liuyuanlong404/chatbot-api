package cn.lakers.chatbot.api.domain.zsxq.model.vo;

public class Root {
    private boolean succeeded;

    private Resp_data resp_data;

    public boolean getSucceeded() {
        return this.succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public Resp_data getResp_data() {
        return this.resp_data;
    }

    public void setResp_data(Resp_data resp_data) {
        this.resp_data = resp_data;
    }
}