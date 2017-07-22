package fiuba.challenge.model;

import java.io.Serializable;

/**
 * Created by german on 5/31/2017.
 */
public class User implements Serializable{
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
