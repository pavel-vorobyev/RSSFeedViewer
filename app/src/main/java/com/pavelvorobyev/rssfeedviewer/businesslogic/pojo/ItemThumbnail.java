package com.pavelvorobyev.rssfeedviewer.businesslogic.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "thumbnail", strict = false)
public class ItemThumbnail {

    @Attribute(name = "url")
    private String url;

    public String getUrl() {
        return url;
    }
}
