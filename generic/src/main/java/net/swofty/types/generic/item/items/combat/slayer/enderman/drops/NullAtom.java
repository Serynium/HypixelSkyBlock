package net.swofty.types.generic.item.items.combat.slayer.enderman.drops;

import net.swofty.types.generic.item.SkyBlockItem;
import net.swofty.types.generic.item.impl.CustomSkyBlockItem;
import net.swofty.types.generic.item.impl.Enchanted;
import net.swofty.types.generic.item.impl.Sellable;
import net.swofty.types.generic.user.statistics.ItemStatistics;

public class NullAtom implements CustomSkyBlockItem, Enchanted, Sellable {
    @Override
    public ItemStatistics getStatistics(SkyBlockItem instance) {
        return ItemStatistics.EMPTY;
    }

    @Override
    public double getSellValue() {
        return 33333;
    }
}
