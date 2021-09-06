package com.daqsoft.commonnanning.ui.robot.entity;

/**
 * 换一批
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-11-30.13:42
 * @since JDK 1.8
 */

public class RobotChangeEty {
    private String question;
    private int mPage;
    private int mPostion;

    public RobotChangeEty(String question, int mPage, int mPostion) {
        this.question = question;
        this.mPage = mPage;
        this.mPostion = mPostion;
    }

    public int getmPostion() {
        return mPostion;
    }

    public void setmPostion(int mPostion) {
        this.mPostion = mPostion;
    }

    public int getmPage() {
        return mPage;
    }

    public void setmPage(int mPage) {
        this.mPage = mPage;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
