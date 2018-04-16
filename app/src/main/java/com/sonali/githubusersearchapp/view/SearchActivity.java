package com.sonali.githubusersearchapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sonali.githubusersearchapp.R;
import com.sonali.githubusersearchapp.databinding.ActivitySearchBinding;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by Sonali
 */
public class SearchActivity extends AppCompatActivity implements ISearchPresentor.ISearchView {

    private SearchPresentorImpl mPresentor;
    private ActivitySearchBinding mBinder;
    private SearchResultAdapter mAdapter;
    private PublishSubject<String> mObserver = PublishSubject.create();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_search);

        new SearchPresentorImpl(this);

        mBinder.userListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchResultAdapter(this, mPresentor.getSearchResult());
        mBinder.userListRecyclerView.setAdapter(mAdapter);
        mBinder.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("Log", "onQueryTextSubmit: ");
                mObserver.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mObserver.onNext(newText);
                return true;
            }
        });
    }

    @Override
    public void attachPresentor(ISearchPresentor p) {
        mPresentor = (SearchPresentorImpl) p;
        mPresentor.init();
    }

    @Override
    public void displayError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public PublishSubject<String> getSubject() {
        return mObserver;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresentor.onDetach();
    }
}
