package net.swofty.types.generic.event.actions.player;

import net.kyori.adventure.text.Component;
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.swofty.commons.item.ItemType;
import net.swofty.types.generic.collection.CustomCollectionAward;
import net.swofty.types.generic.data.datapoints.DatapointQuiver;
import net.swofty.types.generic.event.EventNodes;
import net.swofty.types.generic.event.SkyBlockEvent;
import net.swofty.types.generic.event.SkyBlockEventClass;
import net.swofty.types.generic.gui.inventory.ItemStackCreator;
import net.swofty.types.generic.item.SkyBlockItem;
import net.swofty.types.generic.item.components.ArrowComponent;
import net.swofty.types.generic.item.components.QuiverDisplayComponent;
import net.swofty.types.generic.item.updater.NonPlayerItemUpdater;
import net.swofty.types.generic.item.updater.PlayerItemUpdater;
import net.swofty.types.generic.user.SkyBlockPlayer;
import net.swofty.commons.StringUtility;

import java.util.List;

public class ActionPlayerChangeSkyBlockMenuDisplay implements SkyBlockEventClass {

    @SkyBlockEvent(node = EventNodes.PLAYER , requireDataLoaded = false)
    public void run(PlayerChangeHeldSlotEvent event) {
        SkyBlockPlayer player = (SkyBlockPlayer) event.getPlayer();
        runCheck(player);
    }

    public static void runCheck(SkyBlockPlayer player) {
        SkyBlockItem switchedTo = new SkyBlockItem(player.getItemInMainHand());
        if (switchedTo.isNA() || switchedTo.toConfigurableItem() == null) {
            setMainMenu(player);
            return;
        }

        // Check if item shows quiver
        if (switchedTo.hasComponent(QuiverDisplayComponent.class)) {
            DatapointQuiver.PlayerQuiver quiver = player.getQuiver();
            QuiverDisplayComponent quiverDisplay = switchedTo.getComponent(QuiverDisplayComponent.class);

            // If the bow should not be drawn back then also replace all arrows in inventory with a feather
            if (!quiverDisplay.isShouldBeArrow()) {
                for (int index = 0; index < player.getInventory().getSize(); index++) {
                    SkyBlockItem item = new SkyBlockItem(player.getInventory().getItemStack(index));
                    if (item.hasComponent(ArrowComponent.class)) {
                        player.getInventory().setItemStack(index, ItemStack.builder(Material.FEATHER)
                                .set(ItemComponent.CUSTOM_DATA, item.getItemStack().get(ItemComponent.CUSTOM_DATA))
                                .set(ItemComponent.CUSTOM_NAME, Component.text("§cSwitch your held item for this item!"))
                                .amount(item.getAmount()).build());
                    }
                }
            } else {
                setMainMenu(player);
            }

            if (!player.hasCustomCollectionAward(CustomCollectionAward.QUIVER)) return;

            ItemStack.Builder builder;
            if (player.getQuiver().isEmpty()) {
                builder = ItemStackCreator.getStack("§8Empty Quiver", Material.FEATHER, 1, List.of(
                        "§7This item is in your inventory",
                        "§7because you are currently holding a",
                        "§7Bow",
                        " ",
                        "§cQuiver is empty",
                        " ",
                        "§7Switch away from your Bow to see",
                        "§7the item that was here before."
                ));
            } else {
                SkyBlockItem item = quiver.getFirstItemInQuiver();
                builder = ItemStackCreator.getStack("§8Quiver " + StringUtility.stripColor(item.getDisplayName()),
                        quiverDisplay.isShouldBeArrow() ? Material.ARROW : Material.FEATHER,
                        Math.min(64, quiver.getAmountOfArrows(item.getAttributeHandler().getPotentialType())),
                        "§7This item is in your inventory",
                        "§7because you are currently holding a",
                        "§7Bow",
                        " ",
                        "§7Active Arrow: " + item.getDisplayName()
                                + " §7(§e" + quiver.getAmountOfArrows(item.getAttributeHandler().getPotentialType()) + "§7)",
                        " ",
                        "§7Switch away from your Bow to see",
                        "§7the item that was here before.");
            }

            player.getInventory().setItemStack(8, ItemStackCreator.setNotEditable(builder).build());
            return;
        }

        setMainMenu(player);
    }

    public static void setMainMenu(SkyBlockPlayer player) {
        for (int index = 0; index < player.getInventory().getSize(); index++) {
            SkyBlockItem item = new SkyBlockItem(player.getInventory().getItemStack(index));
            if (item.hasComponent(ArrowComponent.class)) {
                player.getInventory().setItemStack(index,
                        PlayerItemUpdater.playerUpdate(player, item.getItemStack())
                                .build());
            }
        }

        player.getInventory().setItemStack(8,
                new NonPlayerItemUpdater(new SkyBlockItem(ItemType.SKYBLOCK_MENU).getItemStack())
                        .getUpdatedItem().build());
    }
}
