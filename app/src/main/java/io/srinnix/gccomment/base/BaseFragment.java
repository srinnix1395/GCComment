package io.srinnix.gccomment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseFragment<VB extends ViewBinding, VM extends BaseViewModel> extends Fragment {

    protected VB viewBinding;

    protected VM viewModel;

    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = inflateViewBinding(inflater, container);
        viewModel = createViewModel();
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        bindAction();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    protected void bindView() {

    }

    protected void bindAction() {

    }

    protected abstract VB inflateViewBinding(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container);

    protected abstract VM createViewModel();
}
