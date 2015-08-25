package org.diorite.impl.connection.packets.handshake.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.handshake.PacketHandshakingClientListener;
import org.diorite.impl.connection.packets.handshake.RequestType;

@PacketClass(id = 0x00, protocol = EnumProtocol.HANDSHAKING, direction = EnumProtocolDirection.SERVERBOUND, size = 50)
public class PacketHandshakingClientSetProtocol extends PacketHandshakingClient
{
    private int         protocolVersion; // ~2 bytes
    private String      serverAddress; // ~40 bytes
    private int         serverPort; // 2 bytes
    private RequestType requestType; // 2 bytes

    public PacketHandshakingClientSetProtocol()
    {
    }

    public static void main(String[] args)
    {
        System.out.println("diorite.org.se.f.d.f.geefr.frgdrdfvs".getBytes(StandardCharsets.UTF_8).length);
    }

    public PacketHandshakingClientSetProtocol(final int protocolVersion, final RequestType requestType, final int serverPort, final String serverAddress)
    {
        this.protocolVersion = protocolVersion;
        this.requestType = requestType;
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.protocolVersion = data.readVarInt();
        this.serverAddress = data.readText(Short.MAX_VALUE);
        this.serverPort = data.readUnsignedShort();
        this.requestType = RequestType.getByInt(data.readVarInt());
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.protocolVersion);
        data.writeText(this.serverAddress);
        data.writeShort(this.serverPort); // ?
        data.writeVarInt(this.requestType.getValue());
    }

    @Override
    public void handle(final PacketHandshakingClientListener listener)
    {
        listener.handle(this);
    }

    public int getProtocolVersion()
    {
        return this.protocolVersion;
    }

    public void setProtocolVersion(final int protocolVersion)
    {
        this.protocolVersion = protocolVersion;
    }

    public RequestType getRequestType()
    {
        return this.requestType;
    }

    public void setRequestType(final RequestType requestType)
    {
        this.requestType = requestType;
    }

    public int getServerPort()
    {
        return this.serverPort;
    }

    public void setServerPort(final int serverPort)
    {
        this.serverPort = serverPort;
    }

    public String getServerAddress()
    {
        return this.serverAddress;
    }

    public void setServerAddress(final String serverAddress)
    {
        this.serverAddress = serverAddress;
    }

    @Override
    public int hashCode()
    {
        int result = this.protocolVersion;
        result = (31 * result) + ((this.serverAddress != null) ? this.serverAddress.hashCode() : 0);
        result = (31 * result) + this.serverPort;
        result = (31 * result) + ((this.requestType != null) ? this.requestType.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PacketHandshakingClientSetProtocol))
        {
            return false;
        }

        final PacketHandshakingClientSetProtocol that = (PacketHandshakingClientSetProtocol) o;

        return (this.protocolVersion == that.protocolVersion) && (this.serverPort == that.serverPort) && (this.requestType == that.requestType) && ! ((this.serverAddress != null) ? ! this.serverAddress.equals(that.serverAddress) : (that.serverAddress != null));

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("protocolVersion", this.protocolVersion).append("serverAddress", this.serverAddress).append("serverPort", this.serverPort).append("requestType", this.requestType).toString();
    }
}
