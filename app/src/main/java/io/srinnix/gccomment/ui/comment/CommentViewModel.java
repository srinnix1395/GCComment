package io.srinnix.gccomment.ui.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.core.util.Pair;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.srinnix.gccomment.base.BaseViewModel;
import io.srinnix.gccomment.data.model.Comment;
import io.srinnix.gccomment.data.model.Link;
import io.srinnix.gccomment.utils.RegexUtils;
import io.srinnix.gccomment.utils.UrlUtils;

public class CommentViewModel extends BaseViewModel {

    public BehaviorSubject<UiState> uiStateSubject = BehaviorSubject.createDefault(new UiState());
    private final PublishSubject<UiAction> actionSubject = PublishSubject.create();

    public CommentViewModel() {
        compositeDisposable
                .add(
                        actionSubject
                                .filter(uiAction -> uiAction instanceof UiAction.Submit)
                                .map((Function<UiAction, UiAction.Submit>) action -> (UiAction.Submit) action)
                                .flatMap(this::handleSubmitAction)
                                .subscribe(comments -> {
                                    UiState uiState = CommentViewModel.this.uiStateSubject.getValue();
                                    uiState.comments = comments;
                                    uiStateSubject.onNext(uiState);
                                })
                );

        compositeDisposable
                .add(
                        actionSubject
                                .filter(uiAction -> uiAction instanceof UiAction.ClickComment)
                                .map((Function<UiAction, UiAction.ClickComment>) action -> (UiAction.ClickComment) action)
                                .flatMap(this::handleClickCommentAction)
                                .subscribe(comments -> {
                                    UiState uiState = CommentViewModel.this.uiStateSubject.getValue();
                                    uiState.comments = comments;
                                    uiStateSubject.onNext(uiState);
                                })
                );
    }

    private Observable<ArrayList<Comment>> handleSubmitAction(UiAction.Submit uiAction) {
        return Observable
                .just(new Pair<>(uiStateSubject.getValue().comments, uiAction.comment))
                .map(pair -> {
                    String inputComment = pair.second;
                    List<String> tags = extractTag(inputComment);
                    List<Link> links = extractLinks(inputComment);
                    String id = UUID.randomUUID().toString();
                    Comment newComment = new Comment(id, inputComment, links, tags);

                    ArrayList<Comment> oldComments = pair.first;
                    ArrayList<Comment> newComments = new ArrayList<>(oldComments);
                    newComments.add(newComment);

                    return newComments;
                })
                .subscribeOn(Schedulers.io());
    }

    private List<String> extractTag(String inputComment) {
        return RegexUtils.extractTag(inputComment);
    }

    private List<Link> extractLinks(String inputComment) {
        List<String> urls = RegexUtils.extractUrl(inputComment);
        if (urls == null || urls.isEmpty()) {
            return null;
        }

        List<Link> links = new ArrayList<>();
        for (String url : urls) {
            String title = UrlUtils.getPageTitle(url);
            Link link = new Link(title, url);
            links.add(link);
        }

        return links;
    }

    private Observable<ArrayList<Comment>> handleClickCommentAction(UiAction.ClickComment uiAction) {
        return Observable
                .just(new Pair<>(uiStateSubject.getValue().comments, uiAction.position))
                .filter(pair -> {
                    ArrayList<Comment> comments = pair.first;
                    int position = pair.second;
                    return position >= 0 && position < comments.size();
                })
                .map(pair -> {
                    Comment oldComment = pair.first.get(pair.second);
                    Comment newComment = new Comment(oldComment);
                    newComment.setExpand(!oldComment.isExpand());

                    ArrayList<Comment> newComments = new ArrayList<>(pair.first);
                    newComments.set(pair.second, newComment);

                    return newComments;
                });
    }

    public <A extends UiAction> void action(A action) {
        actionSubject.onNext(action);
    }

    public static class UiState {
        public ArrayList<Comment> comments = new ArrayList<>();
    }

    public static class UiAction {
        public static class Submit extends UiAction {

            public static Submit create(String comment) {
                Submit action = new Submit();
                action.comment = comment;
                return action;
            }

            public String comment;
        }

        public static class ClickComment extends UiAction {

            public static ClickComment create(int position) {
                ClickComment action = new ClickComment();
                action.position = position;
                return action;
            }

            public int position;
        }
    }
}
