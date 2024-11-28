package com.example.duan1_customer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_customer.HistoryCustomerManager;
import com.example.duan1_customer.R;
import com.example.duan1_customer.adapter.CustomersAdapter;
import com.example.duan1_customer.model.ApiClient;
import com.example.duan1_customer.model.Customer;
import com.example.duan1_customer.model.CustomerFilterRequestType;
import com.example.duan1_customer.model.Request;
import com.example.duan1_customer.model.Response_GetCustomer_ByTelesale;
import com.example.duan1_customer.model.Response_SearchCus;
import com.example.duan1_customer.model.ServiceAPI;
import com.example.duan1_customer.model.TokenManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment {

    RecyclerView rcViewMyCus;
    SearchView svSearch;
    ImageView ivRefresh;
    TokenManager tokenManager;
    TextView nameScreen;
    ArrayList<Customer> customers;
    CustomersAdapter customersAdapter;
    LinearLayoutManager linearLayoutManager;
    ServiceAPI service;
    CustomerFilterRequestType customerFilterRequestType;

    // Thêm Handler để xử lý setTimeout
    private Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    HistoryCustomerManager manager;
    public ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcViewMyCus = view.findViewById(R.id.rcViewMyCus);
        svSearch = view.findViewById(R.id.svSearch);
        ivRefresh = view.findViewById(R.id.ivRefresh);
        nameScreen = view.findViewById(R.id.tvNameScreen);
        progressBar = view.findViewById(R.id.progressBar);
        tokenManager = new TokenManager(getContext());
        nameScreen.setText("Khách thả nổi");
        customers = new ArrayList<>();

        service = ApiClient.getClient(getActivity().getApplicationContext()).create(ServiceAPI.class);
        customerFilterRequestType = new CustomerFilterRequestType();
        manager = HistoryCustomerManager.getInstance(getContext());

        listMyCustomers();

        ivRefresh.setOnClickListener(view1 -> listMyCustomers());

        svSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svSearch.setIconified(false);
            }
        });

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCusInList(newText);
                return true;
            }
        });


        return view;
    }
    public void addHistoryCall(Customer customer){
        manager.addCustomer(customer);
    }

    public void listMyCustomers() {
        Call<Response_GetCustomer_ByTelesale> call = service.getCustomerexpried(1, 100, customerFilterRequestType);
        call.enqueue(new Callback<Response_GetCustomer_ByTelesale>() {
            @Override
            public void onResponse(Call<Response_GetCustomer_ByTelesale> call, Response<Response_GetCustomer_ByTelesale> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    if (response.body().result.customers.size() > 0 && customers.size() > 0 && response.body().result.customers.get(0) != customers.get(0)) return;
                    customers = response.body().result.customers;
                    if(manager.getHistoryCustomerList().size() > 0){
                        Customer lastHistoryCustomer = (manager.getHistoryCustomerList()).get(manager.getHistoryCustomerList().size() - 1);
                        Collections.sort(customers, new Comparator<Customer>() {
                            @Override
                            public int compare(Customer c1, Customer c2) {
                                if (c1.equals(lastHistoryCustomer)) {
                                    return -1; // Đưa target lên đầu
                                } else if (c2.equals(lastHistoryCustomer)) {
                                    return 1;  // Đưa target lên đầu
                                }
                                return 0; // Giữ nguyên thứ tự cho các phần tử khác
                            }
                        });
                    }
                    customersAdapter = new CustomersAdapter(getContext(), customers);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    rcViewMyCus.setLayoutManager(linearLayoutManager);
                    rcViewMyCus.setAdapter(customersAdapter);
                } else {
                    Toast.makeText(getActivity(), "Không lấy được thông tin người dùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_GetCustomer_ByTelesale> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchCusInList(String phoneNumber) {
        ArrayList<Customer> filteredCustomers = new ArrayList<>();
        if (phoneNumber != null && phoneNumber.length() ==0) {
            listMyCustomers();
            return;
        }
        for (Customer customer : customers) {
            if (customer.getPhone().contains(phoneNumber.trim())) {
                filteredCustomers.add(customer);
            }
        }
        customersAdapter = new CustomersAdapter(getContext(), filteredCustomers);
        rcViewMyCus.setAdapter(customersAdapter);
        if (filteredCustomers.size() == 0) {
            if (searchRunnable != null) {
                searchHandler.removeCallbacks(searchRunnable); // Hủy các callback trước đó
            }
            searchRunnable = () -> searchCustomers(phoneNumber); // Chạy tìm kiếm sau 1 giây
            searchHandler.postDelayed(searchRunnable, 500);
        }
    }
    private void searchCustomers(String phoneNumber) {
        // Thêm logic filter theo số điện thoại vào body request
        Call<Response_SearchCus> call = service.searchPhoneByExpried(new Request(phoneNumber));
        call.enqueue(new Callback<Response_SearchCus>() {
            @Override
            public void onResponse(Call<Response_SearchCus> call, Response<Response_SearchCus> response) {
                if (response.isSuccessful() && response.body() != null) {
                    customers = response.body().result;
                    customersAdapter = new CustomersAdapter(getContext(), customers);
                    rcViewMyCus.setAdapter(customersAdapter);
                } else {
                    Toast.makeText(getActivity(), "Không tìm thấy khách hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_SearchCus> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi kết nối khi tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchHandler.removeCallbacksAndMessages(null); // Hủy tất cả các callback
    }
}

