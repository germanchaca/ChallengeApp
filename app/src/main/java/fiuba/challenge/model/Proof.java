package fiuba.challenge.model;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

public class Proof implements Serializable {

    private Challenge challenge;
    private String videoId;
    private String username;
    private Long creationDate;

    public Proof(String videoId, String username, Long creationDate) {
        this.videoId = videoId;
        this.username = username;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public Proof(){
        this.creationDate =  System.currentTimeMillis();;
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

    public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        try {
            jo.put("creationDate", creationDate);
            jo.put("username", username);
            jo.put("videoId", videoId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }
}
