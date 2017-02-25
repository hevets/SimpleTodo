package com.hevets.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    // stores edit item request code
    // TODO: refactor into enum later to store different request types
    private final int EDIT_ITEM_REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // configure lvItems
        lvItems = (ListView) findViewById(R.id.lvItems);

        // populate arraylist
        readItems();

        // setup adapter and lvItems
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {
            String editItemText = data.getExtras().getString("editItem");
            int position = data.getExtras().getInt("position");

            items.get(position);
            items.set(position, editItemText);
            syncItems(true);
        }
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if (itemText.length() > 0) {
            itemsAdapter.add(itemText);
            etNewItem.setText("");
            syncItems(false);
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                syncItems(true);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("editItem", items.get(position));
                i.putExtra("position", position);
                startActivityForResult(i, EDIT_ITEM_REQUEST_CODE);
            }
        });
    }

    private File getFile() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        return todoFile;
    }

    private void syncItems(Boolean shouldNotifyDataSetChanged) {
        if (shouldNotifyDataSetChanged) {
            itemsAdapter.notifyDataSetChanged();
        }

        writeItems();
    }

    private void readItems() {
        File file = getFile();

        try {
            items = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File file = getFile();
        try {
            FileUtils.writeLines(file, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
