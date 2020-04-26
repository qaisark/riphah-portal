package com.goprogs.riphahportal;

public class Answer {
    private int ID;
    private String answer;
    private String created_at;
    private int user_id;
    private String user_name;

    public Answer(int ID, String answer) {
        this.ID = ID;
        this.answer = answer;
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
