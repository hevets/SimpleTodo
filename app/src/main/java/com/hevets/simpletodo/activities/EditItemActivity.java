package com.hevets.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

        Spinner spinner = (Spinner)findViewById(R.id.spinPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setSelection(getPosByPriority(item.getPriority()));
    }


    public void onEditItem(View v) {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        Spinner spinner = (Spinner)findViewById(R.id.spinPriority);
        String updatedItemTitle = etEditItem.getText().toString();
        String priority = spinner.getSelectedItem().toString();

        if (updatedItemTitle.length() > 0) {
            Intent i = new Intent();
            todoItem.setTitle(updatedItemTitle);
            todoItem.setPriority(priority);
            i.putExtra("todoItem", Parcels.wrap(todoItem));
            setResult(RESULT_OK, i);
            finish();
        }
    }

    private int getPosByPriority(String priority) {
        String[] strings = {"Low", "Medium", "High"};

        int pos = -1;

        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(priority)) {
                pos = i;
            }
        }

        return pos;
    }


}
