package gr.unipi.android.audiostories;
import android.content.Context;
import android.speech.tts.TextToSpeech;
public class MyTts {
    private TextToSpeech tts;
    private Context context;
    TextToSpeech.OnInitListener  initListener= status -> {
        if (status == TextToSpeech.SUCCESS) {
            //tts.setLanguage(Locale.forLanguageTag("el"));
        }
    };

    public MyTts(Context context){
        this.context = context;
        tts = new TextToSpeech(context,initListener);
    }

    public void speak(String message){
        tts.speak(message,TextToSpeech.QUEUE_ADD,null,null);
    }

    public void stopSpeaking() {
        if (tts != null) {
            tts.stop();
        }
    }
}

