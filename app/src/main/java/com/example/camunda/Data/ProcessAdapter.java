package com.example.camunda.Data;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camunda.Domain.ItemClickListener;
import com.example.camunda.Domain.Process;
import com.example.camunda.R;
import com.example.camunda.UI.formfragment;
import com.example.camunda.UI.processesfragment;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;

public class ProcessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    OkHttpClient client = new OkHttpClient();

    //create variable
    private final List<Process> recyclerViewItems; //mete3 liste de process
    private final Context mContext; //l'activité ili a7ena taw feha
    processesfragment fragmentone;
    private ItemClickListener clickListener;
    DataAdapter conx;
    private String username, password;

    //generate constructor
    public ProcessAdapter(Context context, List<Process> recyclerViewItems, processesfragment fragmentone, String user,String pass) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.fragmentone = fragmentone;
        this.username = user;
        this.password = pass;
    }


    public String getProcesses(String url) throws IOException
    {
        String result = null;
        try {
            conx =new DataAdapter();
             result = conx.getData(url,username,password);


        } catch (IOException e) {
            e.printStackTrace();
        }
            client.newBuilder()  //creation de nouveau client
                    .build();

            return result;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_process,viewGroup, false);
        return new MenuItemViewHolder(itemLayoutView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final Process fp = (Process) recyclerViewItems.get(position);

        menuItemHolder.name.setText(fp.getName());

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
        public TextView name;
        public CardView cardView;

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name      = (TextView) itemLayoutView.findViewById(R.id.process_name);
            cardView  = (CardView) itemLayoutView.findViewById(R.id.process_card);
            itemLayoutView.setTag(itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {  //tejibeli le fragment ili fi wessetou liste des process

            int position = this.getAdapterPosition();
            final Process proc = recyclerViewItems.get(position);
            System.out.println("CLICKED ON :" +proc);
            formfragment temp;
            temp=new formfragment();
            Bundle bundle = new Bundle();
            bundle.putString("idprocess", proc.getId());
            bundle.putString("username",username);
            bundle.putString("password",password);
            temp.setArguments(bundle);
            fragmentone.getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.Layout_container, temp, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

        }
    }

}