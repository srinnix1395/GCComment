package io.srinnix.gccomment.ui.comment;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Objects;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.srinnix.gccomment.data.model.Comment;

public class CommentViewModelTest {

    private CommentViewModel viewModel;

    @BeforeClass
    public static void setupClass() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
        RxJavaPlugins.setInitIoSchedulerHandler(schedulerSupplier -> TrampolineScheduler.instance());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerSupplier -> TrampolineScheduler.instance());
    }

    @Before
    public void setupTest() {
        viewModel = new CommentViewModel();
    }

    @Test
    public void testSubmitPlainText() {
        String plainText = "comment";
        CommentViewModel.UiAction.Submit action = CommentViewModel.UiAction.Submit.create(plainText);
        viewModel.action(action);

        viewModel
                .uiStateSubject
                .test()
                .assertValue(uiState -> {
                    if (uiState.comments.isEmpty()) {
                        return false;
                    }

                    Comment comment = uiState.comments.get(0);
                    return Objects.equals(comment.getContent(), plainText);
                })
                .dispose();
    }

    @Test
    public void testSubmitMultipleComments() {
        String comment1 = "comment";
        CommentViewModel.UiAction.Submit action1 = CommentViewModel.UiAction.Submit.create(comment1);
        viewModel.action(action1);

        String comment2 = "comment";
        CommentViewModel.UiAction.Submit action2 = CommentViewModel.UiAction.Submit.create(comment2);
        viewModel.action(action2);

        String comment3 = "comment";
        CommentViewModel.UiAction.Submit action3 = CommentViewModel.UiAction.Submit.create(comment3);
        viewModel.action(action3);

        viewModel
                .uiStateSubject
                .test()
                .assertValue(uiState -> uiState.comments.size() == 3)
                .dispose();
    }

    @Test
    public void testSubmitMentionedComment() {
        String comment = "@billgates do you know where is @elonmusk?";
        CommentViewModel.UiAction.Submit action = CommentViewModel.UiAction.Submit.create(comment);
        viewModel.action(action);

        viewModel
                .uiStateSubject
                .test()
                .assertValue(uiState -> {
                    Comment createdComment = uiState.comments.get(0);
                    String expectedJson = "{\"mentions\": [\n" +
                            "    \"billgates\",\n" +
                            "    \"elonmusk\"\n" +
                            "]}";

                    return createdComment.getJson().equals(expectedJson);
                })
                .dispose();
    }

    @Test
    public void testSubmitLinkComment() {
        String comment = "Olympics 2020 is happening; https://olympics.com/tokyo-2020/en/";
        CommentViewModel.UiAction.Submit action = CommentViewModel.UiAction.Submit.create(comment);
        viewModel.action(action);

        viewModel
                .uiStateSubject
                .test()
                .assertValue(uiState -> {
                    Comment createdComment = uiState.comments.get(0);
                    String expectedJson = "{\"links\": [{\n" +
                            "    \"title\": \"Tokyo 2020 Summer Olympics - Athletes, Medals & Results\",\n" +
                            "    \"url\": \"https://olympics.com/tokyo-2020/en/\"\n" +
                            "}]}";

                    return createdComment.getJson().equals(expectedJson);
                })
                .dispose();
    }

    @Test
    public void testSubmitMentionsAndLinkComment() {
        String comment = "@billgates do you know where is @elonmusk? Olympics 2020 is happening; https://olympics.com/tokyo-2020/en/";
        CommentViewModel.UiAction.Submit action = CommentViewModel.UiAction.Submit.create(comment);
        viewModel.action(action);

        viewModel
                .uiStateSubject
                .test()
                .assertValue(uiState -> {
                    Comment createdComment = uiState.comments.get(0);
                    String expectedJson = "{\n" +
                            "    \"mentions\": [\n" +
                            "        \"billgates\",\n" +
                            "        \"elonmusk\"\n" +
                            "    ],\n" +
                            "    \"links\": [{\n" +
                            "        \"title\": \"Tokyo 2020 Summer Olympics - Athletes, Medals & Results\",\n" +
                            "        \"url\": \"https://olympics.com/tokyo-2020/en/\"\n" +
                            "    }]\n" +
                            "}";

                    return createdComment.getJson().equals(expectedJson);
                })
                .dispose();
    }

    @Test
    public void testClickCommentAction() {
        String comment = "@billgates do you know where is @elonmusk? Olympics 2020 is happening; https://olympics.com/tokyo-2020/en/";
        CommentViewModel.UiAction.Submit submitAction = CommentViewModel.UiAction.Submit.create(comment);
        viewModel.action(submitAction);

        CommentViewModel.UiAction.ClickComment clickCommentAction = CommentViewModel.UiAction.ClickComment.create(0);
        viewModel.action(clickCommentAction);

        viewModel
                .uiStateSubject
                .test()
                .assertValue(uiState -> {
                    Comment createdComment = uiState.comments.get(0);
                    return !createdComment.isExpand();
                })
                .dispose();
    }

    @AfterClass
    public static void tearDownClass() {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}
