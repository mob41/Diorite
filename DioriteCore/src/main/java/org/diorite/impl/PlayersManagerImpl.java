package org.diorite.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.mojang.authlib.GameProfile;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.NetworkManager;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutAbilities;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutCustomPayload;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutHeldItemSlot;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutKeepAlive;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutLogin;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutPosition;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutServerDifficulty;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSpawnPosition;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.world.world.WorldImpl;
import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.TeleportData;
import org.diorite.entity.Player;
import org.diorite.world.Dimension;
import org.diorite.world.WorldType;

import io.netty.buffer.Unpooled;

public class PlayersManagerImpl
{
    public static final int                   KEEP_ALIVE_TIMER = 15;
    private final       Map<UUID, PlayerImpl> players          = new ConcurrentHashMap<>(100, 0.2f, 8);
    private final ServerImpl server;

    private transient long lastKeepAlive = System.currentTimeMillis();

    private final WorldImpl world = new WorldImpl("world", UUID.randomUUID()); // testing

    public PlayersManagerImpl(final ServerImpl server)
    {
        this.server = server;
    }

    public PlayerImpl createPlayer(final GameProfile gameProfile, final NetworkManager networkManager)
    {// TODO: loading player
        final PlayerImpl player = new PlayerImpl(this.server, this.server.entityManager.getNextID(), gameProfile, networkManager, new ImmutableLocation(4, 71, - 4, 0, 0, this.world));
        this.players.put(gameProfile.getId(), player);
        return player;
    }

    public void playerJoin(final PlayerImpl player)
    {

        // TODO: this is only test code
        player.getNetworkManager().sendPacket(new PacketPlayOutLogin(player.getId(), GameMode.SURVIVAL, false, Dimension.OVERWORLD, Difficulty.PEACEFUL, 20, WorldType.FLAT));
        player.getNetworkManager().sendPacket(new PacketPlayOutCustomPayload("MC|Brand", new PacketDataSerializer(Unpooled.buffer()).writeText(this.server.getServerModName())));
        player.getNetworkManager().sendPacket(new PacketPlayOutServerDifficulty(Difficulty.EASY));
        player.getNetworkManager().sendPacket(new PacketPlayOutSpawnPosition(new BlockLocation(2, 71, - 2)));
        player.getNetworkManager().sendPacket(new PacketPlayOutAbilities(false, false, false, false, Player.WALK_SPEED, Player.FLY_SPEED));
        player.getNetworkManager().sendPacket(new PacketPlayOutHeldItemSlot(3));
        player.getNetworkManager().sendPacket(new PacketPlayOutPosition(new TeleportData(4, 71, - 4)));
    }

    public List<String> getOnlinePlayersNames()
    {
        return this.players.values().parallelStream().map(PlayerImpl::getName).collect(Collectors.toList());
    }

    public Map<UUID, PlayerImpl> getRawPlayers()
    {
        return this.players;
    }

    public void playerQuit(final PlayerImpl player)
    {
        this.playerQuit(player.getUniqueID());
    }

    public void playerQuit(final UUID uuid)
    {
        this.players.remove(uuid);
    }

    public void keepAlive()
    {
        final long curr = System.currentTimeMillis();
        if ((curr - this.lastKeepAlive) > TimeUnit.SECONDS.toMillis(KEEP_ALIVE_TIMER))
        {
            this.players.values().parallelStream().forEach(p -> p.getNetworkManager().sendPacket(new PacketPlayOutKeepAlive(p.getId())));
            this.lastKeepAlive = curr;
        }
    }

    public void forEach(final Packet<?> packet)
    {
        this.forEach(player -> player.getNetworkManager().sendPacket(packet));
    }

    public void forEach(final Predicate<PlayerImpl> predicate, final Packet<?> packet)
    {
        this.forEach(predicate, player -> player.getNetworkManager().sendPacket(packet));
    }

    public void forEach(final Consumer<PlayerImpl> consumer)
    {
        this.players.values().parallelStream().forEach(consumer);
    }

    public void forEach(final Predicate<PlayerImpl> predicate, final Consumer<PlayerImpl> consumer)
    {
        this.players.values().parallelStream().filter(predicate).forEach(consumer);
    }

    public ServerImpl getServer()
    {
        return this.server;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("players", this.players).append("server", this.server).toString();
    }
}
