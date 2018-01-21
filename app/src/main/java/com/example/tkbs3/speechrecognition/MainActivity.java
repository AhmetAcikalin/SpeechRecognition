package com.example.tkbs3.speechrecognition;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
        final Locale locale = new Locale("tr", "TR");
        private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_SPEECH_INPUT1 = 101;
        private TextView mVoiceInputTv;
        private ImageButton mSpeakBtn;
        TextToSpeech tts;
        Database v1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
            mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
            mSpeakBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startVoiceInput();
                }
            });
            tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != TextToSpeech.ERROR) {
                        tts.setLanguage(new Locale("tr-TR"));
                    }
                }
            });
        }

        private void startVoiceInput() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "tr-TR");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "tr-TR");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Merhaba, Sana Nasıl Yardım Edebilirim?");
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {

            }
        }

    private void saveMemory(String word, String answer) {
        Memory a= new Memory(word,answer);
        Database db = new Database(getApplicationContext());
        db.createMemory(a);
    }

    String word="";
    String answer="";
        boolean mant=false;
    boolean up=false;
        @Override
        protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case REQ_CODE_SPEECH_INPUT: {
                    if (resultCode == RESULT_OK && null != data) {
                        final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        mVoiceInputTv.setText(result.get(0));
                        final String utteranceId=this.hashCode() + "";
                        String toSpeak;


                        switch(result.get(0)){

                            case "Merhaba":
                                toSpeak = "Merhaba, Sana Nasıl Yardım Edebilirim";
                                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                break;
                            case "Selam":
                                toSpeak = "Merhaba, Sana Nasıl Yardım Edebilirim";
                                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                break;
                            case "Selam canım":
                                toSpeak = "Selam cınım naber yaa?";
                                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                break;
                            case "Evet öğretirim" :
                                mant=true;
                                toSpeak = "Bunu duyduğumda ne demeliyim ";
                                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startVoiceInput();
                                        final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                                    }
                                }, 3000);

                                break;
                            case "Hayır öğretemem":
                                toSpeak = "Hımmm çok üzüldüm, yeni şeyler öğrenmeyi çok severim oysaki";
                                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                mant=false;
                                break;
                            case "kelime değiştirmek istiyorum":
                                toSpeak = "Hangi kelimeyi değiştirmemi istiyorsunuz ?";
                                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                final Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startVoiceInput();
                                        final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                                    }
                                }, 3000);
                                up=true;
                                break;
                            case "sus":
                                tts.stop();
                                break;
                            default:

                                Database db = new Database(getApplicationContext());

                                List<String> memos=db.getAllMemories();
                                if(!mant) {

                                    String db_word="";
                                    String db_answer ="";
                                    String db_id ="";
                                    int counter=0;
                                    if(up){
                                        for(int i=0;i<memos.size();i++){
                                            db_word = memos.get(i).substring(memos.get(i).indexOf(".")+1, memos.get(i).indexOf(";"));
                                            db_answer = memos.get(i).substring(0, memos.get(i).indexOf("."));
                                            if(result.get(0).equals(db_word)){

                                                toSpeak = "Dinliyorum";
                                                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                                                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                                final Handler handler5 = new Handler();
                                                handler5.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        startVoiceInput();
                                                        final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                                                    }
                                                }, 3000);

                                                break;
                                            }


                                            }
                                    }else {
                                        for (int i = 0; i < memos.size(); i++) {
                                            db_word = memos.get(i).substring(memos.get(i).indexOf(".") + 1, memos.get(i).indexOf(";"));
                                            db_answer = memos.get(i).substring(memos.get(i).indexOf(";") + 1, memos.get(i).length());
                                            if (result.get(0).equals(db_word)) {
                                                toSpeak = db_answer;
                                                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                                                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                                counter++;
                                                break;

                                            }


                                        }

                                        if (counter != 0) break;
                                    }
                                    toSpeak = "Üzgünüm bu söz öbeğini bilmiyorum. Bunu bana öğretirmisin ?";
                                    Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                    word = result.get(0);
                                    final Handler handler3 = new Handler();
                                    handler3.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startVoiceInput();
                                            final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                                        }
                                    }, 4000);
                                }
                                else {
                                    answer=result.get(0);
                                    toSpeak = "Hadi deniyelim";
                                    Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                    saveMemory(word,answer);
                                    mant=false;
                                }

                                break;

                        }
                    }
                    break;
                }

            }
        }
}

