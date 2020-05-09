package com.sumit.spid.home.HomeData;

public class PeopleSaysData  {
    String peopleQuestion;
    String peopleAnswer;

    public PeopleSaysData(String peopleQuestion, String peopleAnswer) {
        this.peopleQuestion = peopleQuestion;
        this.peopleAnswer = peopleAnswer;
    }

    public String getPeopleQuestion() {
        return peopleQuestion;
    }

    public void setPeopleQuestion(String peopleQuestion) {
        this.peopleQuestion = peopleQuestion;
    }

    public String getPeopleAnswer() {
        return peopleAnswer;
    }

    public void setPeopleAnswer(String peopleAnswer) {
        this.peopleAnswer = peopleAnswer;
    }
}
