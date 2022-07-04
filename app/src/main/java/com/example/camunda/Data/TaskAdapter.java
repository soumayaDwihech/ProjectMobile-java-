package com.example.camunda.Data;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camunda.Domain.ItemClickListener;
import com.example.camunda.Domain.Task;
import com.example.camunda.R;
import com.example.camunda.UI.tasksfragment;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    OkHttpClient client = new OkHttpClient();

    //create variable
    private final List<Task> recyclerViewItems;
    private final Context mContext;
    tasksfragment fragmentone;
    private ItemClickListener clickListener;
    DataAdapter conx;
    private String username, password;

    //generate constructor
    public TaskAdapter(Context context, List<Task> recyclerViewItems, tasksfragment fragmentone, String user, String pass) {
        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.fragmentone = fragmentone;
        this.username = user;
        this.password = pass;
    }

    public String getTasks(String url) throws IOException
    {
        String result = null;
        try {
            conx =new DataAdapter();
             result = conx.getData(url,username,password);

        } catch (IOException e) {
            e.printStackTrace();
        }
            client.newBuilder()
                    .build();

            return result;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item,viewGroup, false);
        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final Task fp = (Task) recyclerViewItems.get(position);

        menuItemHolder.nameTask.setText(fp.getName());
        menuItemHolder.assignee.setText(fp.getAssignee());
        menuItemHolder.taskprocesname.setText(fp.getProcessDefinitionId());
        menuItemHolder.taskpriority.setText(""+fp.getPriority());
        menuItemHolder.taskdate.setText(fp.getCreated());

    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

    public void setClickListener(ItemClickListener itemClickListener)
    {
    this.clickListener = itemClickListener;
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTask;
        public TextView taskprocesname;
        public TextView assignee;
        public TextView taskdate;
        public TextView taskpriority;
        public ConstraintLayout taskitemlayout;


        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            nameTask = (TextView) itemLayoutView.findViewById(R.id.task_name);
            taskprocesname = (TextView) itemLayoutView.findViewById(R.id.taskprocesname);
            taskdate = (TextView) itemLayoutView.findViewById(R.id.taskdate);
            taskpriority = (TextView) itemLayoutView.findViewById(R.id.taskpriority);
            assignee = (TextView) itemLayoutView.findViewById(R.id.assignee);
            taskitemlayout = (ConstraintLayout) itemLayoutView.findViewById(R.id.tasklayoutitem);
            itemLayoutView.setTag(itemView);
            itemView.setOnClickListener(this);


        }



        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            final Task T = recyclerViewItems.get(position);
            System.out.println("CLICKED ON :" +T);
            Bundle bundle = new Bundle();
            bundle.putString("idTask", T.getId());
            bundle.putString("username",username);
            bundle.putString("password",username);

            fragmentone.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .commit();

        }
    }

}