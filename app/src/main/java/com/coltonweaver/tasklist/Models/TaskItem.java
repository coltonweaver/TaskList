package com.coltonweaver.tasklist.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Colton Weaver on 2/25/2017.
 * Model of a generic task item
 */

public class TaskItem extends RealmObject {

    @PrimaryKey
    private long id;

    private String title;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

}
