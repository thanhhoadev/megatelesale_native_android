package com.example.duan1_customer.model;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ServiceAPI {
    //api login
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Call<Response_User> checkLogin(@Body Profile_User user);

    //api get customer by telesale
    @Headers("Content-Type: application/json")
    @POST("customers/all-customer-of-telesale")
    Call<Response_GetCustomer_ByTelesale> getCustomerByTelesale(
            @Query("page") int page,
            @Query("limit") int limit,
            @Body CustomerFilterRequestType filter
    );
    // api get customer by telesale expired
    @Headers("Content-Type: application/json")
    @POST("customers/all-customer-expried")
    Call<Response_GetCustomer_ByTelesale> getCustomerexpried(
            @Query("page") int page,
            @Query("limit") int limit,
            @Body CustomerFilterRequestType filter
    );
    // api get all customer
    @Headers("Content-Type: application/json")
    @POST("customers/all")
    Call<Response_GetCustomer_ByTelesale> getAllCustomer(
            @Query("page") int page,
            @Query("limit") int limit
    );

    // api get all telesale
    @Headers("Content-Type: application/json")
    @GET("users/all-telesale")
    Call<Respone_GetAllTele> getAllTele();

    // api search customer by phone and tele
    @Headers("Content-Type: application/json")
    @POST("/search/by-phone-telesale")
    Call<Response_SearchCus> searchPhoneByTele(@Body Request phone);

    // api search customer by phone expried
    @Headers("Content-Type: application/json")
    @POST("/search/by-phone-expried")
    Call<Response_SearchCus> searchPhoneByExpried(@Body Request phone);

    // api search customer by phone
    @Headers("Content-Type: application/json")
    @POST("/search/by-phone")
    Call<Response_SearchCus> searchPhone(@Body Request phone);

    // api upload audio
    @Multipart
    @POST("/upload")
    Call<Respone_UploadAudio> uploadAudio(@Part MultipartBody.Part file);

    // api update record customer
    @Headers("Content-Type: application/json")
    @POST("/customers/update-record")
    Call<Response_Update_Record_Cus> updateRecordCustomer(@Body Request_Update_Record_Cus payload);

    // api update minutes and missed call of telesale
    @Headers("Content-Type: application/json")
    @POST("/minutes")
    Call<Response_Update_Minute_Tele> updateMinutesOfTelesale(@Body Request_Update_Minute_Tele request);
}
