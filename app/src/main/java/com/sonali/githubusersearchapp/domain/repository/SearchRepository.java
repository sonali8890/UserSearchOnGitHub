package com.sonali.githubusersearchapp.domain.repository;


import com.sonali.githubusersearchapp.domain.IRestApis;
import com.sonali.githubusersearchapp.domain.NetworkModule;
import com.sonali.githubusersearchapp.domain.model.UserModel;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by Sonali
 */
public class SearchRepository implements ISearchRepository{

    Retrofit retrofit;

    public SearchRepository(){
        retrofit = NetworkModule.getInstance().provideBaseRetrofit();
    }

    @Override
    public Observable<UserModel> SearchUserByName(String name) {
        IRestApis masterServiceMethods = retrofit.create(IRestApis.class);
        return masterServiceMethods.searchGithubUser(name, "followers", "desc");
    }


}
