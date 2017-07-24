package fiuba.challenge.model;

import java.io.Serializable;

/**
 * Created by german on 5/31/2017.
 */
public class Proof implements Serializable {

    private Challenge challenge;
    private String videoId;
    private String username;

    public Proof(String videoId, String username, Long creationDate) {
        this.videoId = videoId;
        this.username = username;
        this.creationDate = creationDate;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    private Long creationDate;

    public Proof(){

    }
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
