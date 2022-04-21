package edu.hitsz.application;

import java.util.Objects;

/**
 * @author Huang
 */
public class MusicManager {
    private static final String DIR = "./src/videos/";
    private static final String BGM = "bgm.wav";
    private static final String BGM_BOSS = "bgm_boss.wav";
    private static final String BOMB = "bomb_explosion.wav";
    private static final String BULLET = "bullet.wav";
    private static final String BULLET_HIT = "bullet_hit.wav";
    private static final String GAME_OVER = "game_over.wav";
    private static final String GET_SUPPLY = "get_supply.wav";
    private static MusicThread bgMusic;
    private final boolean musicOnFlag;

    public MusicManager(boolean musicOnFlag) {
        this.musicOnFlag = musicOnFlag;
    }

    public void bgm() {
        if (musicOnFlag) {
            if (bgMusic != null && Objects.equals(bgMusic.getFileName(), DIR + BGM_BOSS)) {
                bgMusic.istop();
                play(DIR + BGM, true);
            } else if (bgMusic == null) {
                play(DIR + BGM, true);
            }
        }
    }

    public void bgmBoss() {
        if (musicOnFlag) {
            if (bgMusic != null && Objects.equals(bgMusic.getFileName(), DIR + BGM)) {
                bgMusic.istop();
                play(DIR + BGM_BOSS, true);
            } else if (bgMusic == null) {
                play(DIR + BGM_BOSS, true);
            }
        }
    }

    public void bomb() {
        if (musicOnFlag) {
            play(DIR + BOMB);
        }
    }

    public void bullet() {
        if (musicOnFlag) {
            //play(DIR + BULLET);
        }
    }

    public void bulletHit() {
        if (musicOnFlag) {
            play(DIR + BULLET_HIT);
        }
    }

    public void gameOver() {
        if (musicOnFlag) {
            play(DIR + GAME_OVER);
            bgMusic.istop();
        }
    }

    public void getSupply() {
        if (musicOnFlag) {
            play(DIR + GET_SUPPLY);
        }
    }

    private static void play(String file) {
        MusicThread player = new MusicThread(file);
        player.play();
    }

    private static void play(String file, boolean circulate) {
        MusicThread player = new MusicThread(file, circulate);
        player.play();
        bgMusic = player;
    }
}
