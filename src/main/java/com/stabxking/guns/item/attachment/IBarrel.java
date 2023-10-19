package com.stabxking.guns.item.attachment;

import com.stabxking.guns.item.attachment.impl.Barrel;
import com.stabxking.guns.item.BarrelItem;

/**
 * An interface to turn an any item into a barrel attachment. This is useful if your item extends a
 * custom item class otherwise {@link BarrelItem} can be used instead of
 * this interface.
 * <p>
 * Author: Ocelot, MrCrayfish
 */
public interface IBarrel extends IAttachment<Barrel>
{
    /**
     * @return The type of this attachment
     */
    @Override
    default Type getType()
    {
        return Type.BARREL;
    }
}
