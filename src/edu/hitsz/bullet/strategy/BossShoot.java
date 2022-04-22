package edu.hitsz.bullet.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * @author Huang
 */
public class BossShoot implements BulletStrategy {
    @Override
    public List<BaseBullet> ballisticChange(List<BaseBullet> bullets) {
        int len = bullets.size();
        int half = len / 2;
        for (int i = 0; i < len; i++) {
            bullets.get(i).setSpeedX(half - i);
        }
        return bullets;
    }
}
