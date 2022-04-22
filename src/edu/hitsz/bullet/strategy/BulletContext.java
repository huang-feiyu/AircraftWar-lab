package edu.hitsz.bullet.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * @author Huang
 */
public class BulletContext {

    private BulletStrategy strategy;

    public BulletContext(BulletStrategy strategy) {
        this.strategy = strategy;
    }

    public BulletStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(BulletStrategy strategy) {
        this.strategy = strategy;
    }

    public List<BaseBullet> executeStrategy(List<BaseBullet> bullets) {
        return this.strategy.ballisticChange(bullets);
    }
}
