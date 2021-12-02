package ua.com.alevel.persistence.entity;

import java.util.Date;

public abstract class BaseEntity {

    private Long id;
    private Date created;
    private Date updated;
    private boolean visible;

    public BaseEntity() {
        this.created = new Date();
        this.updated = new Date();
        this.visible = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
