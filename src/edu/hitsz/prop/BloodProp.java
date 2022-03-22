package edu.hitsz.prop;

/**
 * 加血道具
 *
 * @author huang
 */
public class BloodProp extends AbstractProp {
    final private int blood = 50; // 加血量

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public int getBlood() {
        return blood;
    }
}