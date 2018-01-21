package com.example.tkbs3.speechrecognition;


/**
 * Created by emine on 28.12.2017.
 */

public class Memory {
    int word_id;
    String word_name;
    String word_answer;
    //Constructor
    public Memory(int id, String name, String answer){
        this.word_id=id;
        this.word_name=name;
        this.word_answer=answer;

    }
    //Constructor
    public Memory( String name, String answer){

        this.word_name=name;
        this.word_answer=answer;

    }
    public Memory() {

    }


    public int getWord_id() {  return word_id;   }

    public String getWord_name() {    return word_name;    }

    public String getWord_answer() {
        return word_answer;
    }

    public void setWord_id(int wordId) { this.word_id = wordId;   }

    public void setWord_name(String wordName) {
        this.word_name = wordName;
    }

    public void setWord_answere(String wordAnswer) {
        this.word_answer = wordAnswer;
    }


}
