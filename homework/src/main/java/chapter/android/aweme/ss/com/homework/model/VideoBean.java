package chapter.android.aweme.ss.com.homework.model;

public class VideoBean {
    private String id;
    private String feedurl;
    private String nickname;
    private String description;
    private int  likecount;
    private String avatar;

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }

    public String getFeedurl(){
        return feedurl;
    }
    public void setFeedurl(String feedurl){
        this.feedurl=feedurl;
    }

    public String getNickname(){
        return nickname;
    }
    public void setNickname(String nickname){
        this.nickname=nickname;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }

    public String getAvatar(){
        return avatar;
    }
    public void setAvatar(String avatar){
        this.avatar=avatar;
    }

    public int getLikecount(){
        return likecount;
    }
    public void setLikecount(int likecount){
        this.likecount=likecount;
    }
}
