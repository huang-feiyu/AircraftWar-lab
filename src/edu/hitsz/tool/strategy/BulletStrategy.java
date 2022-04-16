package edu.hitsz.tool.strategy;

import edu.hitsz.bullet.BaseBullet;
import java.util.List;

/**
 * @author Huang
 */
public interface BulletStrategy {
    /**
     * 弹道改变
     * @param bullets
     * @return
     */
    List<BaseBullet> ballisticChange(List<BaseBullet> bullets);
}
