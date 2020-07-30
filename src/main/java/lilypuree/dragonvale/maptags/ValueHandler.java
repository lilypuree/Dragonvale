package lilypuree.dragonvale.maptags;

import com.google.gson.JsonElement;
import net.minecraft.network.PacketBuffer;

public interface ValueHandler<V> {

    V readFromJson(JsonElement jsonElement);

    V readFromBuffer(PacketBuffer buf);

    JsonElement parseToJson(V value);

    void writeToBuffer(PacketBuffer buf, V value);
}
