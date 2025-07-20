package Community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityService {
    private CommunityDAO communityDAO = new CommunityDAO();
    private CommentDAO commentDAO = new CommentDAO();
    
    // 게시글 목록 조회 (페이징)
    public Map<String, Object> getPostList(int page, int pageSize) {
        Map<String, Object> result = new HashMap<>();
        
        List<CommunityDTO> postList = communityDAO.getPostList(page, pageSize);
        int totalPosts = communityDAO.getPostCount();
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        
        result.put("postList", postList);
        result.put("currentPage", page);
        result.put("totalPages", totalPages);
        result.put("totalPosts", totalPosts);
        
        return result;
    }
    
    // 게시글 작성
    public int createPost(CommunityDTO post) {
        return communityDAO.createPost(post);
    }
    
    // 게시글 상세 조회
    public CommunityDTO getPost(int postId) {
        return communityDAO.getPost(postId);
    }
    
    // 게시글에 달린 댓글 목록 조회
    public List<CommentDTO> getCommentList(int postId) {
        return commentDAO.getCommentList(postId);
    }
    
    // 게시글 수정
    public boolean updatePost(CommunityDTO post) {
        return communityDAO.updatePost(post);
    }
    
    // 게시글 삭제
    public boolean deletePost(int postId, String userId) {
        return communityDAO.deletePost(postId, userId);
    }
    
    // 좋아요 증가
    public boolean increaseLike(int postId) {
        return communityDAO.increaseLike(postId);
    }
    
    // 댓글 작성
    public boolean addComment(CommentDTO comment) {
        return commentDAO.addComment(comment);
    }
    
    // 댓글 삭제
    public boolean deleteComment(int commentId, String userId) {
        return commentDAO.deleteComment(commentId, userId);
    }
    
    // 게시글 검색
    public Map<String, Object> searchPosts(String searchType, String keyword, int page, int pageSize) {
        Map<String, Object> result = new HashMap<>();
        
        List<CommunityDTO> postList = communityDAO.searchPosts(searchType, keyword, page, pageSize);
        int totalPosts = communityDAO.getSearchPostCount(searchType, keyword);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        
        result.put("postList", postList);
        result.put("currentPage", page);
        result.put("totalPages", totalPages);
        result.put("totalPosts", totalPosts);
        result.put("searchType", searchType);
        result.put("keyword", keyword);
        
        return result;
    }
}