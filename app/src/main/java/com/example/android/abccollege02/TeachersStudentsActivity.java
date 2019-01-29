package com.example.android.abccollege02;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class TeachersStudentsActivity extends AppCompatActivity {

    TabLayout teacherStudenttab;

    ViewPager teacherStudentviewPager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teachers_students);

        teacherStudenttab = findViewById(R.id.teachers_students_Tab);

        teacherStudentviewPager = findViewById(R.id.teachers_students_ViewPager);



        MyTeachersStudentsFragment tsFragment = new MyTeachersStudentsFragment();

        tsFragment.al1.add(new TeachersFragment());

        tsFragment.al1.add(new StudentsFragment());

        tsFragment.al2.add("Teachers");

        tsFragment.al2.add("Students");



        teacherStudentviewPager.setAdapter(tsFragment);

        teacherStudenttab.setupWithViewPager(teacherStudentviewPager);

//        teacherStudenttab.getTabAt(0).select();

        Intent intent = getIntent();
        int index = intent.getIntExtra("index",-1);
        TabLayout tab = findViewById(R.id.teachers_students_Tab);
        if(index!=-1)
            tab.getTabAt(index).select();
    }



    public class MyTeachersStudentsFragment extends FragmentPagerAdapter {

        ArrayList<Fragment> al1 = new ArrayList<>();

        ArrayList<String> al2 = new ArrayList<>();



        MyTeachersStudentsFragment() {

            super(getSupportFragmentManager());

        }



        @Override

        public Fragment getItem(int position) {

            return al1.get(position);

        }



        @Override

        public CharSequence getPageTitle(int position){

            return al2.get(position);

        }



        @Override

        public int getCount() {

            return al1.size();

        }

    }
}