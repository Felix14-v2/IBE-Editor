package com.github.franckyi.ibeeditor.forge.common;

import com.github.franckyi.gameadapter.api.common.IPacketBuffer;
import com.github.franckyi.gameadapter.api.common.IPlayer;
import com.github.franckyi.ibeeditor.base.common.Network;
import com.github.franckyi.ibeeditor.base.common.Packet;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class ForgeNetwork implements Network {
    public static final Network INSTANCE = new ForgeNetwork();

    private final String VERSION = "2";
    private final SimpleChannel channel = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("ibeeditor:network"),
            () -> VERSION,
            VERSION::equals,
            VERSION::equals
    );

    private ForgeNetwork() {
    }

    @Override
    public void sendToServer(String id, Packet packet) {
        channel.sendToServer(packet);
    }

    @Override
    public void sendToClient(String id, IPlayer player, Packet packet) {
        channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), packet);
    }

    @Override
    public <P extends Packet> void registerServerHandler(String id, int id1, Class<P> msgClass, PacketReader<P> reader, ServerPacketHandler<P> handler) {
        registerHandler(id1, msgClass, reader, (msg, ctx) -> handler.accept(msg, (IPlayer) ctx.get().getSender()));
    }

    @Override
    public <P extends Packet> void registerClientHandler(String id, int id1, Class<P> msgClass, PacketReader<P> reader, ClientPacketHandler<P> handler) {
        registerHandler(id1, msgClass, reader, (msg, ctx) -> handler.accept(msg));
    }

    private <P extends Packet> void registerHandler(int id, Class<P> msgClass, PacketReader<P> reader, BiConsumer<P, Supplier<NetworkEvent.Context>> handler) {
        channel.messageBuilder(msgClass, id)
                .decoder(buffer -> reader.read((IPacketBuffer) buffer))
                .encoder((p, buffer) -> p.write((IPacketBuffer) buffer))
                .consumer((msg, ctx) -> {
                    ctx.get().enqueueWork(() -> handler.accept(msg, ctx));
                    ctx.get().setPacketHandled(true);
                }).add();
    }
}
