package com.sonali.githubusersearchapp.view;

/**
 * Created by Sonali
 */
public interface BaseView {

    void attachPresentor(ISearchPresentor p);
    void displayError(String error);
}
