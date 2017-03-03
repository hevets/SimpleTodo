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
    String title;

    // Needed by Parcel
    public TodoItem() {
    }

    public TodoItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
