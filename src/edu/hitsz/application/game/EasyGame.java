package edu.hitsz.application.game;

import edu.hitsz.application.ImageManager;

/**
 * @author Huang
 */
public class EasyGame extends Game {

    public EasyGame(boolean musicOnFlag) {
        super(musicOnFlag);
    }

    @Override
    void difficultyAction() {
    }

    @Override
    void difficultyInit() {
        ImageManager.bgImg = "src/images/bg.jpg";
        chooseBgImage();
        propProb = 0.5;
        eliteEnemyProb = 0.1;
        enemyMaxNumber = 5;
        bulletPropDuration = 3000;
        bossShowUp = false;
        System.out.println("\033[33mEASY INIT:\033[0m propProb " + propProb +
            "\teliteEnemyProb " + eliteEnemyProb + "\tenemyMaxNumber " + enemyMaxNumber +
            "\tbulletPropDuration " + bulletPropDuration + "\tbossShowUp " + bossShowUp);
    }
}
