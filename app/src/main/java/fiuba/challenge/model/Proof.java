package fiuba.challenge.model;

import java.io.Serializable;

/**
 * Created by german on 5/31/2017.
 */
public class Proof implements Serializable {
    private final Challenge challenge;
    private String urlVideo;
    private User user;

    public Proof(String urlVideo, User user, Challenge challenge) {
        this.urlVideo = urlVideo;
        this.user = user;
        this.challenge = challenge;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }


    public Challenge getChallenge() {
        return challenge;
    }
}
