package net.swofty.commons.skyblock.item.items.enchanted;

import net.swofty.commons.skyblock.item.ItemType;
import net.swofty.commons.skyblock.item.impl.Enchanted;
import net.swofty.commons.skyblock.item.impl.SkyBlockRecipe;

public class EnchantedBirchWood implements Enchanted {
    @Override
    public ItemType getCraftingMaterial() {
        return ItemType.BIRCH_WOOD;
    }

    @Override
    public SkyBlockRecipe.RecipeType getRecipeType() {
        return SkyBlockRecipe.RecipeType.FORAGING;
    }
}