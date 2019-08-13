package com.jsorrell.blockowater.common.config;

import com.jsorrell.blockowater.BlockOWater;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PacketConfigSync implements IMessage {
  public Map<String, Object> fieldMap = null;

  public PacketConfigSync() { }

  @Override
  public void toBytes(ByteBuf buf) {
    ByteArrayOutputStream obj = new ByteArrayOutputStream();

    try {
      GZIPOutputStream gzip = new GZIPOutputStream(obj);
      ObjectOutputStream objStream = new ObjectOutputStream(gzip);
      objStream.writeObject(ConfigSettings.getSettingsMap(true));
      objStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    buf.writeShort(obj.size());
    buf.writeBytes(obj.toByteArray());
  }

  @Override
  @SuppressWarnings("unchecked")
  public void fromBytes(ByteBuf buf) {
    short size = buf.readShort();
    byte[] body = new byte[size];
    buf.readBytes(body, 0, size);

    try {
      ObjectInputStream obj = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(body)));
      fieldMap = (Map<String, Object>) obj.readObject();
      obj.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static class Handler implements IMessageHandler<PacketConfigSync, IMessage> {
    @Override
    public IMessage onMessage(PacketConfigSync message, MessageContext ctx) {
      BlockOWater.logger.info("Received config sync message");
      if (message.fieldMap == null) {
        BlockOWater.logger.warn("fieldMap null in " + PacketConfigSync.class.getName());
      } else {
        ConfigProcessor.INSTANCE.serverConfig = Collections.unmodifiableMap(message.fieldMap);
        if (ConfigProcessor.INSTANCE.clientConfig == null) {
          ConfigProcessor.INSTANCE.clientConfig = Collections.unmodifiableMap(ConfigSettings.getSettingsMap(true));
        }
        ConfigSettings.loadSettingsMap(ConfigProcessor.INSTANCE.serverConfig);
      }

      return null;
    }
  }
}
