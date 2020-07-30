package lilypuree.dragonvale.setup;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.core.DVBlockProperties;
import lilypuree.dragonvale.setup.registry.DragonvaleBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonvaleMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandlers {

    @SubscribeEvent
    public static void onCropGrow(BlockEvent.CropGrowEvent.Post event){
        IWorld world = event.getWorld();
        BlockState farmland = world.getBlockState(event.getPos().down());
        BlockState growing = event.getState();
        if(farmland.getBlock() == DragonvaleBlocks.ENCHANTED_FARMLAND.get()){
            if(growing.getBlock() instanceof CropsBlock){
                CropsBlock cropBlock = (CropsBlock) growing.getBlock();
                if(cropBlock.isMaxAge(growing)){
                    world.setBlockState(event.getPos().down(), farmland.with(DVBlockProperties.MAGICAL, false), 3);
                }
            }
        }
    }


}
