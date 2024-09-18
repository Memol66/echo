import javax.sound.sampled.*;
import java.io.*;

public class EchoEffect {
    public static void main(String[] args) {
        try {
            // Load audio file.000000000001111111
            File audioFile = new File("input.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioInputStream.getFormat();
            byte[] audioData = new byte[(int) audioFile.length()];
            audioInputStream.read(audioData);

            // Define echo parameters.
            float sampleRate = format.getSampleRate();
            int numChannels = format.getChannels();
            float delayTime = 0.5f; // in seconds
            int delaySamples = (int) (delayTime * sampleRate);
            float gain = 0.8f;

            // Apply echo effect
            for (int i = 0; i < audioData.length - delaySamples; i++) {
                int delayedIndex = i + delaySamples;
                if (delayedIndex < audioData.length) {
                    // Apply echo
                    byte originalSample = audioData[i];
                    byte delayedSample = audioData[delayedIndex];
                    int echoedSample = (int) (originalSample + gain * delayedSample);
                    audioData[i] = (byte) echoedSample;
                }
            }

            // Save echoed audio
            File outputFile = new File("output.wav");
            AudioOutputStream audioOutputStream = AudioSystem.getAudioOutputStream(format, new AudioFormat(format.getEncoding(), sampleRate, format.getSampleSizeInBits(), numChannels, format.getFrameSize(), sampleRate, format.isBigEndian()));
            audioOutputStream.write(audioData);
            audioOutputStream.close();
            System.out.println("Echo effect applied successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
