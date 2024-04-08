package net.swofty.types.generic.item.items.minion.upgrade;

import net.swofty.types.generic.item.SkyBlockItem;
import net.swofty.types.generic.item.impl.CustomSkyBlockItem;
import net.swofty.types.generic.item.impl.NotFinishedYet;
import net.swofty.types.generic.user.SkyBlockPlayer;
import net.swofty.types.generic.user.statistics.ItemStatistics;

import java.util.ArrayList;
import java.util.Arrays;

public class AutoSmelter implements CustomSkyBlockItem, NotFinishedYet {
    @Override
    public ItemStatistics getStatistics() {
        return null;
    }
    @Override
    public ArrayList<String> getLore(SkyBlockPlayer player, SkyBlockItem item) {
        return new ArrayList<>(Arrays.asList(
                "§7This item can be used as a",
                "§7minion upgrade. This will",
                "§7automatically smelt materials",
                "§7that a minion produces."));
    }
}
