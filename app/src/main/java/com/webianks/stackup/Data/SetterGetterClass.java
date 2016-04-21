package com.webianks.stackup.Data;

public class SetterGetterClass {

    String title,link,name,profile;
    long date,creationDate;
    int views,answers,score;
    String[] tags;
    int apiQuota;

    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return title;
    }


    public void setLink(String link){
        this.link=link;
    }
    public String getLink(){
        return link;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getTags() {
        return tags;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getViews() {
        return views;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public int getAnswers() {
        return answers;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public long getDate() {
        return date;
    }

    public void setApiQuota(int apiQuota) {
        this.apiQuota = apiQuota;
    }

    public int getApiQuota() {
        return apiQuota;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getCreationDate() {
        return creationDate;
    }
}
