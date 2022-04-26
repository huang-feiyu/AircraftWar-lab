package edu.hitsz.observer;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.List;

/**
 * @author Huang
 */
public class BombImpl implements BombListener {
    BombImpl() {
    }

    @Override
    public int updateEnemy(List<AbstractAircraft> enemies) {
        int score = 0;
        for (AbstractAircraft enemy : enemies) {
            if (!(enemy instanceof BossEnemy)) {
                enemy.vanish();
                score += enemy instanceof MobEnemy ? 10 : 20;
            }
        }
        return score;
    }

    @Override
    public void updateBullet(List<BaseBullet> bullets) {
        for (BaseBullet bullet : bullets) {
            bullet.vanish();
        }
    }
}
