package lilypuree.dragonvale.maptags;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tags.Tag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

//TODO
//Update based on TagCollection!
public class MapTagCollection<K, V> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();
    private static final int JSON_EXTENSION_LENGTH = ".json".length();
    private Map<ResourceLocation, MapTag<K, V>> mapTagMap = ImmutableMap.of();
    private final Function<ResourceLocation, Optional<K>> resourceLocationToKey;
    protected final ValueHandler<V> valueHandler;
    private final String resourceLocationPrefix;
    private final boolean preserveOrder;
    private final String itemTypeName;

    public MapTagCollection(Function<ResourceLocation, Optional<K>> entryLookup, ValueHandler<V> valueHandler, String resourceLocationPrefix, boolean preserveOrderIn, String itemTypeName) {
        this.resourceLocationToKey = entryLookup;
        this.valueHandler = valueHandler;
        this.resourceLocationPrefix = resourceLocationPrefix;
        this.preserveOrder = preserveOrderIn;
        this.itemTypeName = itemTypeName;
    }

    @Nullable
    public MapTag<K, V> get(ResourceLocation resourceLocationIn) {
        return this.mapTagMap.get(resourceLocationIn);
    }

    public MapTag<K, V> getOrCreate(ResourceLocation resourceLocationIn) {
        MapTag<K, V> tag = this.mapTagMap.get(resourceLocationIn);
        return tag == null ? new MapTag<>(resourceLocationIn) : tag;
    }

    public Collection<ResourceLocation> getRegisteredTags() {
        return this.mapTagMap.keySet();
    }

    public Collection<ResourceLocation> getOwningTags(K keyIn) {
        List<ResourceLocation> list = new ArrayList<>();
        for (Map.Entry<ResourceLocation, MapTag<K, V>> entry : this.mapTagMap.entrySet()) {
            if (entry.getValue().containsKey(keyIn)) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    public CompletableFuture<Map<ResourceLocation, MapTag.Builder<K, V>>> reload(IResourceManager resourceManager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            Map<ResourceLocation, MapTag.Builder<K, V>> map = new HashMap<>();
            for (ResourceLocation tempLocation : resourceManager.getAllResourceLocations(this.resourceLocationPrefix, string -> {
                return string.endsWith(".json");
            })) {
                String s = tempLocation.getPath();
                ResourceLocation rl = new ResourceLocation(tempLocation.getNamespace(), s.substring(this.resourceLocationPrefix.length() + 1, s.length() - JSON_EXTENSION_LENGTH));

                try {
                    for (IResource iresource : resourceManager.getAllResources(tempLocation)) {
                        try (
                                InputStream inputStream = iresource.getInputStream();
                                Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        ) {
                            JsonObject jsonobject = JSONUtils.fromJson(GSON, reader, JsonObject.class);
                            if (jsonobject == null) {
                                LOGGER.error("Couldn't load {} maptag list {} from {} in data pack {} as it's empty or null", this.itemTypeName, rl, tempLocation, iresource.getPackName());
                            } else {
                                map.computeIfAbsent(rl, resourcelocation -> {
                                    return Util.make(MapTag.Builder.create(), builder -> {
                                        builder.ordered(this.preserveOrder);
                                    });
                                }).fromJson(this.resourceLocationToKey, valueHandler, jsonobject);
                            }
                        } catch (RuntimeException | IOException ioexception) {
                            LOGGER.error("Couldn't read {} maptag list {} from {} in data pack {}", this.itemTypeName, rl, tempLocation, iresource.getPackName(), ioexception);
                        } finally {
                            IOUtils.closeQuietly((Closeable) iresource);
                        }
                    }
                } catch (IOException ioexception1) {
                    LOGGER.error("Couldn't read {} maptag list {} from {}", this.itemTypeName, rl, tempLocation, ioexception1);
                }
            }
            return map;
        }, executor);
    }

    public void registerAll(Map<ResourceLocation, MapTag.Builder<K, V>> mapTagBuilderMap) {
        Map<ResourceLocation, MapTag<K, V>> map = new HashMap<>();
        while (!mapTagBuilderMap.isEmpty()) {
            boolean flag = false;
            Iterator<Map.Entry<ResourceLocation, MapTag.Builder<K, V>>> iterator = mapTagBuilderMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<ResourceLocation, MapTag.Builder<K, V>> entry = iterator.next();
                MapTag.Builder<K, V> builder = entry.getValue();
                if (builder.resolve(map::get)) {
                    flag = true;
                    ResourceLocation resourceLocation = entry.getKey();
                    map.put(resourceLocation, builder.build(resourceLocation));
                    iterator.remove();
                    ;
                }
            }

            if (!flag) {
                mapTagBuilderMap.forEach((resourceLocation, builder) -> {
                    LOGGER.error("Couldn't load {} tag {} as it either references another tag that doesn't exist, or ultimately references itself", this.itemTypeName, resourceLocation);
                });
                break;
            }
        }
        mapTagBuilderMap.forEach(((resourceLocation, builder) -> {
            MapTag mapTag = map.put(resourceLocation, builder.build(resourceLocation));
        }));
        this.toImmutable(map);
    }

    protected void toImmutable(Map<ResourceLocation, MapTag<K, V>> mapTagMap) {
        this.mapTagMap = ImmutableMap.copyOf(mapTagMap);
    }

    public Map<ResourceLocation, MapTag<K, V>> getMapTagMap() {
        return mapTagMap;
    }

    public Function<ResourceLocation, Optional<K>> getEntryLookup() {
        return resourceLocationToKey;
    }
}
