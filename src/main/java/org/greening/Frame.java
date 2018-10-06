package org.greening;

public class Frame {
    private int frameScore = 0, frameStep = 0;
    private int[] balls = {-1, -1, -1};
    private int ballIndex = 0;
    private int balltotal = 0;

    public boolean needBall() {
        return ! ( (ballIndex > 2) || (ballIndex == 2 && balltotal < 10) );
    }

    /**
     *
     * @return Does another Frame need to be created?
     */
    public boolean createAnother() {
        return balltotal == 10 && ballIndex <= 2;
    }

    public void onNext(int ball) {
        balls[ballIndex++] = ball;
        balltotal += ball;
    }

    public Integer score() {
        return balltotal;
    }

    public String toString() {
        String out = "";
        for (var i = 0; i<ballIndex; i++)
            out += balls[i] + ":";
        out += balltotal;
        return out;
    }
}
