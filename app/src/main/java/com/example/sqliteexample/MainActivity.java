package com.example.sqliteexample;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextName, editTextAge;
    private ListView listViewData;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        Button buttonSave = findViewById(R.id.buttonSave);
        listViewData = findViewById(R.id.listViewData);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                displayData();
            }
        });

        displayData();
    }

    private void saveData() {
        String name = editTextName.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        int age = Integer.parseInt(ageStr);

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_AGE, age);

        db.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    private void displayData() {
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                null, null, null, null, null, null);

        String[] from = {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_AGE};
        int[] to = {android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item,
                cursor,
                from,
                to,
                0);

        listViewData.setAdapter(adapter);
    }
}
