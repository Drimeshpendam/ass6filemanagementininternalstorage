package com.example.ass6filemanagementininternalstorage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView messageTextView;

    // File name for internal storage
    private static final String FILE_NAME = "user_input.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        messageTextView = findViewById(R.id.messageTextView);

        Button saveButton = findViewById(R.id.saveButton);
        Button readButton = findViewById(R.id.readButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromFile();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile();
            }
        });
    }

    // Method to save text to internal storage
    private void saveToFile() {
        String textToSave = editText.getText().toString().trim();
        if (textToSave.isEmpty()) {
            Toast.makeText(this, "Please enter some text.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(textToSave);
            writer.close();
            Toast.makeText(this, "File saved successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error saving file: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Method to read text from the file in internal storage
    private void readFromFile() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            messageTextView.setText(stringBuilder.toString());
            bufferedReader.close();
            Toast.makeText(this, "File read successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            messageTextView.setText("");
            Toast.makeText(this, "Error reading file: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Method to delete the saved file
    private void deleteFile() {
        boolean isDeleted = deleteFile(FILE_NAME);
        if (isDeleted) {
            messageTextView.setText("");
            Toast.makeText(this, "File deleted successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error deleting file or file does not exist.", Toast.LENGTH_LONG).show();
        }
    }
}
