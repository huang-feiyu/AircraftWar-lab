package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * boss敌机，固定不动，分散式子弹
 *
 * @author Huang
 */
public class BossEnemy extends AbstractAircraft {
    private int shootNum = 5;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        int shootNum = 5; // 5枚子弹
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + 5;
        BaseBullet baseBullet;
        for (int i = 0; i < shootNum; i++) {
            baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, 5);
            res.add(baseBullet);
        }
        return res;
    }
}
