package com.hcidev.hciv2.restapi;

import com.hcidev.hciv2.model.Follows;
import com.hcidev.hciv2.model.Note;
import com.hcidev.hciv2.model.Notecount;
import com.hcidev.hciv2.model.Subscribefollow;
import com.hcidev.hciv2.model.Users;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Apiservice {

    @GET("notebyuser/{username}")
    Call<ArrayList<Note>> getNote(@Path("username") String note_username);

    @GET("profile/{username}")
    Call<List<Users>> getProfile(@Path("username")String user_username);

    @GET("followercount/{username}")
    Call<List<Follows>> getFollowercount(@Path("username") String x);

    @GET("followingcount/{username}")
    Call<List<Follows>> getFollowingcount(@Path("username") String b);

    @GET("alluser/{username}")
    Call<ArrayList<Users>> getalluser(@Path("username") String path);

    @GET("isfollowing/{username}")
    Call<ArrayList<Subscribefollow>> getFollowing(@Path("username") String z);

    @GET("isfollower/{username}")
    Call<ArrayList<Subscribefollow>> getFollowwer(@Path("username") String k);

    @GET("isrequest/{username}")
    Call<ArrayList<Subscribefollow>> getRequest(@Path("username") String r);

    @GET("notecount/{username}")
    Call<List<Notecount>> getNotecount(@Path("username") String note);

    @Multipart
    @POST("uploadnote")
    Call<Note> uploadNote(@Part MultipartBody.Part note_link,
                           @Part("title") RequestBody note_title,
                           @Part("content") RequestBody note_content,
                           @Part("username")RequestBody note_username);

    @Multipart
    @POST("updateprofile/{username}")
    Call<Users> updateProfile(@Part MultipartBody.Part user_pic,
                          @Part("userfirstname") RequestBody user_firstname,
                          @Part("useremail") RequestBody user_email,
                          @Part("usercaption")RequestBody user_caption,
                             @Path("username") String u);


    @GET("verify/{username}/{password}")
    Call<Users> Login (@Path("username") String user_username, @Path("password") String user_password);


    @FormUrlEncoded
    @POST("users")
    Call<Users> regUsers(
                         @Field("username") String user_username,
                                @Field("email")String user_email,
                                @Field("password")String user_password,
                         @Field("firstname") String user_firstname,
                          @Field("userpic") String user_pic);


    @FormUrlEncoded
    @POST("follow")
    Call<Follows>setFollow (@Field("following") String hci_following,
                           @Field("follower") String hci_follower,
                           @Field("status") String status);

    @FormUrlEncoded
    @POST("setapprove/{fid}")
    Call<Follows>setApprove(@Field("status") String status,@Path("fid") int ids);

    @FormUrlEncoded
    @POST("notedelete")
    Call<Note> setDelete (@Field("noteid") int noteid);




}
