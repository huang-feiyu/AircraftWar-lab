package edu.hitsz.observer;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BombProp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Huang
 */
public class BombObserver {
    private HashMap<BombProp, BombListener> bombs = new HashMap<>();

    public void addBomb(BombProp bomb) {
        bombs.put(bomb, new BombImpl());
    }

    public void removeBomb(BombProp bomb) {
        bombs.remove(bomb);
    }

    public int notifyBomb( BombProp bomb,
                        List<AbstractAircraft> enemies,
                        List<BaseBullet> bullets) {
        bombs.get(bomb).updateBullet(bullets);
        return bombs.get(bomb).updateEnemy(enemies);
    }
}
