package com.example.android.abccollege02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase db;

    DepartmentDetails d;

    private ListView deptList;

    private ArrayList<String> arr;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arr = new ArrayList<>();

        db = FirebaseDatabase.getInstance();

        deptList = findViewById(R.id.dept_list);

        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arr);

        deptList.setAdapter(adapter);

        ReadDepts();

        deptList.setOnItemClickListener(new MyListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cust_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            FirebaseAuth fa = FirebaseAuth.getInstance();
            fa.signOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ValueEventListener valEvtListnr = new ValueEventListener() {

        @Override

        public void onDataChange(DataSnapshot dataSnapshot) {

            for (DataSnapshot DepartmentSnapshot : dataSnapshot.getChildren()) {

                String deptName = (String) DepartmentSnapshot.child("deptName").getValue();

                arr.add(deptName);

                adapter.notifyDataSetChanged();

            }

        }

        @Override

        public void onCancelled(DatabaseError databaseError) {

            Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();

        }

    };

    public void goToAddDept(View view) {

        Intent intent = new Intent(this, AddDeptActivity.class);

        startActivity(intent);
    }


    private void ReadDepts() {

        DatabaseReference deptRef = db.getReference("Departments");

        deptRef.addValueEventListener(valEvtListnr);

//        deptRef2.addChildEventListener(childEventListener);

    }


    public class MyListener implements AdapterView.OnItemClickListener {


        @Override


        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            final String deptName = arr.get(i);

            DatabaseReference deptRef = db.getReference("Departments");

            deptRef.addValueEventListener(new ValueEventListener() {

                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot DepartmentSnapshot : dataSnapshot.getChildren()) {

//                        String dept = (String)DepartmentSnapshot.child("deptName").getValue();

                        d = DepartmentSnapshot.getValue(DepartmentDetails.class);

                        if (d != null && d.getDeptName().equals(deptName)) {

                            Intent intent = new Intent(MainActivity.this, TeachersStudentsActivity.class);

                            intent.putExtra("Dept Name", d.getDeptName());

                            Toast.makeText(MainActivity.this, "Dept Name: " + d.getDeptName(), Toast.LENGTH_SHORT).show();

                            startActivity(intent);
                        }
                    }
                }

                @Override

                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
