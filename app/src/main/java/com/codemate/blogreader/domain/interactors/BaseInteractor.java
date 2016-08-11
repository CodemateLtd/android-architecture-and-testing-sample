package com.codemate.blogreader.domain.interactors;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by ironman on 26/07/16.
 */
public abstract class BaseInteractor<T> {
    private final Scheduler worker;
    private final Scheduler observer;
    private Subscription subscription = Subscriptions.empty();

    protected BaseInteractor(Scheduler worker, Scheduler observer) {
        this.worker = worker;
        this.observer = observer;
    }

    protected abstract Observable<T> buildObservable();

    public void execute(Subscriber<T> subscriber) {
        this.subscription = buildObservable()
                .subscribeOn(worker)
                .observeOn(observer)
                .subscribe(subscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public abstract void setForceRefresh(boolean forceRefresh);
}
