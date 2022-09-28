package io.srinnix.gccomment.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import io.srinnix.gccomment.R;
import io.srinnix.gccomment.databinding.LayoutCommentBottomViewBinding;
import io.srinnix.gccomment.utils.ViewUtils;

public class CommentBottomView extends LinearLayout {

    private LayoutCommentBottomViewBinding viewBinding;

    public CommentBottomView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CommentBottomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CommentBottomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        viewBinding = LayoutCommentBottomViewBinding.inflate(layoutInflater, this);

        setOrientation(LinearLayout.HORIZONTAL);

        int paddingVertical= (int) getContext().getResources().getDimension(R.dimen.dimen16dp);
        int paddingHorizontal= (int) getContext().getResources().getDimension(R.dimen.dimen16dp);
        setPaddingRelative(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);

        viewBinding.etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String comment = charSequence.toString().trim();
                if (comment.isEmpty()) {
                    viewBinding.imbSend.setEnabled(false);
                    viewBinding.imbSend.setAlpha(0.3F);
                } else {
                    viewBinding.imbSend.setEnabled(true);
                    viewBinding.imbSend.setAlpha(1F);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ViewUtils.expandClickArea(viewBinding.imbSend);
    }

    public void setOnSubmitCommentListener(OnSubmitCommentListener listener) {
        viewBinding.imbSend.setOnClickListener(view -> {
            String comment = viewBinding.etComment.getText().toString().trim();
            submitComment(comment, listener);
        });

        viewBinding.etComment.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String comment = textView.getText().toString().trim();
                submitComment(comment, listener);
            }
            return true;
        });
    }

    private void submitComment(String comment, OnSubmitCommentListener listener) {
        viewBinding.etComment.setText("");
        ViewUtils.hideKeyboard(viewBinding.etComment);

        listener.onSubmit(comment);
    }

    public interface OnSubmitCommentListener {

        void onSubmit(String comment);
    }
}
