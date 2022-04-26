package edu.hitsz.observer;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * @author Huang
 */
public interface BombListener {
    int updateEnemy(List<AbstractAircraft> enemies);
    void updateBullet(List<BaseBullet> bullets);
}
