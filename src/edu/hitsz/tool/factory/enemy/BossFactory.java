package edu.hitsz.tool.factory.enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossFactory implements EnemyFactory {
    @Override
    public AbstractAircraft CreateEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new BossEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2), 0, 5, 30);
    }
}