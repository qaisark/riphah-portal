package com.goprogs.riphahportal;

public class Query {
    public int id;
    public String title;
    public String description;
    public int answers;
    public int user_id;
    public String created_at;
    public String uname;
    public Query(String title) {
        this.title = title;
    }

    public Query(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Query(int id, String title, String created_at) {
        this.id = id;
        this.title = title;
        this.created_at = created_at;
    }

    public String getUname() {
        return uname;
    }

    public Query(int id, String title, String created_at, String uname) {
        this.id = id;
        this.title = title;
        this.created_at = created_at;
        this.uname = uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Query(String title, String description, int answers, int user_id, String created_at)
    {
        this.title = title;
        this.description = description;
        this.answers = answers;
        this.user_id = user_id;
        this.created_at= created_at;
    }

    public Query(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


}
