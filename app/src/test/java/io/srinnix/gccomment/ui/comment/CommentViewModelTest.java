package io.srinnix.gccomment.ui.comment;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import io.srinnix.gccomment.data.model.Comment;

public class CommentViewModelTest {

    private CommentViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new CommentViewModel();
    }

    @Test
    public void testSubmitPlainText() {
        TestScheduler testScheduler = new TestScheduler();

        String plainText = "comment";
        CommentViewModel.UiAction.Submit action = CommentViewModel.UiAction.Submit.create(plainText);
        viewModel.action(action);

        viewModel
                .uiStateSubject
                .test()
                .assertValue(new Predicate<CommentViewModel.UiState>() {
                    @Override
                    public boolean test(CommentViewModel.UiState uiState) {
                        if (uiState.comments.isEmpty()) {
                            return false;
                        }

                        Comment comment = uiState.comments.get(0);
                        if (!Objects.equals(comment.getContent(), plainText)) {
                            return false;
                        }

                        return true;
                    }
                })
                .dispose();
    }
}
