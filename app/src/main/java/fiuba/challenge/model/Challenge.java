package fiuba.challenge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by german on 5/31/2017.
 */
public class Challenge implements Serializable {
    private String title;

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
}
