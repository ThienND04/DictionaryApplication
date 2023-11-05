package com.example.dictionary.api;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
public class TextToSpeech {
    static {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    }
    public static void textToSpeech(String text) {
        Voice voice = VoiceManager.getInstance().getVoice("kevin");
        if (voice != null) {
            voice.allocate();
            voice.setPitch(150.0f);  // Adjust the pitch
            voice.setRate(150.0f);   // Adjust the speech rate
            voice.setVolume(3.0f);   // Adjust the volume
            voice.speak(text);
        }
    }
}
