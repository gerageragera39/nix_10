package ua.com.alevel.persistence.entity.token;

import ua.com.alevel.persistence.entity.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Token extends BaseEntity {

    private String url;

    private String content;

    public Token() {
        super();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
