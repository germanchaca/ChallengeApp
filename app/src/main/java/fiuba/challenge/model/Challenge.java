package fiuba.challenge.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by german on 5/31/2017.
 */
public class Challenge implements Serializable {
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private String description;
    private String rulesVideoId;
    private Long creationDate;
    private List<Proof> proofs;

    public List<Proof> getProofs() {
        return proofs;
    }

    public void setProofs(List<Proof> proofs) {
        this.proofs = proofs;
    }
    public void addProof(Proof proof){
        this.proofs.add(proof);
    }

    public Challenge() {
        this.proofs = new ArrayList<>();
    }

    public Challenge(String title,  String rulesVideoUrl) {
        this.proofs = new ArrayList<>();
        this.title = title;
        this.rulesVideoId = rulesVideoUrl;
    }

    public String getRulesVideoId() {
        return rulesVideoId;
    }

    public void setRulesVideoId(String rulesVideoUrl) {
        this.rulesVideoId = rulesVideoUrl;
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

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        try {
            jo.put("creationDate", creationDate);
            jo.put("username", username);
            jo.put("videoId", rulesVideoId);
            jo.put("title", title);
            jo.put("description", description);

            //getProofJsonObject(jo);

            Log.d("Challenge.java ",jo.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }

    private void getProofJsonObject(JSONObject jo) throws JSONException {
        JSONArray jsArray = new JSONArray();
        Iterator iterator = this.proofs.iterator();
        while (iterator.hasNext()){
            jsArray.put( ((Proof)iterator.next()).toJSON());
        }
        jo.put("proofs", jsArray);
    }

}
