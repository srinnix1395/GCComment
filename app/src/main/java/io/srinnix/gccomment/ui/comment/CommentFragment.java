package io.srinnix.gccomment.ui.comment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.srinnix.gccomment.R;
import io.srinnix.gccomment.base.BaseFragment;
import io.srinnix.gccomment.data.model.Comment;
import io.srinnix.gccomment.databinding.FragmentCommentBinding;
import io.srinnix.gccomment.ui.comment.adapter.CommentAdapter;
import io.srinnix.gccomment.utils.ContextUtils;
import io.srinnix.gccomment.widget.TextCommentView;
import io.srinnix.gccomment.widget.recyclerview.itemdecoration.CommentItemDecoration;

public class CommentFragment extends BaseFragment<FragmentCommentBinding, CommentViewModel> {

    public static final String TAG = "CommentFragment";

    private CommentAdapter commentAdapter;

    @Override
    protected FragmentCommentBinding inflateViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentCommentBinding.inflate(inflater, container, false);
    }

    @Override
    protected CommentViewModel createViewModel() {
        return new ViewModelProvider(getActivity()).get(CommentViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void bindView() {
        viewBinding.toolbar.setTitle(R.string.comment_title);
        viewBinding.toolbar.setNavigationOnClickListener(view -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        commentAdapter = new CommentAdapter(getContext(),
                new CommentAdapter.OnClickCommentListener() {
                    @Override
                    public void onClick(int position) {
                        viewModel.action(CommentViewModel.UiAction.ClickComment.create(position));
                    }
                },
                new TextCommentView.OnClickContentListener() {
                    @Override
                    public void onClickTagListener(String tag) {

                    }

                    @Override
                    public void onClickUrlListener(String url) {
                        ContextUtils.openUrl(getActivity(), url);
                    }
                });
        viewBinding.rvComment.setAdapter(commentAdapter);
        CommentItemDecoration itemDecoration = new CommentItemDecoration(getContext());
        viewBinding.rvComment.addItemDecoration(itemDecoration);

        viewBinding.layoutBottomView.setOnSubmitCommentListener(comment -> {
            viewModel.action(CommentViewModel.UiAction.Submit.create(comment));
        });
    }

    @Override
    protected void bindAction() {
        compositeDisposable.add(
                viewModel
                        .uiStateSubject
                        .map((Function<CommentViewModel.UiState, ArrayList<Comment>>) uiState -> uiState.comments)
                        .distinctUntilChanged()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((Consumer<ArrayList<Comment>>) this::showComments)
        );
    }

    private void showComments(ArrayList<Comment> comments) {
        viewBinding.tvEmpty.setVisibility(comments.isEmpty() ? View.VISIBLE : View.GONE);
        viewBinding.rvComment.setVisibility(comments.isEmpty() ? View.GONE : View.VISIBLE);
        commentAdapter.submitList(comments);
    }
}
