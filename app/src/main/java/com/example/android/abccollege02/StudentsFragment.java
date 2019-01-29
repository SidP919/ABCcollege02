package com.example.android.abccollege02;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StudentsFragment extends Fragment {

    String deptName, category;

    ArrayList<String> studentsList;

    ArrayAdapter<String> adapter;

    Button addDeleteButton;

    DatabaseReference databaseReference;

    ValueEventListener valEvtListnr = new ValueEventListener() {

        @Override

        public void onDataChange(DataSnapshot dataSnapshot) {

            for (DataSnapshot StudentsSnapshot : dataSnapshot.getChildren()) {

                String studentName = (String) StudentsSnapshot.child("sName").getValue();

                String studentPost = (String) StudentsSnapshot.child("sPost").getValue();

                studentsList.add(studentName + " Designation: " + studentPost);

                adapter.notifyDataSetChanged();

                TabLayout tab = StudentsFragment.super.getActivity().findViewById(R.id.teachers_students_Tab);

                tab.getTabAt(0).select();

            }

        }



        @Override

        public void onCancelled(DatabaseError databaseError) {

            Toast.makeText(StudentsFragment.super.getContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();

        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = "Students";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_students, container, false);

        addDeleteButton = v.findViewById(R.id.add_delete_student);
        addDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intn = new Intent(StudentsFragment.super.getContext(), TeacherStudentEditActivity.class);
                intn.putExtra("Dept Name", deptName);
                intn.putExtra("Category", category);
                startActivity(intn);
            }
        });

        ListView students = v.findViewById(R.id.students);
        studentsList = new ArrayList<>();

        adapter = new ArrayAdapter<>(StudentsFragment.super.getContext(), R.layout.support_simple_spinner_dropdown_item, studentsList);

        students.setAdapter(adapter);
        Intent intn = getActivity().getIntent();
        Bundle b = intn.getExtras();

        if (b != null) {
            deptName = b.getString("Dept Name");
        }

        ReadStudents();
        return v;
    }

    private void ReadStudents() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(deptName);
        databaseReference.addValueEventListener(valEvtListnr);

//        deptRef2.addChildEventListener(childEventListener);

    }

}
