package fiuba.challenge.model;

/**
 * Created by german on 7/24/2017.
 */
public class ChallengeWrapper {
    private String title;
    private String description;
    private String creationDate;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    private String videoId;
    private String username;

    public ChallengeWrapper(){
        this.creationDate = Long.toString(System.currentTimeMillis()/1000);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


}
