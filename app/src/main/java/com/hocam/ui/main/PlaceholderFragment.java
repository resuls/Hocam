package com.hocam.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hocam.R;
import com.hocam.models.Course;
import com.hocam.models.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment
{

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index)
    {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        pageViewModel.getmList().observe(this, new Observer<List>()
        {

            @Override
            public void onChanged(@Nullable List itemList)
            {
                if (itemList != null && !itemList.isEmpty())
                {
                    if (itemList.get(0) instanceof Course)
                    {
                        CoursesRecyclerViewAdapter adapter;
                        adapter = new CoursesRecyclerViewAdapter(root.getContext(), (ArrayList<Course>) itemList);
                        recyclerView.setAdapter(adapter);
                    }
                    else
                    {
                        DepartmentsRecyclerViewAdapter adapter;
                        adapter = new DepartmentsRecyclerViewAdapter(root.getContext(), (ArrayList<Department>) itemList);
                        recyclerView.setAdapter(adapter);
                    }
                }

            }

        });
        return root;
    }
}