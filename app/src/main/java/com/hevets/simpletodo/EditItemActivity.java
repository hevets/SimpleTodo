package com.hevets.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    String editItemText;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // get data from intent
        editItemText = getIntent().getStringExtra("editItem");
        position = getIntent().getIntExtra("position", 0);

        // configure the view for rendering
        EditText etView = (EditText) findViewById(R.id.etEditItem);
        etView.setText(editItemText);
    }

    public void onEditItem(View v) {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        String itemText = etEditItem.getText().toString();
        if (itemText.length() > 0) {
            etEditItem.setText(itemText);

            Intent i = new Intent();
            i.putExtra("editItem", itemText.toString());
            i.putExtra("position", position);
            setResult(RESULT_OK, i);
            finish();
        }
    }

}
