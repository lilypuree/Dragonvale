package lilypuree.dragonvale.maptags;

import com.google.gson.JsonElement;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class NetworkMapTagCollection<K, V> extends MapTagCollection<K, V> {
    private final Registry<K> registry;

    public NetworkMapTagCollection(Registry<K> keyRegistry, ValueHandler<V> valueHandler, String resourceLocationPrefix, String itemTypeName) {
        super(keyRegistry::getValue, valueHandler, resourceLocationPrefix, false, itemTypeName);
        this.registry = keyRegistry;
    }

    public void write(PacketBuffer buf) {
        Map<ResourceLocation, MapTag<K, V>> mapTagMap = this.getMapTagMap();
        buf.writeVarInt(mapTagMap.size());

        for (Map.Entry<ResourceLocation, MapTag<K, V>> rlToMapTag : mapTagMap.entrySet()) {
            buf.writeResourceLocation(rlToMapTag.getKey());
            Map<K, V> map = rlToMapTag.getValue().getMap();
            buf.writeVarInt(map.size());
            for (Map.Entry<K, V> element : map.entrySet()) {
                buf.writeVarInt(this.registry.getId(element.getKey()));
                valueHandler.writeToBuffer(buf, element.getValue());
            }
        }
    }

    public void read(PacketBuffer buf) {
        Map<ResourceLocation, MapTag<K, V>> mapTagMap = new HashMap<>();
        int len = buf.readVarInt();
        for (int i = 0; i < len; ++i) {
            ResourceLocation resourceLocation = buf.readResourceLocation();
            int entryLen = buf.readVarInt();
            MapTag.Builder<K, V> builder = MapTag.Builder.create();
            for (int j = 0; j < entryLen; j++) {
                builder.add(this.registry.getByValue(buf.readVarInt()), valueHandler.readFromBuffer(buf));
            }
            mapTagMap.put(resourceLocation, builder.build(resourceLocation));
        }
        this.toImmutable(mapTagMap);
    }
}
