package fiuba.challenge.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by german on 5/31/2017.
 */
public class Challenge implements Serializable {
    private String title;
    private String rulesVideoUrl;
    private List<Proof> proofs;

    public List<Proof> getProofs() {
        return proofs;
    }

    public void setProofs(List<Proof> proofs) {
        this.proofs = proofs;
    }

    public Challenge(String title,  String rulesVideoUrl) {
        this.title = title;
        this.rulesVideoUrl = rulesVideoUrl;
    }

    public String getRulesVideoUrl() {
        return rulesVideoUrl;
    }

    public void setRulesVideoUrl(String rulesVideoUrl) {
        this.rulesVideoUrl = rulesVideoUrl;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
