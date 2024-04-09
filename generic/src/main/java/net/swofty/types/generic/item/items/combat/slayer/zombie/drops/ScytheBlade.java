package net.swofty.types.generic.item.items.combat.slayer.zombie.drops;

import net.swofty.types.generic.item.ItemType;
import net.swofty.types.generic.item.SkyBlockItem;
import net.swofty.types.generic.item.impl.CustomSkyBlockItem;
import net.swofty.types.generic.item.impl.Enchanted;
import net.swofty.types.generic.item.impl.RightClickRecipe;
import net.swofty.types.generic.user.statistics.ItemStatistics;

public class ScytheBlade implements CustomSkyBlockItem, Enchanted, RightClickRecipe {
    @Override
    public ItemStatistics getStatistics(SkyBlockItem instance) {
        return ItemStatistics.EMPTY;
    }

    @Override
    public ItemType getRecipeItem() {
        return ItemType.REAPER_SCYTHE;
    }
}
