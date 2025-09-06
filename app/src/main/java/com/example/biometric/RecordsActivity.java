package com.example.biometric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RecordsActivity extends AppCompatActivity {

    private ListView listViewRecords;
    private DatabaseHelper dbHelper;
    private List<User> userList;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        initViews();
        loadRecords();
        setupListeners();
    }

    private void initViews() {
        listViewRecords = findViewById(R.id.listViewRecords);
    }

    private void loadRecords() {
        dbHelper = new DatabaseHelper(this);
        userList = dbHelper.getAllUsers();
        
        if (userList.isEmpty()) {
            Toast.makeText(this, "No hay registros disponibles", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new DataAdapter(this, userList);
            listViewRecords.setAdapter(adapter);
        }
    }

    private void setupListeners() {
        listViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = userList.get(position);
                Intent intent = new Intent(RecordsActivity.this, RecordDetailActivity.class);
                intent.putExtra("userId", selectedUser.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to this activity
        if (dbHelper != null) {
            userList = dbHelper.getAllUsers();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
