package com.example.camunda.UI;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camunda.Data.ProcessAdapter;
import com.example.camunda.Domain.Process;
import com.example.camunda.Domain.Profile;
import com.example.camunda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class processesfragment extends Fragment {


    private List<Process> mRecyclerViewItems = new ArrayList<>();
    private ProcessAdapter mAdapter;
    private RecyclerView rv;
    private Profile profilelog;
    private String USERNAME , PASSWORD;

    String UrlProcessList="http://digitalisi.tn:8080/engine-rest/process-definition?latest=true&active=true&startableInTasklist=true&startablePermissionCheck=true&firstResult=0&maxResults=15";


    public processesfragment(Profile profile) {
        // Required empty public constructor
        this.profilelog=profile;
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
        USERNAME = getArguments().getString("username");
        PASSWORD = getArguments().getString("password");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_processes, container, false);
        rv = (RecyclerView)view.findViewById(R.id.process_recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter    = new ProcessAdapter(getContext(),mRecyclerViewItems,this,USERNAME,PASSWORD);
        String result;
        try {

            result = mAdapter.getProcesses(UrlProcessList);
            parsingData(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rv.setAdapter(mAdapter);
        return view;
    }



    public void parsingData(String jsonData)
    {

        try {
            //JSONObject obj = new JSONObject(jsonData);
            JSONArray m_jArry = new JSONArray(jsonData);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String id = jo_inside.getString("id");
                String name = jo_inside.getString("name");

                Process processItem = new Process();
                processItem.setId(id);
                processItem.setName(name);
                mRecyclerViewItems.add(processItem);
            }

            rv.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
