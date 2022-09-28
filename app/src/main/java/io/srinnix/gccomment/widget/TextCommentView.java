package io.srinnix.gccomment.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import io.srinnix.gccomment.R;
import io.srinnix.gccomment.data.model.Link;
import io.srinnix.gccomment.utils.StringUtils;

public class TextCommentView extends AppCompatTextView {

    private OnClickContentListener onClickContentListener;

    public TextCommentView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TextCommentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLinkTextColor(ContextCompat.getColor(context, R.color.color_blue));
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    //region SpannableContent
    private SpannableStringBuilder createSpannableContent(Context context, String text, List<String> tags, List<Link> links) {
        if (text.isEmpty()) {
            return null;
        }

        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        setTagSpan(ssb, context, text, tags);
        setUrlSpan(ssb, context, text, links);
        return ssb;
    }

    private void setUrlSpan(SpannableStringBuilder ssb, Context context, String text, List<Link> links) {
        if (links == null || links.isEmpty()) {
            return;
        }

        for (Link link : links) {
            int index = 0;
            index = text.indexOf(link.getUrl(), index);
            while (index != -1) {
                StringUtils.setClickableSpan(ssb, index, index + link.getUrl().length(), new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        if (onClickContentListener != null) {
                            onClickContentListener.onClickUrlListener(link.getUrl());
                        }
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                });
                index = text.indexOf(link.getUrl(), index + ssb.length());
            }
        }
    }

    private void setTagSpan(SpannableStringBuilder ssb, Context context, String text, List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return;
        }

        for (String tag : tags) {
            int index = 0;
            index = text.indexOf(tag, index);
            while (index != -1) {
                StringUtils.setClickableSpan(ssb, index, index + tag.length(), new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        if (onClickContentListener != null) {
                            onClickContentListener.onClickTagListener(tag);
                        }
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                });
                index = text.indexOf(tag, index + ssb.length());
            }
        }
    }
    //endregion SpannableContent

    public void setContent(String text, List<String> tags, List<Link> links) {
        SpannableStringBuilder ssb = createSpannableContent(getContext(), text, tags, links);
        setText(ssb);
    }

    public void setOnClickContentListener(OnClickContentListener listener) {
        this.onClickContentListener = listener;
    }

    public interface OnClickContentListener {
        void onClickTagListener(String tag);
        void onClickUrlListener(String url);
    }
}
