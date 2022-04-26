package edu.hitsz.application.game;

import edu.hitsz.application.ImageManager;

/**
 * @author Huang
 */
public class MediumGame extends Game {
    private final double minPropProb = 0.2;
    private final double maxEliteEnemyProb = 0.5;
    private final int maxEnemyMaxNumber = 10;
    private final int minBulletPropDuration = 1200;
    private final int minBossScoreThreshold = 200;

    private final int countCycle = 20;
    private int count = 0;

    public MediumGame(boolean musicOnFlag) {
        super(musicOnFlag);
    }

    @Override
    void difficultyAction() {
        count++;
        if (count != countCycle) {
            return;
        }
        double decreaseRatio = 0.95;
        double increaseRatio = 1.05;
        propProb = propProb > minPropProb ? propProb * decreaseRatio : propProb;
        eliteEnemyProb = eliteEnemyProb < maxEliteEnemyProb ? eliteEnemyProb * increaseRatio : propProb;
        enemyMaxNumber = enemyMaxNumber < maxEnemyMaxNumber ? enemyMaxNumber + 1 : enemyMaxNumber;
        bulletPropDuration = bulletPropDuration > minBulletPropDuration ? bulletPropDuration - 600 : bulletPropDuration;
        bossScoreThreshold = bossScoreThreshold > minBossScoreThreshold ? (int) (bossScoreThreshold * decreaseRatio) : bossScoreThreshold;
        System.out.println("\033[33mMEDIUM INCREASE:\033[0m propProb " + propProb +
            "\teliteEnemyProb " + eliteEnemyProb + "\tenemyMaxNumber " + enemyMaxNumber +
            "\tbulletPRopDuration" + bulletPropDuration + "\tbulletPropDuration " + bulletPropDuration);
        count = 0;
    }

    @Override
    void difficultyInit() {
        ImageManager.bgImg = ((int) (Math.random() * 2)) == 0 ?
            "src/images/bg2.jpg" : "src/images/bg3.jpg";
        chooseBgImage();
        propProb = 0.5;
        eliteEnemyProb = 0.2;
        enemyMaxNumber = 5;
        bulletPropDuration = 3000;
        bossScoreThreshold = 1000;
        bossHp = 1000;
        bossShowUp = true;
        System.out.println("\033[33mMEDIUM INIT:\033[0m propProb " + propProb +
            "\teliteEnemyProb " + eliteEnemyProb + "\tenemyMaxNumber " + enemyMaxNumber +
            "\tbulletPRopDuration " + bulletPropDuration + "\tbossHp " + bossHp +
            "\tbossScoreThreshold " + bossScoreThreshold + "\tbossShowUp " + bossShowUp);
    }
}
