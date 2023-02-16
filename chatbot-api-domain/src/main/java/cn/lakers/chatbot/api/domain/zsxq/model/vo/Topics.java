package cn.lakers.chatbot.api.domain.zsxq.model.vo;

import java.util.List;

public class Topics {
    private Long topic_id;

    private Group group;

    private String type;

    private Talk talk;

    private List<Show_comments> show_comments;

    private Long likes_count;

    private Long rewards_count;

    private Long comments_count;

    private Long reading_count;

    private Long readers_count;

    private Boolean digested;

    private Boolean sticky;

    private String create_time;

    private User_specific user_specific;

    public Long getTopic_id() {
        return this.topic_id;
    }

    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Talk getTalk() {
        return this.talk;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    public List<Show_comments> getShow_comments() {
        return this.show_comments;
    }

    public void setShow_comments(List<Show_comments> show_comments) {
        this.show_comments = show_comments;
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

    public Long getComments_count() {
        return this.comments_count;
    }

    public void setComments_count(Long comments_count) {
        this.comments_count = comments_count;
    }

    public Long getReading_count() {
        return this.reading_count;
    }

    public void setReading_count(Long reading_count) {
        this.reading_count = reading_count;
    }

    public Long getReaders_count() {
        return this.readers_count;
    }

    public void setReaders_count(Long readers_count) {
        this.readers_count = readers_count;
    }

    public Boolean getDigested() {
        return this.digested;
    }

    public void setDigested(Boolean digested) {
        this.digested = digested;
    }

    public Boolean getSticky() {
        return this.sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    public String getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public User_specific getUser_specific() {
        return this.user_specific;
    }

    public void setUser_specific(User_specific user_specific) {
        this.user_specific = user_specific;
    }
}