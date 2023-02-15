package cn.lakers.chatbot.api.domain.zsxq.model.vo;

public class User_specific {
    private boolean liked;

    private boolean subscribed;

    public boolean getLiked() {
        return this.liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean getSubscribed() {
        return this.subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}