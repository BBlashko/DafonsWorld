package Audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioOut {
	private Clip clip;
	private boolean playedonce;
	public AudioOut(String s){
		
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
			
			AudioFormat baseFormat = audioIn.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(),baseFormat.getChannels() *2, baseFormat.getSampleRate(), false);
			AudioInputStream dAudioIn = AudioSystem.getAudioInputStream(decodeFormat, audioIn);
			clip = AudioSystem.getClip();
			clip.open(dAudioIn);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void play(){
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();		
	}
	public void stop(){
		if(clip.isRunning()) clip.stop();
	}
	public void close(){
		stop();
		clip.close();
		
		
	}
	public void loop() {
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public boolean getPlayedonce(){
		return playedonce;
	}
}
