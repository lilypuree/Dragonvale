package lilypuree.dragonvale.dragons;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;

public enum DragonGrowthStages {

    BABY, JUVENILE, ADULT;


    public static final IDataSerializer<DragonGrowthStages> GROWTH_STAGE_SERIALIZER = new IDataSerializer<DragonGrowthStages>() {
        @Override
        public void write(PacketBuffer buf, DragonGrowthStages value) {
            buf.writeByte(value.ordinal());
        }

        @Override
        public DragonGrowthStages read(PacketBuffer buf) {
            return DragonGrowthStages.values()[buf.readByte()];
        }

        @Override
        public DragonGrowthStages copyValue(DragonGrowthStages value) {
            return value;
        }
    };

    static {
        DataSerializers.registerSerializer(GROWTH_STAGE_SERIALIZER);
    }
}
