package com.hocam.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hocam.models.Course;
import com.hocam.models.Department;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel
{
    private ArrayList<Course> courseList = new ArrayList<>(); //temp data
    private ArrayList<Department> deptList = new ArrayList<>(); //temp data
    private DatabaseReference mDatabase;

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<List> mList = Transformations.map(mIndex, new Function<Integer, List>()
    {
        @Override
        public List apply(Integer input)
        {
            return setList(input);
        }
    });

    private List setList(Integer input)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("departments");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                deptList.clear();
                for (DataSnapshot department : dataSnapshot.getChildren()) {
                    if (department.getKey() == "CTIS") {

                        for (DataSnapshot course : department.getChildren()) {
                            courseList.add(new Course(course.child("name").toString(), course.getKey(), Double.parseDouble(course.child("avg").toString())));
                        }

                    }
                    deptList.add(new Department(department.child("name").toString(), department.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        if (input == 1)
//        {
//            courseList.clear();
//            courseList.add(new Course("OOP", "CTIS221", 8.5));
//            courseList.add(new Course("C", "CTIS151", 9));
//            courseList.add(new Course("Web", "CTIS255", 10));
//            return courseList;
//        }
//        else
//        {
//            deptList.clear();
//            deptList.add(new Department("Computer Tech", "CTIS"));
//            deptList.add(new Department("Economics", "ECON"));
//            deptList.add(new Department("Mathematics", "MATH"));
//            return deptList;
//        }
        if (input == 1) {
            return courseList;
        }
        else {
            return deptList;
        }
    }

    public void setIndex(int index)
    {
        mIndex.setValue(index);
    }

    public LiveData<List> getmList()
    {
        return mList;
    }
}