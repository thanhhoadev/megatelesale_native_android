package com.example.duan1_customer;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.duan1_customer.model.Customer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryCustomerManager {
    private static final String PREF_NAME = "history_customer_pref";
    private static final String KEY_HISTORY_LIST = "history_list";
    private static final int MAX_ITEMS = 100;

    private static HistoryCustomerManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private List<Customer> historyCustomerList;

    // Private constructor for Singleton
    private HistoryCustomerManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        loadHistoryList();
    }

    // Get Singleton instance
    public static synchronized HistoryCustomerManager getInstance(Context context) {
        if (instance == null) {
            instance = new HistoryCustomerManager(context.getApplicationContext());
        }
        return instance;
    }

    // Load history list from SharedPreferences
    private void loadHistoryList() {
        String json = sharedPreferences.getString(KEY_HISTORY_LIST, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Customer>>() {}.getType();
            historyCustomerList = gson.fromJson(json, type);
        } else {
            historyCustomerList = new ArrayList<>();
        }
    }

    // Save history list to SharedPreferences
    private void saveHistoryList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(historyCustomerList);
        editor.putString(KEY_HISTORY_LIST, json);
        editor.apply();
    }

    // Add a new customer to the history list
    public void addCustomer(Customer customer) {
        if (historyCustomerList.contains(customer)) {
            // Remove the existing item if it already exists
            historyCustomerList.remove(customer);
        } else if (historyCustomerList.size() >= MAX_ITEMS) {
            // Remove the oldest item (index 0) if the list is full
            historyCustomerList.remove(0);
        }
        // Add the new customer to the end of the list
        historyCustomerList.add(customer);
        saveHistoryList();
    }

    // Get the current history list
    public List<Customer> getHistoryCustomerList() {
        return new ArrayList<>(historyCustomerList); // Return a copy to avoid direct modification
    }

    // Clear the history list
    public void clearHistory() {
        historyCustomerList.clear();
        saveHistoryList();
    }
}
