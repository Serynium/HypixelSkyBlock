package net.swofty.types.generic.item.items.farming.vanilla;

import net.swofty.types.generic.item.ItemType;
import net.swofty.types.generic.item.MaterialQuantifiable;
import net.swofty.types.generic.item.SkyBlockItem;
import net.swofty.types.generic.item.impl.*;
import net.swofty.types.generic.item.impl.recipes.ShapedRecipe;
import net.swofty.types.generic.user.statistics.ItemStatistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IronHoe implements CustomSkyBlockItem, StandardItem, DefaultCraftable, Sellable {

    @Override
    public ItemStatistics getStatistics(SkyBlockItem instance) {
        return ItemStatistics.EMPTY;
    }

    @Override
    public SkyBlockRecipe<?> getRecipe() {
        Map<Character, MaterialQuantifiable> ingredientMap = new HashMap<>();
        ingredientMap.put('A', new MaterialQuantifiable(ItemType.IRON_INGOT, 1));
        ingredientMap.put('B', new MaterialQuantifiable(ItemType.STICK, 1));
        ingredientMap.put(' ', new MaterialQuantifiable(ItemType.AIR, 1));
        List<String> pattern = List.of(
                "AA",
                " B",
                " B");

        return new ShapedRecipe(SkyBlockRecipe.RecipeType.NONE, new SkyBlockItem(ItemType.IRON_HOE), ingredientMap, pattern);
    }

    @Override
    public double getSellValue() {
        return 3;
    }

    @Override
    public StandardItemType getStandardItemType() {
        return StandardItemType.HOE;
    }
}
