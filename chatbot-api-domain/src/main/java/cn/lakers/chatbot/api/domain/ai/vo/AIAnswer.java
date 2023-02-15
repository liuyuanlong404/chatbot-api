package cn.lakers.chatbot.api.domain.ai.vo;

import java.util.List;

/**
 * Created on 2023/2/15 17:51
 *
 * @author lakers
 */
public class AIAnswer {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Choices> choices;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return this.object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getCreated() {
        return this.created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choices> getChoices() {
        return this.choices;
    }

    public void setChoices(List<Choices> choices) {
        this.choices = choices;
    }
}
