package dev.henko.message.bukkit;

import dev.henko.message.api.Messenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.MessengerProvider;
import dev.henko.message.adventure.messenger.AdventureMessenger;
import dev.henko.message.bukkit.source.YamlSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AdventureMessengerProvider
  implements MessengerProvider<Component> {

  private final Plugin plugin;
  private final String sourceName;
  private final BukkitAudiences bukkitAudiences;
  private final MiniMessage miniMessage;

  public AdventureMessengerProvider(
    Plugin plugin,
    String sourceName,
    BukkitAudiences bukkitAudiences,
    MiniMessage miniMessage

  ) {
    this.plugin = plugin;
    this.sourceName = sourceName;
    this.bukkitAudiences = bukkitAudiences;
    this.miniMessage = miniMessage;
  }

  @Override
  public Messenger<Component> get() {
    MessengerConfig<Component> config = new MessengerConfig<>();
    config.setSource(new YamlSource(plugin, sourceName));

    config.addEntity(CommandSender.class)
      .setSender((entity, message) -> {
        Audience audience;
        if(entity instanceof Player) {
          audience = bukkitAudiences.player((Player) entity);
        } else {
          audience = bukkitAudiences.sender(entity);
        }
        audience.sendMessage(message);
      });

    config.addEntity(Player.class)
      .setSender((entity, message) -> {
        Audience audience = bukkitAudiences.player(entity);
        audience.sendMessage(message);
      });

    config.addEntity(Audience.class)
      .setSender(Audience::sendMessage);

    return new AdventureMessenger(config, miniMessage);
  }
}
