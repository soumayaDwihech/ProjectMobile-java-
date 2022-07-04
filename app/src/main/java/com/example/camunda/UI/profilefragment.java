package com.example.camunda.UI;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.camunda.Data.DataAdapter;
import com.example.camunda.Domain.Profile;
import com.example.camunda.R;

public class profilefragment extends Fragment {



    private DataAdapter profileAdapter;
    private Profile profilelog;

    public profilefragment(Profile prof) {
        // Required empty public constructor
        this.profilelog=prof;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_profile, container, false);


        ((TextView)view.findViewById(R.id.username)).setText(profilelog.getFirstName()+" "+ profilelog.getLastName());
        ((TextView)view.findViewById(R.id.email)).setText(profilelog.getEmail());

        // Inflate the layout for this fragment
        return view;
    }




}
