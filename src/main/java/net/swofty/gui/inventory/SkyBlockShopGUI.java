package net.swofty.gui.inventory;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.swofty.data.DataHandler;
import net.swofty.data.datapoints.DatapointDouble;
import net.swofty.gui.inventory.item.GUIClickableItem;
import net.swofty.item.MaterialQuantifiable;
import net.swofty.item.SkyBlockItem;
import net.swofty.item.updater.NonPlayerItemUpdater;
import net.swofty.user.SkyBlockPlayer;
import net.swofty.user.statistics.ItemStatistics;
import net.swofty.utility.PaginationList;
import net.swofty.utility.StringUtility;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SkyBlockShopGUI extends SkyBlockInventoryGUI
{
      private static final int[] INTERIOR = new int[]{
              10, 11, 12, 13, 14, 15, 16,
              19, 20, 21, 22, 23, 24, 25,
              28, 29, 30, 31, 32, 33, 34,
              37, 38, 39, 40, 41, 42, 43
      };

      private final List<ShopItem> shopItemList;
      private int page;

      public SkyBlockShopGUI(String title, int page) {
            super(title, InventoryType.CHEST_6_ROW);
            this.shopItemList = new ArrayList<>();
            this.page = page;
      }

      public SkyBlockShopGUI(String title) {
            this(title, 1);
      }

      @Override
      public void onOpen(InventoryGUIOpenEvent e) {
            initializeShopItems();
            border(ItemStackCreator.createNamedItemStack(Material.BLACK_STAINED_GLASS_PANE, " "));
            PaginationList<ShopItem> paginatedItems = new PaginationList<>(INTERIOR.length);
            paginatedItems.addAll(shopItemList);

            updateItemStacks(e.inventory(), getPlayer());

            if (paginatedItems.size() == 0) page = 0;
            int finalPage = page;
            if (page > 1)
                  set(new GUIClickableItem() {
                        @Override
                        public void run(InventoryPreClickEvent e, SkyBlockPlayer player) {
                              SkyBlockShopGUI.this.page -= 1;
                              SkyBlockShopGUI.this.open(player);
                        }

                        @Override
                        public int getSlot() {
                              return 45;
                        }

                        @Override
                        public ItemStack.Builder getItem(SkyBlockPlayer player) {
                              return ItemStackCreator.createNamedItemStack(Material.ARROW, "§a<-");
                        }
                  });

            if (page != paginatedItems.getPageCount())
                  set(new GUIClickableItem() {
                        @Override
                        public void run(InventoryPreClickEvent e, SkyBlockPlayer player) {
                              SkyBlockShopGUI.this.page += 1;
                              SkyBlockShopGUI.this.open(player);
                        }

                        @Override
                        public int getSlot() {
                              return 53;
                        }

                        @Override
                        public ItemStack.Builder getItem(SkyBlockPlayer player) {
                              return ItemStackCreator.createNamedItemStack(Material.ARROW, "§a->");
                        }
                  });

            List<ShopItem> p = paginatedItems.getPage(page);
            if (p == null) return;
            for (int i = 0; i < p.size(); i++) {
                  int slot = INTERIOR[i];
                  ShopItem item = p.get(i);
                  SkyBlockItem sbItem = item.item;
                  ItemStack.Builder itemStack = new NonPlayerItemUpdater(sbItem).getUpdatedItem();
                  List<Component> lore = new ArrayList<>(itemStack.build().getLore());


                  lore.add(Component.text(""));
                  lore.add(Component.text("§7Cost"));
                  double price = item.price * item.amount;
                  double stackPrice = price / item.modifier;
                  if (stackPrice < 1) {
                        stackPrice = 1;
                  }
                  lore.add(Component.text("§6 " + StringUtility.commaify(price) + " Coin" + (price != 1 ? "s" : "")));
                  lore.add(Component.text(""));
                  lore.add(Component.text("§eClick to trade!"));
                  if (item.stackable)
                        lore.add(Component.text("§eRight-click for more trading options!"));

                  itemStack.lore(lore);
                  itemStack.amount(item.amount);
                  int finalI = i;
                  double finalStackPrice = stackPrice;

                  set(new GUIClickableItem()
                  {
                        @Override
                        public void run(InventoryPreClickEvent e, SkyBlockPlayer player) {
                              double purse = player.getDataHandler().get(DataHandler.Data.COINS, DatapointDouble.class).getValue();
                              if (price > purse) {
                                    player.sendMessage("§cYou don't have enough coins!");
                                    return;
                              }
                              ItemStack.Builder cleanStack = new NonPlayerItemUpdater(sbItem).getUpdatedItem();
                              cleanStack.amount(item.amount);
                              player.getInventory().addItemStack(cleanStack.build());
                              player.playSound(Sound.sound(Key.key("block.note_block.pling"), Sound.Source.PLAYER, 1.0f, 2.0f));
                              player.getDataHandler().get(DataHandler.Data.COINS, DatapointDouble.class).setValue(purse - price);
                        }

                        @Override
                        public int getSlot() {
                              return slot;
                        }

                        @Override
                        public ItemStack.Builder getItem(SkyBlockPlayer player) {
                              return itemStack;
                        }
                  });
            }
            updateItemStacks(e.inventory(), getPlayer());
      }

      @Override
      public void onBottomClick(InventoryPreClickEvent e) {
            ItemStack stack = e.getClickedItem();
            if (stack.getMaterial().equals(Material.AIR)) return;
      }

      public abstract void initializeShopItems();

      public void attachItem(ShopItem i) {
            shopItemList.add(i);
      }

      public record ShopItem(SkyBlockItem item, int amount, double price, double modifier, boolean stackable) {
            public static ShopItem Stackable(SkyBlockItem item, int amount, double price, double modifier) {
                  return new ShopItem(item, amount, price, modifier, true);
            }

            public static ShopItem Single(SkyBlockItem item, int amount, double price, double modifier) {
                  return new ShopItem(item, amount, price, modifier, false);
            }
      }
}