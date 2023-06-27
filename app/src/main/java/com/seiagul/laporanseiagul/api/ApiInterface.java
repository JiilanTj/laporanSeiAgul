package com.seiagul.laporanseiagul.api;

import com.seiagul.laporanseiagul.model.ChangePasswordResponse;
import com.seiagul.laporanseiagul.model.UploadResponse;
import com.seiagul.laporanseiagul.model.login.Login;
import com.seiagul.laporanseiagul.model.register.Register;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {


    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerResponse(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("change_password.php")
    Call<ChangePasswordResponse> changePasswordResponse(
            @Field("email") String email,
            @Field("old_password") String oldPassword,
            @Field("new_password") String newPassword
    );





}
