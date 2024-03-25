package net.swofty.types.generic.item.items.combat.mythological.craftable;

import net.swofty.types.generic.gems.Gemstone;
import net.swofty.types.generic.item.ItemType;
import net.swofty.types.generic.item.MaterialQuantifiable;
import net.swofty.types.generic.item.SkyBlockItem;
import net.swofty.types.generic.item.impl.*;
import net.swofty.types.generic.item.impl.recipes.ShapedRecipe;
import net.swofty.types.generic.user.SkyBlockPlayer;
import net.swofty.types.generic.user.statistics.ItemStatistics;

import java.util.*;

public class DaedalusAxe implements CustomSkyBlockItem, Craftable, GemstoneItem, SwordImpl {
    @Override
    public SkyBlockRecipe<?> getRecipe() {
        Map<Character, MaterialQuantifiable> ingredientMap = new HashMap<>();
        ingredientMap.put('A', new MaterialQuantifiable(ItemType.ENCHANTED_GOLD_BLOCK, 16));
        ingredientMap.put('B', new MaterialQuantifiable(ItemType.DAEDALUS_STICK, 1));
        ingredientMap.put(' ', new MaterialQuantifiable(ItemType.AIR, 1));
        List<String> pattern = List.of(
                "AA ",
                "AB ",
                " B ");

        return new ShapedRecipe(SkyBlockRecipe.RecipeType.SPECIAL, new SkyBlockItem(ItemType.DAEDALUS_AXE), ingredientMap, pattern);
    }

    @Override
    public ItemStatistics getStatistics() {
        return ItemStatistics.EMPTY;
    }

    @Override
    public ArrayList<String> getLore(SkyBlockPlayer player, SkyBlockItem item) {
        return new ArrayList<>(Arrays.asList(
                "§7§7Gains §c+4 Damage §7per Taming",
                "§7level.",
                "§7§7Copies the stats from your",
                "§7active pet.",
                "",
                "§7§7Earn §6+35 coins §7from",
                "§7monster kills",
                "",
                "§7Deals §a+200% §7damage against",
                "§7mythological creatures and Minos",
                "§7followers"));
    }

    @Override
    public Map<Gemstone.Slots, Integer> getGemstoneSlots() {
        return Map.of(
                Gemstone.Slots.COMBAT, 50000 //+20 Fine Jasper, Sapphire, Ruby and Amethyst
                //Gemstone.Slots.COMBAT, 100000 //+40 Fine Jasper, Sapphire, Ruby and Amethyst; commented out cause throws an error, Issue #142
        );
    }
}