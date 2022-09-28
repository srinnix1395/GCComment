package io.srinnix.gccomment.data.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Link {

    private static final String JSON_KEY_URL = "url";
    private static final String JSON_KEY_TITLE = "title";

    private String title;

    private String url;

    public Link(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(title, link.title) && Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }

    public JSONObject generateJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (url != null && !url.isEmpty()) {
            jsonObject.put(JSON_KEY_URL, url);
        }
        if (title != null && !title.isEmpty()) {
            jsonObject.put(JSON_KEY_TITLE, title);
        }
        if (jsonObject.length() == 0) {
            return null;
        } else {
            return jsonObject;
        }
    }
}
