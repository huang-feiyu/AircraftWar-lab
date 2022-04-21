package edu.hitsz.application;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.*;

/**
 * @author Huang
 */
public class MusicThread extends Thread {
    private String fileName; //音频文件名
    private AudioFormat audioFormat;
    private byte[] samples;
    private boolean circulate = false;
    private Clip clip = null;
    Thread thread; // 父进程

    public MusicThread(String filename) {
        //初始化filename
        this.fileName = filename;
        reverseMusic();
    }

    public MusicThread(String filename, boolean circulate) {
        //初始化filename
        this.fileName = filename;
        this.circulate = circulate;
        reverseMusic();
    }

    public void reverseMusic() {
        try {
            //定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));
            //用AudioFormat来获取AudioInputStream的格式
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }

    public void playStream(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        //源数据行SourceDataLine是可以写入数据的数据行
        SourceDataLine dataLine = null;
        //获取受数据行支持的音频格式DataLine.info
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        assert dataLine != null;
        dataLine.start();
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                //从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
                numBytesRead =
                    source.read(buffer, 0, buffer.length);
                //通过此源数据行将数据写入混频器
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        dataLine.drain();
        dataLine.close();

    }

    @Override
    public void run() {
        if (!circulate) {
            InputStream stream = new ByteArrayInputStream(samples);
            playStream(stream);
        } else {
            try {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(fileName));
                this.clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("AudioInputStream: Unhandled Error");
            }
        }
    }

    public void play() {
        thread = new Thread(this);
        thread.start();
    }

    public void istop() {
        circulate = false;
        clip.stop();
        thread.stop();
    }

    public String getFileName() {
        return fileName;
    }

}
