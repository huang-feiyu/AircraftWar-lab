package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * @author Huang
 */
public class EliteFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        int x = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()));
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2);
        int speedX = ((int) (Math.random() * 3) - 2) * 3;
        return new EliteEnemy(x, y, speedX, 5, 30);
    }
}
