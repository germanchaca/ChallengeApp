package fiuba.challenge.model;

/**
 * Created by german on 7/24/2017.
 */
public class ProofWrapper {
    private String videoId;
    private String username;
    private String creationDate;

    public ProofWrapper(){
        this.creationDate = Long.toString(System.currentTimeMillis());
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
