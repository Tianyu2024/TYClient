package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C11PacketEnchantItem implements Packet<INetHandlerPlayServer> {
    private int windowId;
    private int button;

    public C11PacketEnchantItem() {
    }

    public C11PacketEnchantItem(int windowId, int button) {
        this.windowId = windowId;
        this.button = button;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayServer handler) {
        handler.processEnchantItem(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.windowId = buf.readByte();
        this.button = buf.readByte();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeByte(this.windowId);
        buf.writeByte(this.button);
    }

    public int getWindowId() {
        return this.windowId;
    }

    public int getButton() {
        return this.button;
    }
}
