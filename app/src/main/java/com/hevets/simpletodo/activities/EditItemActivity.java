package com.hevets.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.hevets.simpletodo.R;
import com.hevets.simpletodo.models.TodoItem;

import org.parceler.Parcels;

public class EditItemActivity extends AppCompatActivity {

    private TodoItem todoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // configure view with the intent coming in

        configureView((TodoItem) Parcels.unwrap(getIntent().getParcelableExtra("todoItem")));
    }

    // setup view
    private void configureView(TodoItem item) {
        todoItem = new TodoItem(item.getId(), item.getTitle());
        ((EditText) findViewById(R.id.etEditItem)).setText(todoItem.getTitle());
    }


    public void onEditItem(View v) {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        String updatedItemTitle = etEditItem.getText().toString();

        if (updatedItemTitle.length() > 0) {
            Intent i = new Intent();
            todoItem.setTitle(updatedItemTitle);
            i.putExtra("todoItem", Parcels.wrap(todoItem));
            setResult(RESULT_OK, i);
            finish();
        }
    }

}
