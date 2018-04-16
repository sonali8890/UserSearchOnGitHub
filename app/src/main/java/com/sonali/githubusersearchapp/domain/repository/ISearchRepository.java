package com.sonali.githubusersearchapp.domain.repository;


import com.sonali.githubusersearchapp.domain.model.UserModel;

import io.reactivex.Observable;

/**
 * Created by Sonali
 */
public interface ISearchRepository {

    Observable<UserModel> SearchUserByName(String name);

}
