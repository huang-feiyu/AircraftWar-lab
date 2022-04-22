package edu.hitsz.factory.prop;

import edu.hitsz.prop.AbstractProp;

/**
 * @author Huang
 */
public interface PropFactory {
    AbstractProp createProp(int locationX, int locationY);
}
