package com.example.sql;

import static com.example.sql.DatabaseHelper.*;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private DataManager dataManager;
    private EditText editTextName, editTextAge;
    private Button buttonAdd, buttonUpdate, buttonDelete;
    private TextView textViewRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = new DataManager(this);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        textViewRecords = findViewById(R.id.textViewRecords);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());
                dataManager.insertRecord(name, age);
                showAllRecords();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = 1; // For demonstration, update record with ID 1
                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());
                dataManager.updateRecord(id, name, age);
                showAllRecords();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = 1; // For demonstration, delete record with ID 1
                dataManager.deleteRecord(id);
                showAllRecords();
            }
        });

        showAllRecords();
    }

    private void showAllRecords() {
        Cursor cursor = dataManager.getAllRecords();
        if (cursor.moveToFirst()) {
            StringBuilder builder = new StringBuilder();
            do {
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE));
                builder.append("ID: ").append(id).append(", Name: ").append(name).append(", Age: ").append(age).append("\n");
            } while (cursor.moveToNext());
            textViewRecords.setText(builder.toString());
        } else {
            textViewRecords.setText("No records found");
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataManager.close();
    }
}