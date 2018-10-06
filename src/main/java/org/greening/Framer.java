package org.greening;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.DirectProcessor;
import org.reactivestreams.Subscription;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;

import java.util.LinkedList;

public class Framer extends Flux<Frame> implements Subscriber<Integer> {
    private static final Logger logger = LogManager.getLogger(Framer.class);

    private Subscription subscription;
    private CoreSubscriber<? super Frame> subscriber;
    private LinkedList<Frame> frames;

    Framer() {
        frames = new LinkedList<Frame>();
        frames.add(new Frame());
    }

    @Override
    public void subscribe(CoreSubscriber<? super Frame> s) {
        subscriber = s;
    }

    public void onComplete() {
        logger.trace("onComplete()");
    }

    public void onNext(Integer ball) {

        // First update the frame state
        for (Frame f : frames)
            f.onNext(ball);
        if (frames.isEmpty() || frames.getLast().createAnother()) {
            var nf = new Frame();
            frames.add(nf);
        }
        // Now spew out a frame if ready
        if (!frames.getFirst().needBall())
            subscriber.onNext(frames.pop());
    }

    public void onSubscribe(Subscription s) {
        subscription = s;
        subscription.request(Long.MAX_VALUE);
    }

    public void onError(Throwable t) {
        logger.trace(t.toString());
    }
}
