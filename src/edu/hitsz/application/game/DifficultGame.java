package edu.hitsz.application.game;

import edu.hitsz.application.ImageManager;

/**
 * @author Huang
 */
public class DifficultGame extends Game {
    private final double minPropProb = 0.1;
    private final double maxEliteEnemyProb = 1;
    private final int maxEnemyMaxNumber = 20;
    private final int minBulletPropDuration = 600;
    private final int minBossScoreThreshold = 100;
    private final int maxBossHp = 10000;

    private final int countCycle = 20;
    private int count = 0;

    public DifficultGame(boolean musicOnFlag) {
        super(musicOnFlag);
    }

    @Override
    void difficultyAction() {
        count++;
        if (count != countCycle) {
            return;
        }
        double decreaseRatio = 0.9;
        double increaseRatio = 1.1;
        eliteEnemyProb = eliteEnemyProb < maxEliteEnemyProb ? eliteEnemyProb * increaseRatio : propProb;
        bossHp = bossHp < maxBossHp ? (int) (bossHp * increaseRatio) : bossHp;
        enemyMaxNumber = enemyMaxNumber < maxEnemyMaxNumber ? enemyMaxNumber + 1 : enemyMaxNumber;
        propProb = propProb > minPropProb ? propProb * decreaseRatio : propProb;
        bulletPropDuration = bulletPropDuration > minBulletPropDuration ? bulletPropDuration - 600 : bulletPropDuration;
        bossScoreThreshold = bossScoreThreshold > minBossScoreThreshold ? bossScoreThreshold + 100 * bossCount : bossScoreThreshold;
        System.out.println("\033[33mMEDIUM INCREASE:\033[0m propProb " + propProb +
            "\teliteEnemyProb " + eliteEnemyProb + "\tenemyMaxNumber " + enemyMaxNumber +
            "\tbulletPRopDuration" + bulletPropDuration + "\tbulletPropDuration " + bulletPropDuration +
            "\tbossHp " + bossHp);
        count = 0;
    }

    @Override
    void difficultyInit() {
        ImageManager.bgImg = ((int) (Math.random() * 2)) == 0 ?
            "src/images/bg4.jpg" : "src/images/bg5.jpg";
        chooseBgImage();
        propProb = 0.3;
        eliteEnemyProb = 0.4;
        enemyMaxNumber = 8;
        bulletPropDuration = 2400;
        bossScoreThreshold = 750;
        bossHp = 2000;
        bossShowUp = true;
        System.out.println("\033[33mMEDIUM INIT:\033[0m propProb " + propProb +
            "\teliteEnemyProb " + eliteEnemyProb + "\tenemyMaxNumber " + enemyMaxNumber +
            "\tbulletPRopDuration " + bulletPropDuration + "\tbossHp " + bossHp +
            "\tbossScoreThreshold " + bossScoreThreshold + "\tbossShowUp " + bossShowUp);
    }
}
