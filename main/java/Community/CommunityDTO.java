package Community;

import java.sql.Timestamp;

public class CommunityDTO {
	private int post_id;
	private String user_id;
	private String post_title;
	private String post_content;
	private int like_number;
	private int views_number;
	private Timestamp created_at;
    private int comment_count;
    
	public CommunityDTO(int post_id, String user_id, String post_title, String post_content, int like_number,
			int views_number, Timestamp created_at, int comment_count) {
		super();
		this.post_id = post_id;
		this.user_id = user_id;
		this.post_title = post_title;
		this.post_content = post_content;
		this.like_number = like_number;
		this.views_number = views_number;
		this.created_at = created_at;
		this.comment_count = comment_count;
	}
	
	 public CommunityDTO(String user_id, String post_title, String post_content) {
	        this.user_id = user_id;
	        this.post_title = post_title;
	        this.post_content = post_content;
	    }
	 
	 public CommunityDTO() {}

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

	public String getPost_title() {
		return post_title;
	}

	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}

	public String getPost_content() {
		return post_content;
	}

	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}

	public int getLike_number() {
		return like_number;
	}

	public void setLike_number(int like_number) {
		this.like_number = like_number;
	}

	public int getViews_number() {
		return views_number;
	}

	public void setViews_number(int views_number) {
		this.views_number = views_number;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
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
