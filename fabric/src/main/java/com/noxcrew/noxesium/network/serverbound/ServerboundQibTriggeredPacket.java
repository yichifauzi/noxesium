package com.noxcrew.noxesium.network.serverbound;

import com.noxcrew.noxesium.network.NoxesiumPackets;
import com.noxcrew.noxesium.network.NoxesiumPayloadType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * Sent to the server to inform it that the client just triggered a qib interaction.
 */
public record ServerboundQibTriggeredPacket(String behavior, Type qibType, int entityId) implements ServerboundNoxesiumPacket {
    public static final StreamCodec<FriendlyByteBuf, ServerboundQibTriggeredPacket> STREAM_CODEC = CustomPacketPayload.codec(ServerboundQibTriggeredPacket::write, ServerboundQibTriggeredPacket::new);

    /**
     * The type of qib interaction the client triggered.
     */
    public enum Type {
        JUMP,
        INSIDE,
        ENTER,
        LEAVE
    }

    private ServerboundQibTriggeredPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readEnum(Type.class), buf.readVarInt());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeUtf(behavior);
        buf.writeEnum(qibType);
        buf.writeVarInt(entityId);
    }

    @Override
    public NoxesiumPayloadType<?> noxesiumType() {
        return NoxesiumPackets.QIB_TRIGGERED;
    }
}
