package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S09PacketHeldItemChange implements Packet<INetHandlerPlayClient> {
    private int heldItemHotbarIndex;

    public S09PacketHeldItemChange() {
    }

    public S09PacketHeldItemChange(int hotbarIndexIn) {
        this.heldItemHotbarIndex = hotbarIndexIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.heldItemHotbarIndex = buf.readByte();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeByte(this.heldItemHotbarIndex);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleHeldItemChange(this);
    }

    public int getHeldItemHotbarIndex() {
        return this.heldItemHotbarIndex;
    }
}
