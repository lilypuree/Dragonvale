package lilypuree.dragonvale.maptags;

import net.minecraft.item.Item;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ItemToIntMapTags {
    private static MapTagCollection<Item, Integer> collection = new MapTagCollection<>(rl -> Optional.empty(), ValueHandlers.INT_VALUE_HANDLER, "", false, "");
    private static int generation;

    public static final MapTag<Item, Integer> ENCHANT_REQUIRE_TIER1 = makeWrapperTag("enchant_require_tier1");

    public static void setCollection(MapTagCollection<Item, Integer> collectionIn) {
        collection = collectionIn;
        generation++;
    }

    public static MapTagCollection<Item, Integer> getCollection() {
        return collection;
    }

    public static MapTag<Item, Integer> makeWrapperTag(String id) {
        return new ItemToIntMapTags.Wrapper(new ResourceLocation(id));
    }

    public static class Wrapper extends MapTag<Item, Integer> {
        private int lastKnownGeneration = -1;
        private MapTag<Item, Integer> cachedTag;

        public Wrapper(ResourceLocation resourceLocationIn) {
            super(resourceLocationIn);
        }

        @Override
        public boolean containsKey(Item keyIn) {
            cache();
            return this.cachedTag.containsKey(keyIn);
        }

        @Override
        public Map<Item, Integer> getMap() {
            cache();
            return this.cachedTag.getMap();
        }

        @Override
        public Collection<IMapTagEntry<Item, Integer>> getEntries() {
            cache();
            return this.cachedTag.getEntries();
        }

        @Override
        public Integer getValue(Item keyIn) {
            cache();
            return this.cachedTag.getValue(keyIn);
        }

        private void cache() {
            if (this.lastKnownGeneration != ItemToIntMapTags.generation) {
                this.cachedTag = ItemToIntMapTags.collection.getOrCreate(this.getId());
                this.lastKnownGeneration = ItemToIntMapTags.generation;
            }
        }
    }
}
