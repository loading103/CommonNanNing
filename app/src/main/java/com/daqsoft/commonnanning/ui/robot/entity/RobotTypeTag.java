package com.daqsoft.commonnanning.ui.robot.entity;

/**
 * 机器人更具Tag来查询
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-24.14:04
 * @since JDK 1.8
 */

public class RobotTypeTag {

    /**
     * question : 黔东南什么特产好
     * answer : 黔东南有好多吃的东西
     * content : 黔东南呵呵水果不错
     */
    /**
     * 问题
     */
    private String question;
    /**
     * 回答
     */
    private String answer;
    /**
     * 内容
     */
    private String content;

    private boolean isSected;

    public boolean isSected() {
        return isSected;
    }

    public void setSected(boolean sected) {
        isSected = sected;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
