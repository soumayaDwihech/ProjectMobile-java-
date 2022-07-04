package com.example.camunda.UI;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camunda.Data.TaskAdapter;
import com.example.camunda.Domain.Task;
import com.example.camunda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class tasksfragment extends Fragment {
    private List<Task> mRecyclerViewItems = new ArrayList<>();
    private TaskAdapter mAdapter;
    private RecyclerView rv;
    private String USERNAME , PASSWORD;

    String UrlProcessList="http://digitalisi.tn:8080/engine-rest/filter/e4f3fa62-b6f5-11ec-b178-c3179e4f32a6/list?firstResult=0&maxResults=15";


    public tasksfragment() {
        // Required empty public constructor
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


        View view = inflater.inflate(R.layout.fragment_task, container, false);
        rv = (RecyclerView)view.findViewById(R.id.task_recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter    = new TaskAdapter(getContext(),mRecyclerViewItems,this,USERNAME,PASSWORD);
        String result;
        try {

            result = mAdapter.getTasks(UrlProcessList);
            System.out.println("task result"+result);
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
                String assignee = jo_inside.getString("assignee");
                String process = jo_inside.getString("processDefinitionId");
                String date = jo_inside.getString("created");
                int priority = jo_inside.getInt("priority");


                Task taskItem = new Task();
                taskItem.setId(id);
                taskItem.setName(name);
                taskItem.setAssignee(assignee);
                taskItem.setProcessDefinitionId(process);
                taskItem.setCreated(date);
                taskItem.setPriority(priority);
                mRecyclerViewItems.add(taskItem);
            }

            rv.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
