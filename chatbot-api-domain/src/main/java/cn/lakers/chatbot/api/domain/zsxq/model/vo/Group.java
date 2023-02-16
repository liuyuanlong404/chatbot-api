package cn.lakers.chatbot.api.domain.zsxq.model.vo;

/**
 * @author lakers
 */
public class Group {
    private Long group_id;

    private String name;

    private String type;

    public Long getGroup_id() {
        return this.group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}