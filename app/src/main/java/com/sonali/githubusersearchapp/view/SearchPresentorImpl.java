package com.sonali.githubusersearchapp.view;

import com.sonali.githubusersearchapp.domain.ApiCallbackWrapper;
import com.sonali.githubusersearchapp.domain.model.UserModel;
import com.sonali.githubusersearchapp.domain.repository.SearchRepository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sonali
 */
public class SearchPresentorImpl implements ISearchPresentor {

    private ISearchView mView;
    private List<UserModel.Item> searchResult = new ArrayList<>();
    private CompositeDisposable mDisposable;
    private SearchRepository repo;

    public SearchPresentorImpl(@NotNull ISearchView view) {
        mView = view;
        mDisposable = new CompositeDisposable();
        repo = new SearchRepository();
        mView.attachPresentor(this);

    }


    @Override
    public void init() {
        mDisposable.add(mView.getSubject()
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String text) throws Exception {
                        if (text.isEmpty()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                })
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<UserModel>>() {
                    @Override
                    public ObservableSource<UserModel> apply(String s) throws Exception {
                        return repo.SearchUserByName(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallbackWrapper<UserModel>(mView) {
                    @Override
                    protected void onSuccess(UserModel userModel) {
                        searchResult.clear();
                        if (userModel.getItems() != null && !userModel.getItems().isEmpty()) {
                            searchResult.addAll(userModel.getItems());
                        }
                        mView.refreshAdapter();
                    }
                }));
    }

    public List<UserModel.Item> getSearchResult() {
        return searchResult;
    }

    @Override
    public void onDetach() {
        if (mDisposable != null)
            mDisposable.dispose();
    }


}
