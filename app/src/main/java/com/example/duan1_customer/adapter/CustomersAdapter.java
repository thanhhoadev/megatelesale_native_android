package com.example.duan1_customer.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_customer.MainActivity;
import com.example.duan1_customer.R;
import com.example.duan1_customer.model.Customer;

import java.util.ArrayList;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Customer> list;

    public CustomersAdapter(Context context, ArrayList<Customer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_customer,parent,false);

        return new CustomersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomersAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvService.setText(list.get(position).getService().get(0));
        holder.tvPhoneNumber.setText(list.get(position).getPhone());

        if (list.get(position).getRecord().size() > 0) {
            holder.tvPhoneNumber.setTextColor(context.getResources().getColor(R.color.yellow)); // Màu khi điều kiện đúng
        } else {
            holder.tvPhoneNumber.setTextColor(context.getResources().getColor(R.color.black)); // Màu mặc định
        }
        holder.btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).setCustomerTarget(list.get(position));
                ((MainActivity)context).openFilePicker();
            };
        });
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).setCustomerTarget(list.get(position));
                ((MainActivity)context).openCallAndListener();
            };
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvService, tvPhoneNumber;
        AppCompatImageButton btnUploadFile, btnCall;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvService = itemView.findViewById(R.id.tvService);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            btnUploadFile = itemView.findViewById(R.id.btnUploadFile);
            btnCall = itemView.findViewById(R.id.btnCall);
        }
    }
}
