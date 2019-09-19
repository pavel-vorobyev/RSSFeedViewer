package com.pavelvorobyev.rssfeedviewer.ui.feed;

import com.pavelvorobyev.rssfeedviewer.businesslogic.pojo.Item;
import java.util.List;

public interface FeedViewState {

    //No internet
    class NoConnectionState implements FeedViewState { }

    //Loading state
    class LoadingState implements FeedViewState { }

    //Data state
    class DataState implements FeedViewState {

        private List<Item> feed;

        DataState(List<Item> feed) {
            this.feed = feed;
        }

        public List<Item> getFeed() {
            return feed;
        }
    }

    //Error state
    class ErrorState implements FeedViewState {

        private Throwable error;

        ErrorState(Throwable error) {
            this.error = error;
        }

        public Throwable getError() {
            return error;
        }
    }

}
