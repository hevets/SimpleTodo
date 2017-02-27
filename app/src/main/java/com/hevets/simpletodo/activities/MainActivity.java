package com.hevets.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.hevets.simpletodo.R;
import com.hevets.simpletodo.adapters.TodoItemAdapter;
import com.hevets.simpletodo.models.TodoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<TodoItem> todoItems;
    TodoItemAdapter itemsAdapter;
    ListView lvItems;

    // stores edit item request code
    // TODO: refactor into enum later to store different request types
    private final int EDIT_ITEM_REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoItems = new ArrayList<TodoItem>();

        // configure lvItems
        lvItems = (ListView) findViewById(R.id.lvItems);

        // populate List of TodoItems
        readItems();

        // setup adapter and lvItems
        itemsAdapter = new TodoItemAdapter(this, todoItems);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {
            TodoItem item = (TodoItem) Parcels.unwrap(data.getParcelableExtra("todoItem"));
            todoItems.set(item.getPosition(), item);
            syncItems(true);
        }
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if (itemText.length() > 0) {

            // add to the adapter
            TodoItem item = new TodoItem(todoItems.size(), itemText);
            itemsAdapter.add(item);

            // TODO: save to the database
            item.save();

            etNewItem.setText("");
            syncItems(false); // NOTE: could do a full sync here if i wasn't using add above (better to use add above)
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the todoItem and remove it from the array list
                TodoItem item = todoItems.get(position);
                todoItems.remove(position);

                // TODO: soft delete todo's here
                item.delete();

                // update the UI
                syncItems(true);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                TodoItem item = todoItems.get(position);
                i.putExtra("todoItem", Parcels.wrap(item));
                startActivityForResult(i, EDIT_ITEM_REQUEST_CODE);
            }
        });
    }

    private void syncItems(Boolean shouldNotifyDataSetChanged) {
        if (shouldNotifyDataSetChanged) {
            itemsAdapter.notifyDataSetChanged();
        }
    }

    private void readItems() {
        List<TodoItem> todosFromDB = SQLite.select()
                .from(TodoItem.class)
                .queryList();

        for(TodoItem item : todosFromDB) {
            todoItems.add(item);
        }
    }

}
