package lilypuree.dragonvale.maptags;

import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class NetworkMapTagManager implements IFutureReloadListener {

    private final NetworkMapTagCollection<Item, Integer> itemToInts = new NetworkMapTagCollection<>(Registry.ITEM, ValueHandlers.INT_VALUE_HANDLER, "maptags/itemtoints", "item");

    public NetworkMapTagCollection<Item, Integer> getItemToInts() {
        return itemToInts;
    }

    public void write(PacketBuffer buffer) {
        this.itemToInts.write(buffer);
    }

    public static NetworkMapTagManager read(PacketBuffer buffer) {
        NetworkMapTagManager networkMapTagManager = new NetworkMapTagManager();
        networkMapTagManager.getItemToInts().read(buffer);
        return networkMapTagManager;
    }

    @Override
    public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        CompletableFuture<Map<ResourceLocation, MapTag.Builder<Item, Integer>>> completableFuture = this.itemToInts.reload(resourceManager, backgroundExecutor);
        return completableFuture.thenApply(result -> {
            return new NetworkMapTagManager.ReloadResults(result);
        }).thenCompose(stage::markCompleteAwaitingOthers).thenAcceptAsync(result -> {
            this.itemToInts.registerAll(result.itemToInts);
            ItemToIntMapTags.setCollection(this.itemToInts);
        }, gameExecutor);
    }

    public static class ReloadResults {
        final Map<ResourceLocation, MapTag.Builder<Item, Integer>> itemToInts;

        public ReloadResults(Map<ResourceLocation, MapTag.Builder<Item, Integer>> itemToIntegers) {
            this.itemToInts = itemToIntegers;
        }
    }
}
