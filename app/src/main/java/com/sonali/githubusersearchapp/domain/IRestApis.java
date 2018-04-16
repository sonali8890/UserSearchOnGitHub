package com.sonali.githubusersearchapp.domain;

import com.sonali.githubusersearchapp.domain.model.UserModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sonali
 */

/**
 * Created by Sonali
 */
public interface IRestApis {

    @GET("search/users")
    Observable<UserModel> searchGithubUser(@Query("q") String userName, @Query("sort") String sort,
             @Query("order") String order);




}
