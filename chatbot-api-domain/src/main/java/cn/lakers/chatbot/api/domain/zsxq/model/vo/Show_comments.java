package cn.lakers.chatbot.api.domain.zsxq.model.vo;

public class Show_comments {
    private Long comment_id;

    private String create_time;

    private Owner owner;

    private String text;

    private Long likes_count;

    private Long rewards_count;

    private Boolean sticky;

    public Long getComment_id() {
        return this.comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public String getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

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

    public Long getLikes_count() {
        return this.likes_count;
    }

    public void setLikes_count(Long likes_count) {
        this.likes_count = likes_count;
    }

    public Long getRewards_count() {
        return this.rewards_count;
    }

    public void setRewards_count(Long rewards_count) {
        this.rewards_count = rewards_count;
    }

    public Boolean getSticky() {
        return this.sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }
}