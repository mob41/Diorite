package org.diorite.impl.connection.packets.play.client;

import static org.diorite.impl.connection.packets.play.server.PacketPlayServerAbilities.FLYING_FLAG;
import static org.diorite.impl.connection.packets.play.server.PacketPlayServerAbilities.FLY_FLAG;
import static org.diorite.impl.connection.packets.play.server.PacketPlayServerAbilities.INSTANTLY_BUILD_FLAG;
import static org.diorite.impl.connection.packets.play.server.PacketPlayServerAbilities.INVULNERABLE_FLAG;


import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x13, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 9)
public class PacketPlayClientAbilities extends PacketPlayClient
{
    private boolean isInvulnerable; // 1/4 byte
    private boolean isFlying; // 1/4 byte
    private boolean canFly; // 1/4 byte
    private boolean canInstantlyBuild; // 1/4 byte
    private float   flyingSpeed; // 4 bytes
    private float   walkingSpeed; // 4 bytes

    public PacketPlayClientAbilities()
    {
    }

    public PacketPlayClientAbilities(final boolean isInvulnerable, final boolean isFlying, final boolean canFly, final boolean canInstantlyBuild, final float walkingSpeed, final float flyingSpeed)
    {
        this.isInvulnerable = isInvulnerable;
        this.isFlying = isFlying;
        this.canFly = canFly;
        this.canInstantlyBuild = canInstantlyBuild;
        this.walkingSpeed = walkingSpeed;
        this.flyingSpeed = flyingSpeed;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        final byte flags = data.readByte();
        this.isInvulnerable = ((flags & INVULNERABLE_FLAG) > 0);
        this.isFlying = ((flags & FLYING_FLAG) > 0);
        this.canFly = ((flags & FLY_FLAG) > 0);
        this.canInstantlyBuild = ((flags & INSTANTLY_BUILD_FLAG) > 0);
        this.flyingSpeed = data.readFloat();
        this.walkingSpeed = data.readFloat();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        byte flags = 0;
        if (this.isInvulnerable)
        {
            flags |= INVULNERABLE_FLAG;
        }
        if (this.isFlying)
        {
            flags |= FLYING_FLAG;
        }
        if (this.canFly)
        {
            flags |= FLY_FLAG;
        }
        if (this.canInstantlyBuild)
        {
            flags |= INSTANTLY_BUILD_FLAG;
        }
        data.writeByte(flags);
        data.writeFloat(this.flyingSpeed);
        data.writeFloat(this.walkingSpeed);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public boolean isInvulnerable()
    {
        return this.isInvulnerable;
    }

    public void setInvulnerable(final boolean isInvulnerable)
    {
        this.isInvulnerable = isInvulnerable;
    }

    public float getWalkingSpeed()
    {
        return this.walkingSpeed;
    }

    public void setWalkingSpeed(final float walkingSpeed)
    {
        this.walkingSpeed = walkingSpeed;
    }

    public float getFlyingSpeed()
    {
        return this.flyingSpeed;
    }

    public void setFlyingSpeed(final float flyingSpeed)
    {
        this.flyingSpeed = flyingSpeed;
    }

    public boolean isCanInstantlyBuild()
    {
        return this.canInstantlyBuild;
    }

    public void setCanInstantlyBuild(final boolean canInstantlyBuild)
    {
        this.canInstantlyBuild = canInstantlyBuild;
    }

    public boolean isCanFly()
    {
        return this.canFly;
    }

    public void setCanFly(final boolean canFly)
    {
        this.canFly = canFly;
    }

    public boolean isFlying()
    {
        return this.isFlying;
    }

    public void setFlying(final boolean isFlying)
    {
        this.isFlying = isFlying;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("isInvulnerable", this.isInvulnerable).append("isFlying", this.isFlying).append("canFly", this.canFly).append("canInstantlyBuild", this.canInstantlyBuild).append("flyingSpeed", this.flyingSpeed).append("walkingSpeed", this.walkingSpeed).toString();
    }
}
