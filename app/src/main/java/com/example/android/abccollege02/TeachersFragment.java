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

public class TeachersFragment extends Fragment {

    String deptName, category;

    DatabaseReference teacherDatabaseReference;

    private ArrayList<String> teachersList;

    private ArrayAdapter<String> teacherAdapter;

    Button addDeleteButton1;

    ValueEventListener teacherValEvtListnr = new ValueEventListener() {

        @Override

        public void onDataChange(DataSnapshot dataSnapshot) {

            for (DataSnapshot TeachersSnapshot : dataSnapshot.getChildren()) {

                String teacherName = (String) TeachersSnapshot.child("tName").getValue();

                String teacherPost = (String) TeachersSnapshot.child("tPost").getValue();

                teachersList.add(teacherName + " Designation: " + teacherPost);

                teacherAdapter.notifyDataSetChanged();

                TabLayout tab = TeachersFragment.super.getActivity().findViewById(R.id.teachers_students_Tab);

                tab.getTabAt(0).select();

            }

        }



        @Override

        public void onCancelled(DatabaseError databaseError) {

            Toast.makeText(TeachersFragment.super.getContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();

        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = "Teachers";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_teachers, container, false);

        addDeleteButton1 = v.findViewById(R.id.add_delete_teacher);
        addDeleteButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intn = new Intent(TeachersFragment.super.getContext(), TeacherStudentEditActivity.class);
                intn.putExtra("Dept Name", deptName);
                intn.putExtra("Category", category);
                startActivity(intn);
            }
        });

        ListView teachers = v.findViewById(R.id.teachers);
        teachers.setVisibility(View.VISIBLE);
        teachersList = new ArrayList<>();
//        Toast.makeText(TeachersFragment.super.getContext(), " teacher fragment= " + TeachersFragment.super.getContext(), Toast.LENGTH_SHORT).show();
        teacherAdapter = new ArrayAdapter<>(TeachersFragment.super.getContext(), R.layout.support_simple_spinner_dropdown_item, teachersList);

        teachers.setAdapter(teacherAdapter);
        Intent intent = getActivity().getIntent();

        Bundle b = intent.getExtras();

        if (b != null) {
            deptName = b.getString("Dept Name");

        }

        ReadTeachers();
        return v;
    }


    private void ReadTeachers() {
        if(deptName!=null){
            teacherDatabaseReference = FirebaseDatabase.getInstance().getReference("Teachers").child(deptName);
            teacherDatabaseReference.addValueEventListener(teacherValEvtListnr);
        }


//        deptRef2.addChildEventListener(childEventListener);

    }

}
