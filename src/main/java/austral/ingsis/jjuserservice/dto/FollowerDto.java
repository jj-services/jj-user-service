package austral.ingsis.jjuserservice.dto;

public class FollowerDto {

    private Long userId;
    private Long followingId;

    public FollowerDto(Long userId, Long followingId) {
        this.userId = userId;
        this.followingId = followingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }
}
