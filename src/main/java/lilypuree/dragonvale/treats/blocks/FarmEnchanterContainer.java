package lilypuree.dragonvale.treats.blocks;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.maptags.ItemToIntMapTags;
import lilypuree.dragonvale.maptags.MapTag;
import lilypuree.dragonvale.setup.registry.DragonvaleBlocks;
import lilypuree.dragonvale.setup.registry.DragonvaleItems;
import lilypuree.dragonvale.setup.registry.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

//Code from https://github.com/Cadiboo/Example-Mod/blob/1.15.2/src/main/java/io/github/cadiboo/examplemod/container/ModFurnaceContainer.java

public class FarmEnchanterContainer extends Container {

    private static List<MapTag<Item, Integer>> enchantableCrops;

    static {
        enchantableCrops = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            enchantableCrops.add(
                    new ItemToIntMapTags.Wrapper(new ResourceLocation(DragonvaleMain.MODID, "enchant_require_tier" + i))
            );
        }
    }

    private final CraftResultInventory outputSlot = new CraftResultInventory();
    private final ItemStackHandler inputSlots = new ItemStackHandler(2) {


        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            FarmEnchanterContainer.this.updateEnchantmentOutput();
        }
    };
//    private int consumeAmount = 0;
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;

    public static FarmEnchanterContainer getClientContainer(final int windowId, PlayerInventory playerInventoryIn, final PacketBuffer extraData) {
        return new FarmEnchanterContainer(windowId, playerInventoryIn, IWorldPosCallable.DUMMY);
    }

    public FarmEnchanterContainer(int id, PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallable) {
        super(Registration.FARM_ENCHANTER_CONTAINER.get(), id);
        this.worldPosCallable = worldPosCallable;
        this.player = playerInventoryIn.player;
        this.addSlot(new SlotItemHandler(inputSlots, 0, 27, 47));
        this.addSlot(new SlotItemHandler(inputSlots, 1, 76, 47));
        this.addSlot(new Slot(outputSlot, 2, 134, 47) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            @Override
            public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {

                FarmEnchanterContainer.this.inputSlots.extractItem(0, 1, false);

                return super.onTake(thePlayer, stack);
            }
        });

        this.addPlayerInventoryToContainer(playerInventoryIn, 8, 84);

    }

    public void updateEnchantmentOutput() {
        ItemStack itemStack = this.inputSlots.getStackInSlot(0);
        if (itemStack.isEmpty()) {
            this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
        } else {
            ItemStack crop = itemStack.copy();
            ItemStack enchantingAgent = this.inputSlots.getStackInSlot(1);
            boolean flag = false;

            for (int tier = 1; tier <= 5 && !flag; tier++) {
                MapTag<Item, Integer> mapTag = enchantableCrops.get(tier - 1);
                if (mapTag.containsKey(crop.getItem())) {
                    int requirement = mapTag.getValue(crop.getItem());
                    if (crop.getCount() >= requirement) {
//                        int treats = crop.getCount()/requirement;
//                        this.consumeAmount = treats * requirement;
                        this.outputSlot.setInventorySlotContents(0, new ItemStack(DragonvaleItems.TREATS.get(tier - 1).get()));
                        flag = true;
                    }
                }
            }
            if(!flag) {
                this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
//                this.consumeAmount = 0;
            }
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume(((world, pos) -> {
            clearContainer(player, world, inputSlots);
        }));
    }

    public void clearContainer(PlayerEntity player, World world, IItemHandler itemHandler) {
        if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity) player).hasDisconnected()) {
            for (int j = 0; j < itemHandler.getSlots(); ++j) {
                player.dropItem(itemHandler.getStackInSlot(j), false);
            }
        } else {
            for (int i = 0; i < itemHandler.getSlots(); ++i) {
                player.inventory.placeItemBackInInventory(world, itemHandler.getStackInSlot(i));
            }

        }
    }

    public void addPlayerInventoryToContainer(final PlayerInventory playerInventory, int playerInventoryStartX, int playerInventoryStartY) {
        final int slotSizePlus2 = 18; // slots are 16x16, plus 2 (for spacing/borders) is 18x18
        // Player Top Inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
            }
        }
        final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
        // Player Hotbar slots
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, DragonvaleBlocks.FARM_ENCHANTER.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }
}
