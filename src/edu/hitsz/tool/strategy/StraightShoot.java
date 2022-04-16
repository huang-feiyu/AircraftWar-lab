package edu.hitsz.tool.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * @author Huang
 */
public class StraightShoot implements BulletStrategy {

    @Override
    public List<BaseBullet> ballisticChange(List<BaseBullet> bullets) {
        for (BaseBullet bullet : bullets) {
            bullet.setSpeedX(0);
        }
        return bullets;
    }
}
