package com.example.duan1_customer.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.duan1_customer.LoginActivity;
import com.example.duan1_customer.MainActivity;
import com.example.duan1_customer.R;

public class ProfileFragment extends Fragment {
    LinearLayout btnLogOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user");
                editor.apply();
                ((MainActivity)getContext()).finish();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                ((MainActivity)getContext()).startActivity(intent);
            }
        });

        return view;
    }
}