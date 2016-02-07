/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.connection.packets.login.serverbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginServerboundListener;

@PacketClass(id = 0x01, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.SERVERBOUND, size = 260)
public class PacketLoginServerboundEncryptionBegin extends PacketLoginServerbound
{
    private byte[] sharedSecret; // 128 bytes + 2 bytes size
    private byte[] verifyToken; // 128 bytes + 2 bytes size

    public PacketLoginServerboundEncryptionBegin()
    {
    }

    public PacketLoginServerboundEncryptionBegin(final byte[] sharedSecret, final byte[] verifyToken)
    {
        this.sharedSecret = sharedSecret;
        this.verifyToken = verifyToken;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.sharedSecret = data.readByteWord();
        this.verifyToken = data.readByteWord();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByteWord(this.sharedSecret);
        data.writeByteWord(this.verifyToken);
    }

    @Override
    public void handle(final PacketLoginServerboundListener listener)
    {
        listener.handle(this);
    }

    public byte[] getSharedSecret()
    {
        return this.sharedSecret;
    }

    public void setSharedSecret(final byte[] sharedSecret)
    {
        this.sharedSecret = sharedSecret;
    }

    public byte[] getVerifyToken()
    {
        return this.verifyToken;
    }

    public void setVerifyToken(final byte[] verifyToken)
    {
        this.verifyToken = verifyToken;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("sharedSecret", this.sharedSecret).append("verifyToken", this.verifyToken).toString();
    }
}
