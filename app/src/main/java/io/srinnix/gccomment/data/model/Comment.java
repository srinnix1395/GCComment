package io.srinnix.gccomment.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class Comment {

    private static final String JSON_KEY_TAG = "mentions";
    private static final String JSON_KEY_LINK = "links";

    private String id;

    private String content;

    private List<Link> links;

    private List<String> tags;

    private final String json;

    private boolean expand = true;

    public Comment(Comment comment) {
        this(comment.id, comment.content, comment.links, comment.tags);
    }

    public Comment(String id, String content, List<Link> links, List<String> tags) {
        this.id = id;
        this.content = content;
        this.links = links;
        this.tags = tags;
        this.json = generateJson();
    }

    private String generateJson() {
        if (links == null && tags == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject();

            if (links != null && !links.isEmpty()) {
                JSONArray linkJsonArray = new JSONArray();

                for (Link link : links) {
                    JSONObject linkJsonObject = link.generateJson();
                    if (linkJsonObject != null) {
                        linkJsonArray.put(linkJsonObject);
                    }
                }

                jsonObject.put(JSON_KEY_LINK, linkJsonArray);
            }

            if (tags != null && !tags.isEmpty()) {
                JSONArray tagJsonArray = new JSONArray();

                for (String tag : tags) {
                    tagJsonArray.put(tag.substring(1));
                }

                jsonObject.put(JSON_KEY_TAG, tagJsonArray);
            }

            return jsonObject.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public String getJson() {
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return expand == comment.expand
                && Objects.equals(id, comment.id)
                && Objects.equals(content, comment.content)
                && Objects.equals(links, comment.links)
                && Objects.equals(tags, comment.tags)
                && Objects.equals(json, comment.json);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, links, tags, json, expand);
    }
}
