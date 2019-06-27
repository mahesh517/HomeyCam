package com.app.homeycam.ServiceApi;


import com.app.homeycam.ModelClass.AddGuestApi.AddGuest;
import com.app.homeycam.ModelClass.GuestUserApi.GuestUsers;
import com.app.homeycam.ModelClass.Login.Login;
import com.app.homeycam.ModelClass.Register.Register;
import com.app.homeycam.ModelClass.UserDevices.UserDevices;
import com.app.homeycam.Rawheaders.AddGuestUser.AddGuestUserData;
import com.app.homeycam.Rawheaders.Guest.GuestData;
import com.app.homeycam.Rawheaders.Login.LoginData;
import com.app.homeycam.Rawheaders.Register.RegisterData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("auth/login")
    Call<Login> loginonCall(@Body LoginData loginData);

    @POST("auth/register")
    Call<Register> userRegisteronCall(@Body RegisterData registerData);

    @POST("product/connectedUsersWithProduct")
    Call<GuestUsers> getGuestUsers(@Header("x-access-token") String token, @Body GuestData guestData);

    @POST("/product/productAccessCreation")
    Call<AddGuest> addNewGuest(@Header("x-access-token") String token, @Body AddGuestUserData guestData);

    @GET("product/connectedDevicesForUser/{user_id}")
    Call<UserDevices>  getConnectedDevices(@Header("x-access-token") String token, @Path("user_id") String _id);


}
