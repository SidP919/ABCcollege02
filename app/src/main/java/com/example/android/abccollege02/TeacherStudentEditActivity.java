package com.example.android.abccollege02;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherStudentEditActivity extends AppCompatActivity {

    EditText ed1, ed2;

    String s, s1, deptName, categoryName;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_edit);

        Intent intn = getIntent();
        deptName = intn.getStringExtra("Dept Name");
        categoryName = intn.getStringExtra("Category");


        Toast.makeText(this, "Dept Name: " + deptName + " and Category: " + categoryName, Toast.LENGTH_SHORT).show();

        ed1 = findViewById(R.id.editText6);

        ed2 = findViewById(R.id.editText7);

    }



    public void addTeacher(View view) {

        s1 = ed1.getText().toString();

        s = ed2.getText().toString();
        if(categoryName.equalsIgnoreCase("Teachers")){
            DatabaseReference dbRef = (FirebaseDatabase.getInstance()).getReference("Teachers").child(deptName);

            Teachers t = new Teachers(s1, s, deptName);

            dbRef.child(s1).setValue(t);


            dbRef.child(s1).addValueEventListener(new ValueEventListener() {


                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Toast.makeText(TeacherStudentEditActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TeacherStudentEditActivity.this, TeachersStudentsActivity.class);
                    intent.putExtra("index",0);
                    startActivity(intent);

                }


                @Override

                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(TeacherStudentEditActivity.this, "Unable to Register", Toast.LENGTH_LONG).show();

                }

            });
        }else if(categoryName.equalsIgnoreCase("Students")) {
            DatabaseReference dbRef2 = (FirebaseDatabase.getInstance()).getReference("Students").child(deptName);
            Students t = new Students(s1, s, deptName);

            dbRef2.child(s1).setValue(t);

            dbRef2.child(s1).addValueEventListener(new ValueEventListener() {


                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Toast.makeText(TeacherStudentEditActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TeacherStudentEditActivity.this, TeachersStudentsActivity.class);
                    intent.putExtra("index",1);
                    startActivity(intent);

                }


                @Override

                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(TeacherStudentEditActivity.this, "Unable to Register", Toast.LENGTH_LONG).show();

                }

            });
        }

    }



    public void deleteTeacher(View view) {



        s1 = ed1.getText().toString();

        s = ed2.getText().toString();
        if(categoryName.equalsIgnoreCase("Teachers")){
            databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");
        }else  if(categoryName.equalsIgnoreCase("Students")){
            databaseReference = FirebaseDatabase.getInstance().getReference("Students");
        }
        DatabaseReference ref = databaseReference.child(deptName);

        DatabaseReference ref2 = ref.child(s1);

        ref2.removeValue();

        Intent intent = new Intent(this, TeachersStudentsActivity.class);

        startActivity(intent);

    }

}