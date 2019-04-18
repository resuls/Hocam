package com.hocam.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.hocam.models.Course;
import com.hocam.models.Department;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {
    private ArrayList<Course> courseList = new ArrayList<>(); //temp data
    private ArrayList<Department> deptList = new ArrayList<>(); //temp data

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<List> mList = Transformations.map(mIndex, new Function<Integer, List>() {
        @Override
        public List apply(Integer input) {
            return setList(input);
        }
    });

    private List setList(Integer input) {
        if (input == 1) {
            courseList.clear();
            courseList.add(new Course("OOP", "CTIS221", 8.5));
            courseList.add(new Course("C", "CTIS151", 9));
            courseList.add(new Course("Web", "CTIS255", 10));
            return courseList;
        }
        else {
            deptList.clear();
            deptList.add(new Department("Computer Tech", "CTIS"));
            deptList.add(new Department("Economics", "ECON"));
            deptList.add(new Department("Mathematics", "MATH"));
            return deptList;
        }
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<List> getmList() {
        return mList;
    }
}