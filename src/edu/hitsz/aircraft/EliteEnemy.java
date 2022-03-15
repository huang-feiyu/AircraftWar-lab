package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机，可发射子弹
 *
 * @author huang
 */
public class EliteEnemy extends AbstractAircraft {
    //private int shootNum = 1; // 子弹一次发射数量
    private int power = 5; // 子弹伤害
    private int direction = 1; // 子弹射击方向 (向上发射：1，向下发射：-1)

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        // System.out.println("Elite init");
    }

    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<AbstractBullet> shoot() {
        List<AbstractBullet> res = new LinkedList<>();
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;
        AbstractBullet abstractBullet;
        res.add(new EnemyBullet(this.getLocationX(),
            this.getLocationY() + direction * 2, speedX, speedY, power));
        return res;
    }
}
