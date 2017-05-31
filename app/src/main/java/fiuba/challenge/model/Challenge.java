package fiuba.challenge.model;

import android.graphics.drawable.Drawable;

/**
 * Created by german on 5/31/2017.
 */
public class Challenge {
    private String title;
    private Drawable frontImage;
    private String rulesVideoUrl;

    public Challenge(String title, Drawable frontImage, String rulesVideoUrl) {
        this.title = title;
        this.frontImage = frontImage;
        this.rulesVideoUrl = rulesVideoUrl;
    }

    public Drawable getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(Drawable frontImage) {
        this.frontImage = frontImage;
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
