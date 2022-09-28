package io.srinnix.gccomment.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.srinnix.gccomment.R;
import io.srinnix.gccomment.ui.comment.CommentFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommentFragment fragment = new CommentFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout_content, fragment, CommentFragment.TAG)
                .commit();
    }
}
