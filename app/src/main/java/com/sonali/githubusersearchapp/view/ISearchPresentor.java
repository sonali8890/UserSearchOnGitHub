package com.sonali.githubusersearchapp.view;

import com.sonali.githubusersearchapp.domain.model.UserModel;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by Sonali
 */
public interface ISearchPresentor {

    interface ISearchView extends BaseView {

        void refreshAdapter();
        PublishSubject<String> getSubject();

    }

    void init();
    void onDetach();
    List<UserModel.Item> getSearchResult();

}
