package lilypuree.dragonvale.maptags;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.network.PacketBuffer;

public class ValueHandlers {
    public static ValueHandler<Integer> INT_VALUE_HANDLER = new ValueHandler<Integer>() {
        @Override
        public Integer readFromJson(JsonElement jsonElement) {
           return jsonElement.getAsInt();
        }

        @Override
        public Integer readFromBuffer(PacketBuffer buf) {
            return buf.readVarInt();
        }

        @Override
        public JsonElement parseToJson(Integer value) {
            return new JsonPrimitive(value);
        }

        @Override
        public void writeToBuffer(PacketBuffer buf, Integer value) {
            buf.writeVarInt(value);
        }
    };
}
