package dev.henko.message.bukkit;

import dev.henko.message.api.Messenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.MessengerProvider;
import dev.henko.message.api.impl.MessengerConfigImpl;
import dev.henko.message.adventure.messenger.AdventureMessenger;
import dev.henko.message.bukkit.source.YamlSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AdventureMessengerProvider
  implements MessengerProvider<Component> {

  private final Plugin plugin;
  private final String sourceName;
  private final BukkitAudiences bukkitAudiences;
  private final MiniMessage miniMessage;
  private final LegacyComponentSerializer componentSerializer;

  public AdventureMessengerProvider(
    Plugin plugin,
    String sourceName,
    BukkitAudiences bukkitAudiences,
    MiniMessage miniMessage,
    LegacyComponentSerializer componentSerializer

  ) {
    this.plugin = plugin;
    this.sourceName = sourceName;
    this.bukkitAudiences = bukkitAudiences;
    this.miniMessage = miniMessage;
    this.componentSerializer = componentSerializer;
  }

  @Override
  public Messenger<Component> get() {
    MessengerConfig<Component> config = new MessengerConfigImpl<>();
    config.setSource(new YamlSource(plugin, sourceName))
      .addEntity(CommandSender.class, (entity, message) -> {
        if(entity instanceof Player) {
          Audience audience = bukkitAudiences.player((Player) entity);
          audience.sendMessage(message);
        } else {
          entity.sendMessage(componentSerializer.serialize(message));
        }
      })
      .addEntity(Player.class, (entity, message) -> {
        Audience audience = bukkitAudiences.player(entity);
        audience.sendMessage(message);
      })
      .addEntity(Audience.class, (Audience::sendMessage));

    return new AdventureMessenger(config, miniMessage);
  }
}
