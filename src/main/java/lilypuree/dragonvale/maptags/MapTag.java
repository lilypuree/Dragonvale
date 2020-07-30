package lilypuree.dragonvale.maptags;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class MapTag<K, V> {
    private final ResourceLocation resourceLocation;
    private final Map<K, V> taggedItems;
    private final Collection<MapTag.IMapTagEntry<K, V>> entries;
    private boolean replace = false;

    public MapTag(ResourceLocation resourceLocationIn) {
        this.resourceLocation = resourceLocationIn;
        this.taggedItems = Collections.emptyMap();
        this.entries = Collections.emptyList();
    }

    public MapTag(ResourceLocation resourceLocationIn, Collection<MapTag.IMapTagEntry<K, V>> entriesIn, boolean preserveOrder) {
        this(resourceLocationIn, entriesIn, preserveOrder, false);
    }

    private MapTag(ResourceLocation resourceLocationIn, Collection<MapTag.IMapTagEntry<K, V>> entriesIn, boolean preserveOrder, boolean replace) {
        this.resourceLocation = resourceLocationIn;
        this.taggedItems = (Map<K, V>) (preserveOrder ? new LinkedHashMap<K, V>() : new HashMap<K, V>());
        this.entries = entriesIn;
        for (MapTag.IMapTagEntry<K, V> imaptagentry : entriesIn) {
            imaptagentry.populate(this.taggedItems);
        }
    }

    public JsonObject serialize(Function<K, ResourceLocation> getNameForKey, ValueHandler<V> valueHandler) {
        JsonObject jsonobject = new JsonObject();
        JsonArray jsonarray = new JsonArray();
        JsonArray optional = new JsonArray();

        for (MapTag.IMapTagEntry<K, V> imaptagentry : this.entries) {
            if (!(imaptagentry instanceof IOptionalMapTagEntry))
                imaptagentry.serialize(jsonarray, getNameForKey, valueHandler);
        }
        for (MapTag.IMapTagEntry<K, V> imaptagentry : this.entries) {
            if (imaptagentry instanceof IOptionalMapTagEntry)
                imaptagentry.serialize(optional, getNameForKey, valueHandler);
        }
        jsonobject.addProperty("replace", replace);
        jsonobject.add("entries", jsonarray);
        if (optional.size() > 0) jsonobject.add("optional", optional);
        return jsonobject;
    }

    public boolean containsKey(K keyIn) {
        return this.taggedItems.containsKey(keyIn);
    }

    public V getValue(K keyIn) {
        return this.taggedItems.get(keyIn);
    }

    public Map<K, V> getMap() {
        return this.taggedItems;
    }

    public Collection<MapTag.IMapTagEntry<K, V>> getEntries() {
        return this.entries;
    }

    public ResourceLocation getId() {
        return this.resourceLocation;
    }

    public static class Builder<K, V> {
        private final Set<MapTag.IMapTagEntry<K, V>> entries = new LinkedHashSet<>();
        private boolean preserveOrder;
        private boolean replace = false;

        public static <K, V> MapTag.Builder<K, V> create() {
            return new MapTag.Builder<>();
        }

        public MapTag.Builder<K, V> add(MapTag.IMapTagEntry<K, V> entry) {
            this.entries.add(entry);
            return this;
        }

        public MapTag.Builder<K, V> add(K key, V value) {
            this.entries.add(new MapTag.MapEntry<>(Collections.singletonMap(key, value)));
            return this;
        }

        public MapTag.Builder<K, V> add(Map<K, V> map) {
            this.entries.add(new MapTag.MapEntry<>(map));
            return this;
        }

        public MapTag.Builder<K, V> add(MapTag<K, V> mapTagIn) {
            this.entries.add(new MapTag.MapTagEntry<>(mapTagIn));
            return this;
        }

        public MapTag.Builder<K, V> addOptional(final Function<ResourceLocation, Optional<K>> entryLookup, final Map<ResourceLocation, V> optionalValues) {
            this.add(new MapTag.OptionalMapTagEntry<K, V>(entryLookup, optionalValues));
            return this;
        }

        public final MapTag.Builder<K, V> addOptionalMapTag(final MapTagCollection<K, V> collection, @SuppressWarnings("unchecked") final MapTag<K, V>... mapTags) {
            for (MapTag<K, V> mapTag : mapTags) {
                addOptionalMapTag(mapTag.getId());
            }
            return this;
        }

        public final MapTag.Builder<K, V> addOptionalMapTag(final ResourceLocation... tags) {
            for (ResourceLocation rl : tags)
                addOptionalMapTag(rl);
            return this;
        }

        public MapTag.Builder<K, V> addOptionalMapTag(ResourceLocation mapTag) {
            this.add(new MapTagTarget<>(mapTag));
            return this;
        }

        @SafeVarargs
        public final MapTag.Builder<K, V> add(MapTag<K, V>... mapTags) {
            for (MapTag<K, V> mapTag : mapTags) {
                add(mapTag);
            }
            return this;
        }

        public MapTag.Builder<K, V> replace(boolean value) {
            this.replace = value;
            return this;
        }

        public MapTag.Builder<K, V> replace() {
            return replace(true);
        }

        public MapTag.Builder<K, V> ordered(boolean preserveOrderIn) {
            this.preserveOrder = preserveOrderIn;
            return this;
        }

        public boolean resolve(Function<ResourceLocation, MapTag<K, V>> resourceLocationToMapTag) {
            for (MapTag.IMapTagEntry<K, V> iMapTagEntry : this.entries) {
                if (!iMapTagEntry.resolve(resourceLocationToMapTag)) {
                    return false;
                }
            }
            return true;
        }

        public MapTag<K, V> build(ResourceLocation resourceLocationIn) {
            return new MapTag<>(resourceLocationIn, this.entries, this.preserveOrder, this.replace);
        }

        public MapTag.Builder<K, V> fromJson(Function<ResourceLocation, Optional<K>> keyResolver, ValueHandler<V> valueHandler, JsonObject jsonObject) {
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "entries");
            List<MapTag.IMapTagEntry<K, V>> list = new ArrayList<>();
            for (JsonElement jsonElement : jsonArray) {
                if (JSONUtils.isString(jsonElement)) {
                    String s = JSONUtils.getString(jsonElement, "entry");
                    if (s.startsWith("#")) {
                        list.add(new MapTag.MapTagEntry<>(new ResourceLocation(s.substring(1))));
                    }
                } else {
                    JsonObject entry = JSONUtils.getJsonObject(jsonElement, "entry");
                    Map<K, V> entryMap = new LinkedHashMap<>();
                    for (Map.Entry<String, JsonElement> element : entry.entrySet()) {
                        ResourceLocation resourceLocation = new ResourceLocation(element.getKey());
                        V value = valueHandler.readFromJson(element.getValue());
                        entryMap.put(keyResolver.apply(resourceLocation).orElseThrow(() -> {
                            return new JsonParseException("Unknown value '" + resourceLocation + "'");
                        }), value);
                    }
                    list.add(new MapTag.MapEntry<>(entryMap));
                }
            }
            if (JSONUtils.getBoolean(jsonObject, "replace", false)) {
                this.entries.clear();
            }
            if (jsonObject.has("optional")) {
                for (JsonElement jsonElement : JSONUtils.getJsonArray(jsonObject, "optional")) {
                    if (JSONUtils.isString(jsonElement)) {
                        String s = JSONUtils.getString(jsonElement, "entry");
                        if (s.startsWith("#")) {
                            list.add(new MapTag.MapTagTarget<>(new ResourceLocation(s.substring(1))));
                        }
                    } else {
                        JsonObject entry = JSONUtils.getJsonObject(jsonElement, "entry");
                        Map<ResourceLocation, V> optionalMap = new LinkedHashMap<>();
                        for (Map.Entry<String, JsonElement> element : entry.entrySet()) {
                            ResourceLocation resourceLocation = new ResourceLocation(element.getKey());
                            V value = valueHandler.readFromJson(element.getValue());
                            optionalMap.put(resourceLocation, value);
                        }
                        list.add(new MapTag.OptionalMapTagEntry<>(keyResolver, optionalMap));
                    }
                }
            }
            this.entries.addAll(list);
            return this;
        }
    }

    public interface IMapTagEntry<K, V> {
        default boolean resolve(Function<ResourceLocation, MapTag<K, V>> resourceLocationToMapTag) {
            return true;
        }

        void populate(Map<K, V> entriesIn);

        void serialize(JsonArray array, Function<K, ResourceLocation> getNameForKey, ValueHandler<V> valueHandler);
    }

    public interface IOptionalMapTagEntry<K, V> extends IMapTagEntry<K, V> {
    }

    public static class MapEntry<K, V> implements MapTag.IMapTagEntry<K, V> {
        public final Map<K, V> taggedItems;

        public MapEntry(Map<K, V> taggedItemsIn) {
            this.taggedItems = taggedItemsIn;
        }

        @Override
        public void populate(Map<K, V> entriesIn) {
            entriesIn.putAll(this.taggedItems);
        }

        @Override
        public void serialize(JsonArray array, Function<K, ResourceLocation> getNameForKey, ValueHandler<V> valueHandler) {
            JsonObject jsonObject = new JsonObject();
            this.taggedItems.forEach((key, value) -> {
                ResourceLocation resourceLocation = getNameForKey.apply(key);
                if (resourceLocation == null) {
                    throw new IllegalStateException("Unable to serialize an anonymous value to json!");
                }
                jsonObject.add(resourceLocation.toString(), valueHandler.parseToJson(value));
            });
            array.add(jsonObject);
        }

        public Map<K, V> getTaggedItems() {
            return this.taggedItems;
        }

        @Override
        public int hashCode() {
            return this.taggedItems.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this || (obj instanceof MapTag.MapEntry && this.taggedItems.equals(((MapEntry) obj).taggedItems));
        }
    }

    public static class MapTagEntry<K, V> implements MapTag.IMapTagEntry<K, V> {
        @Nullable
        private final ResourceLocation id;
        @Nullable
        protected MapTag<K, V> mapTag;

        public MapTagEntry(ResourceLocation resourceLocationIn) {
            this.id = resourceLocationIn;
        }

        public MapTagEntry(MapTag<K, V> mapTagIn) {
            this.id = mapTagIn.getId();
            this.mapTag = mapTagIn;
        }

        @Override
        public boolean resolve(Function<ResourceLocation, MapTag<K, V>> resourceLocationToMapTag) {
            if (this.mapTag == null) {
                this.mapTag = resourceLocationToMapTag.apply(this.id);
            }
            return this.mapTag != null;
        }

        @Override
        public void populate(Map<K, V> entriesIn) {
            if (this.mapTag == null) {
                throw Util.pauseDevMode((new IllegalStateException("Cannot build unresolved mapTag entry")));
            } else {
                entriesIn.putAll(this.mapTag.getMap());
            }
        }

        public ResourceLocation getSerializedId() {
            if (this.mapTag != null) {
                return this.mapTag.getId();
            } else if (this.id != null) {
                return this.id;
            } else {
                throw new IllegalStateException("Cannot serialize an anonymous tag to json!");
            }
        }

        @Override
        public void serialize(JsonArray array, Function<K, ResourceLocation> getNameForKey, ValueHandler<V> valueHandler) {
            array.add("#" + this.getSerializedId());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.id);
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this || (obj instanceof MapTagEntry && Objects.equals(this.id, ((MapTagEntry) obj).id));
        }
    }

    public static class OptionalMapTagEntry<K, V> implements MapTag.IOptionalMapTagEntry<K, V> {
        private final Function<ResourceLocation, Optional<K>> keyResolver;
        private final Map<ResourceLocation, V> optionalMap;

        public OptionalMapTagEntry(Function<ResourceLocation, Optional<K>> keyResolver, Map<ResourceLocation, V> optionalMap) {
            this.keyResolver = keyResolver;
            this.optionalMap = optionalMap;
        }

        @Override
        public void populate(Map<K, V> entriesIn) {
            optionalMap.forEach((rl, value) -> {
                Optional<K> optionalKey = keyResolver.apply(rl);
                optionalKey.ifPresent(key -> {
                    entriesIn.put(key, value);
                });
            });
        }

        @Override
        public void serialize(JsonArray array, Function<K, ResourceLocation> getNameForKey, ValueHandler<V> valueHandler) {
            JsonObject entry = new JsonObject();
            optionalMap.forEach((rl, value) -> {
                entry.add(rl.toString(), valueHandler.parseToJson(value));
            });
            array.add(entry);
        }

    }

    public static class MapTagTarget<K, V> extends MapTag.MapTagEntry<K, V> implements IOptionalMapTagEntry<K, V> {
        protected MapTagTarget(ResourceLocation referent) {
            super(referent);
        }

        @Override
        public boolean resolve(Function<ResourceLocation, MapTag<K, V>> resourceLocationToMapTag) {
            if (this.mapTag == null)
                this.mapTag = resourceLocationToMapTag.apply(this.getSerializedId());
            return true;
        }

        @Override
        public void populate(Map<K, V> entriesIn) {
            if (this.mapTag != null)
                entriesIn.putAll(this.mapTag.getMap());
        }
    }
}
