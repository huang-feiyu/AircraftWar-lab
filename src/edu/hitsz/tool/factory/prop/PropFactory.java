package edu.hitsz.tool.factory.prop;

import edu.hitsz.prop.AbstractProp;

public interface PropFactory {
    AbstractProp CreateProp(int locationX, int locationY, int speedX, int speedY);
}