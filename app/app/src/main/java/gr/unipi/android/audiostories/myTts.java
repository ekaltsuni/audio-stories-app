package gr.unipi.android.audiostories;
import android.content.Context;
import android.speech.tts.TextToSpeech;
public class myTts {
    private TextToSpeech tts;
    TextToSpeech.OnInitListener  initListener= new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                //tts.setLanguage(Locale.forLanguageTag("el"));
            }
        }
    };

    public myTts(Context context){
        tts = new TextToSpeech(context,initListener);
    }

    public void speak(String message){
        tts.speak(message,TextToSpeech.QUEUE_ADD,null,null);
    }
}
