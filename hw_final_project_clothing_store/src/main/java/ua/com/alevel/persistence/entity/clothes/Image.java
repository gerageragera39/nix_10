package ua.com.alevel.persistence.entity.clothes;

import ua.com.alevel.persistence.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {

    private String url;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "thing_id")
    private Clothes thing;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Clothes getThing() {
        return thing;
    }

    public void setThing(Clothes thing) {
        this.thing = thing;
    }
}
