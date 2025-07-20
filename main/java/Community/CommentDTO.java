package Community;

import java.sql.Timestamp;

public class CommentDTO {
    private int comment_id;
    private int post_id;
    private String user_id;
    private String content;
    private Timestamp created_at;
    

    public CommentDTO() {}
    
    public CommentDTO(int comment_id, int post_id, String user_id, String content, Timestamp created_at) {
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = content;
        this.created_at = created_at;
    }
    
 
    public CommentDTO(int post_id, String user_id, String content) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = content;
    }
    
    
    public int getComment_id() {
        return comment_id;
    }
    
    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }
    
    public int getPost_id() {
        return post_id;
    }
    
    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
    
    public String getUser_id() {
        return user_id;
    }
    
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Timestamp getCreated_at() {
        return created_at;
    }
    
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
    
    
    public String getTimeAgo() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long diffInSeconds = (now.getTime() - created_at.getTime()) / 1000;
        
        if (diffInSeconds < 60) {
            return diffInSeconds + "초 전";
        } else if (diffInSeconds < 3600) {
            return (diffInSeconds / 60) + "분 전";
        } else if (diffInSeconds < 86400) {
            return (diffInSeconds / 3600) + "시간 전";
        } else {
            return (diffInSeconds / 86400) + "일 전";
        }
    }
}