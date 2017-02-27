package com.hevets.simpletodo.models;

import com.hevets.simpletodo.database.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

@Parcel(analyze = {TodoItem.class})
@Table(database = MyDatabase.class)
public class TodoItem extends BaseModel {

    @PrimaryKey
    @Column
    int id;

    @Column
    int position;

    @Column
    String title;

    // Needed by Parcel
    public TodoItem() {
    }

    public TodoItem(int position, String title) {
        this.id = position; // TODO: this is a total hack, super messy but its working with SQLite
        this.position = position;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
