package cn.lakers.chatbot.api.domain.zsxq.model.vo;

/**
 * @author lakers
 */
public class Talk {
    private Owner owner;

    private String text;

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}