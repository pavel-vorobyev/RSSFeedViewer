package com.pavelvorobyev.rssfeedviewer.businesslogic.pojo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "item", strict = false)
public class Item {

    @Element(name = "title")
    private String title;
    @Path("description")
    @Text(required=false)
    private String description;
    @Element(name = "thumbnail", required = false)
    private ItemThumbnail thumbnail;
    @Element(name = "link")
    private String link;

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

    public ItemThumbnail getThumbnail() {
        return thumbnail;
    }

    public String getLink() {
        return link;
    }
}