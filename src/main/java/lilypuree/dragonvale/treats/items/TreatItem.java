package lilypuree.dragonvale.treats.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TreatItem extends Item {

    private int tier;

    public TreatItem(Item.Properties properties, int tier){
        super(properties);
        this.tier = tier;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    public int getGrowthPower(){
        return (int)Math.pow(8, tier - 1);
    }

    public int getTier() {
        return tier;
    }
}
