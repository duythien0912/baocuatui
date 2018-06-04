package com.thien.rssredaer;

/**
 * Created by thien on 26-05-2018.
 */
public class FeedItem {
    String title;
    String link;
    String description;
    String pubDate;
    String thumbnailUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkbyNA88nMMzzDLixlbm46KBshwc1nNsydpNAyHuRmaDDMV1iB";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
