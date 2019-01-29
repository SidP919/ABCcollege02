package com.example.android.abccollege02;

public class Students {
    String sName;

    String sPost;

    String deptName;

    public Students(String sName, String sPost, String deptName) {
        this.sName = sName;
        this.sPost = sPost;
        this.deptName = deptName;
    }

    public Students() {
    }

    public String getsName() {
        return sName;
    }

    public String getsPost() {
        return sPost;
    }

    public String getDeptName() {
        return deptName;
    }
}
