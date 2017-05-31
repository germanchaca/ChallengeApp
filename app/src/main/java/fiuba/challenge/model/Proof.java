package fiuba.challenge.model;

/**
 * Created by german on 5/31/2017.
 */
public class Proof {
    private String urlVideo;
    private User user;

    public Proof(String urlVideo, User user) {
        this.urlVideo = urlVideo;
        this.user = user;
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


}
