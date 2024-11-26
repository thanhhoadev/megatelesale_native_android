package com.example.duan1_customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.duan1_customer.fragment.ExploreFragment;
import com.example.duan1_customer.fragment.ProfileFragment;
import com.example.duan1_customer.fragment.HomeFragment;
import com.example.duan1_customer.model.ApiClient;
import com.example.duan1_customer.model.Customer;
import com.example.duan1_customer.model.Profile_User;
import com.example.duan1_customer.model.Request_Update_Minute_Tele;
import com.example.duan1_customer.model.Request_Update_Record_Cus;
import com.example.duan1_customer.model.Respone_UploadAudio;
import com.example.duan1_customer.model.Response_Update_Minute_Tele;
import com.example.duan1_customer.model.Response_Update_Record_Cus;
import com.example.duan1_customer.model.Response_User;
import com.example.duan1_customer.model.Result_UploadAudio;
import com.example.duan1_customer.model.ServiceAPI;
import com.example.duan1_customer.model.TokenManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    FragmentManager fragmentManager;
    Fragment fragment;
    FrameLayout fl_main;
    Spinner selectUser;
    SharedPreferences sharedPreferences;
    Profile_User userCurrent;
    Gson gson;
    ServiceAPI service;
    TokenManager tokenManager;
    private Customer customerTarget;
    private final Object lock = new Object();
    private final Object lockUser = new Object();
    private CallDetectionManager callDetectionManager;
    ArrayList<Profile_User> listLogin;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listLogin = new ArrayList<>();
        createListLogin();

        fl_main = findViewById(R.id.fl_main);
        selectUser = findViewById(R.id.selectUser);
        tokenManager = new TokenManager(MainActivity.this);
        callDetectionManager = new CallDetectionManager(this);
        gson = new Gson();

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        setUserTarget(gson.fromJson(sharedPreferences.getString("user", ""), Profile_User.class));

        service = ApiClient.getClient(MainActivity.this.getApplicationContext()).create(ServiceAPI.class);
        if (getUserTarget().get_id().equals("672344bb84df1a39767a57c8")){
            selectUser.setVisibility(View.VISIBLE);
        }else{
            selectUser.setVisibility(View.GONE);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.sample_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectUser.setAdapter(adapter);
//        customerTarget = new Customer();

        //ánh xạ
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottomNavigationView) ;
        fragmentManager = getSupportFragmentManager();
        fragment = new HomeFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_main, fragment)
                .commit();

        // click item bottomNavigation

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    default:
                    case R.id.menu_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.menu_explore:
                        fragment = new ExploreFragment();
                        break;
                    case R.id.menu_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_main, fragment)
                        .commit();
                return true;
            }
        });
        selectUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleLogin(listLogin.get(position).getEmail(),listLogin.get(position).getPassword());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì cả nếu không chọn
            }
        });
    }
    public void setCustomerTarget(Customer target) {
        synchronized (lock) {
            customerTarget = target;
        }
    }
    public Customer getCustomerTarget() {
        synchronized (lock) {
            return customerTarget;
        }
    }
    public void setUserTarget(Profile_User target) {
        synchronized (lockUser) {
            userCurrent = target;
        }
    }
    public Profile_User getUserTarget() {
        synchronized (lockUser) {
            return userCurrent;
        }
    }
    public void openCallAndListener(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + getCustomerTarget().getPhone()));
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callDetectionManager.startListener();
            startActivity(intent);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Log.d("CallDetection", "Listener started");
//                    callDetectionManager.startListener();
//                }
//            },5000);

        } else {
            Toast.makeText(MainActivity.this, "Vui lòng cấp quyền gọi điện", Toast.LENGTH_SHORT).show();
        }
    };
    public void reopenApp() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class); // Hoặc Activity bạn muốn mở lại
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainActivity.this.startActivity(intent);
        MainActivity.this.findAndUploadLatestRecording();
    }

    public void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, "content://media/external/audio/media");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }
    private String getFilePathFromUri(Uri uri) {
        String filePath = null;

        // Nếu là URI từ MediaStore (file trong máy)
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { MediaStore.MediaColumns.DATA };
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // Nếu là URI từ File
            filePath = uri.getPath();
        }

        return filePath;
    }
    private String copyFileToTemp(Uri uri) {
        File tempFile = new File(getExternalFilesDir(null), "tempFile.m4a");
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Log.e("FilePicker", "Error copying file to temp", e);
        }
        return tempFile.getAbsolutePath();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            Log.d("FilePicker", "File URI: " + fileUri);

            // Lấy đường dẫn file
            String filePath = getFilePathFromUri(fileUri);
            if (filePath == null) {
                // Nếu không có file path, sao chép file vào thư mục tạm
                filePath = copyFileToTemp(fileUri);
            }

            if (filePath != null) {
                Log.d("FilePicker", "Final File Path: " + filePath);

                // Upload file
                uploadFileToServer(filePath);
            } else {
                Log.e("FilePicker", "Unable to resolve file path");
            }
        }
    }
    private void uploadFileToServer(String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("audio/mpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("audio", file.getName(), requestBody);
        Call<Respone_UploadAudio> call = service.uploadAudio(body);
        call.enqueue(new Callback<Respone_UploadAudio>() {
            @Override
            public void onResponse(Call<Respone_UploadAudio> call, Response<Respone_UploadAudio> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateRecordCustomer(response.body().result.get(0));
                } else {
                    Toast.makeText(MainActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Respone_UploadAudio> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecordCustomer(Result_UploadAudio resultUploadAudio) {
        Request_Update_Record_Cus request = new Request_Update_Record_Cus(getCustomerTarget().get_id(), resultUploadAudio.url);
        Call<Response_Update_Record_Cus> call = service.updateRecordCustomer(request);
        call.enqueue(new Callback<Response_Update_Record_Cus>() {
            @Override
            public void onResponse(Call<Response_Update_Record_Cus> call, Response<Response_Update_Record_Cus> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateMinuteTele(resultUploadAudio);
                } else {
                    Toast.makeText(MainActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_Update_Record_Cus> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateMinuteTele(Result_UploadAudio resultUploadAudio) {
        Date date = new Date();

        // Định dạng thời gian
        String currentTime = formatter.format(date);
        Request_Update_Minute_Tele request = new Request_Update_Minute_Tele(getUserTarget().get_id(),resultUploadAudio.minutes,
                getCustomerTarget().getService().get(0), currentTime, false);
        Call<Response_Update_Minute_Tele> call = service.updateMinutesOfTelesale(request);
        call.enqueue(new Callback<Response_Update_Minute_Tele>() {
            @Override
            public void onResponse(Call<Response_Update_Minute_Tele> call, Response<Response_Update_Minute_Tele> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, "Ghi âm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ghi âm lỗi, tải thủ công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_Update_Minute_Tele> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateMissedTele(){
        Date date = new Date();

        // Định dạng thời gian
         // Định dạng: Năm-Tháng-Ngày Giờ:Phút:Giây
        String currentTime = formatter.format(date);
        Request_Update_Minute_Tele request = new Request_Update_Minute_Tele(getUserTarget().get_id(),0,
                getCustomerTarget().getService().get(0), currentTime, true);
        Call<Response_Update_Minute_Tele> call = service.updateMinutesOfTelesale(request);
        call.enqueue(new Callback<Response_Update_Minute_Tele>() {
            @Override
            public void onResponse(Call<Response_Update_Minute_Tele> call, Response<Response_Update_Minute_Tele> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, "Gọi nhỡ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_Update_Minute_Tele> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File findLatestRecording() {
        try {
            Log.d("Recording", "Đang tìm file ghi âm mới nhất...");

            String[] possiblePaths = {
                    "storage/emulated/0/Recordings/Record/Call",
                    "storage/emulated/0/Music/Recordings/Call Recordings",
                    "storage/emulated/0/Record/Call",
                    "storage/emulated/0/Recordings/Call",
                    "storage/emulated/0/Recordings",
                    "storage/emulated/0/Download",
            };

            File latestFile = null;
            long latestModified = 0;

            for (String path : possiblePaths) {
                File recordingDir = new File(path);
                if (recordingDir.exists() && recordingDir.isDirectory()) {
                    Log.d("Recording", "Thư mục tồn tại: " + path);
                    File[] files = recordingDir.listFiles();
                    if (files != null && files.length > 0) {
                        for (File file : files) {
                            if (file.isFile() && file.lastModified() > latestModified) {
                                latestModified = file.lastModified();
                                latestFile = file;
                            }
                        }
                    }
                    // Nếu tìm thấy file, kết thúc vòng lặp.
                    if (latestFile != null) {
                        break;
                    }
                } else {
                    Log.d("Recording", "Thư mục không tồn tại hoặc không hợp lệ: " + path);
                }
            }

            if (latestFile == null) {
                // Không tìm thấy file trong tất cả các thư mục
                Log.e("Recording", "Không tìm thấy file ghi âm mới nhất.");
                Toast.makeText(MainActivity.this, "Không tìm thấy file ghi âm mới nhất", Toast.LENGTH_SHORT).show();
            }
            return latestFile;

        } catch (Exception e) {
            Log.e("Recording", "Lỗi khi tìm file ghi âm mới nhất: " + e.getMessage(), e);
            Toast.makeText(MainActivity.this, "Lỗi khi tìm file ghi âm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    private void findAndUploadLatestRecording() {
        Thread thread = new Thread(() -> {
            try {

                File latestRecordingSkip1 = findLatestRecording();
                File latestRecordingSkip2 = findLatestRecording();
                File latestRecording = findLatestRecording();
                Log.d("Recording", "File ghi âm mới nhất: " + (latestRecording != null ? latestRecording.getAbsolutePath() : "Không tìm thấy"));
                if (latestRecording == null) {
                    Log.d("Recording", "Không tìm thấy file ghi âm mới nhất");
                    return; // Dừng tại đây nếu không có file.
                }
                long currentTime = System.currentTimeMillis();
                long fileLastModified = latestRecording.lastModified();
                long timeDifference = currentTime - fileLastModified;
                if (latestRecording.getName().contains(customerTarget.getPhone()) && timeDifference < 60000) {
                    uploadFileToServer(latestRecording.getAbsolutePath());
                    return;
                }
                updateMissedTele();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void handleLogin (String email,String pass) {
        Profile_User user = new Profile_User(email.trim(), pass.trim(), "name");

        ServiceAPI serviceAPI = ApiClient.getClient(getApplicationContext()).create(ServiceAPI.class);

        Call<Response_User> call = serviceAPI.checkLogin(user);
        call.enqueue(new Callback<Response_User>() {
            @Override
            public void onResponse(Call<Response_User> call, Response<Response_User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response_User responseUser = response.body();
                    if (responseUser.message.equals("Validation Error")) {
                        Toast.makeText(MainActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    } else {
                        String accessToken = responseUser.result.access_token;
                        new TokenManager(MainActivity.this).saveToken(accessToken);
                        setUserTarget(responseUser.result.user);

                        if(fragment instanceof  HomeFragment){
                            ((HomeFragment)fragment).listMyCustomers();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Response_User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
            }
        });
    };
    private void createListLogin(){
        listLogin.add(new Profile_User("telethuyvy@megakorea.vn","trial123aA@", "Admin"));
        listLogin.add(new Profile_User("phuongthao@megakorea.vn","TH@Mega029", "Phương Thảo"));
        listLogin.add(new Profile_User("tham@megakorea.vn", "TH@Mega029", "Thắm"));
        listLogin.add(new Profile_User("linh@megakorea.vn", "TH@Mega029", "Linh"));
        listLogin.add(new Profile_User("telequyen@megakorea.vn", "trial123aA@", "Quyên"));
        listLogin.add(new Profile_User("teletrinh@megakorea.vn", "trial123aA@", "Trinh"));
        listLogin.add(new Profile_User("teletran@megakorea.vn", "trial123aA@", "Trân"));
        listLogin.add(new Profile_User("tuongvy@megakorea.vn", "TV@Mega1", "Tường Vy"));
        listLogin.add(new Profile_User("mainhi@megakorea.vn", "MN@Mega26", "Mai Nhi"));
        listLogin.add(new Profile_User("ngoc@megakorea.vn","NG@Mega02", "Ngọc"));
        listLogin.add(new Profile_User("xinh@megakorea.vn", "XI@Mega065", "Xinh"));
        listLogin.add(new Profile_User("my@megakorea.vn", "M@Mega003", "My"));
        listLogin.add(new Profile_User("thuylinh@megakorea.vn", "TL@Mega026", "Thùy Linh"));
        listLogin.add(new Profile_User("thuy@megakorea.vn","TH@Mega029", "Thúy"));
    }
}