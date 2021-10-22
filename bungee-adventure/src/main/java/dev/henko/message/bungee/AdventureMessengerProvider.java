package dev.henko.message.bungee;

import dev.henko.message.adventure.messenger.AdventureMessenger;
import dev.henko.message.api.Messenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.MessengerProvider;
import dev.henko.message.bungee.source.YamlSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class AdventureMessengerProvider
  implements MessengerProvider<Component> {

  private final Plugin plugin;
  private final String sourceName;
  private final BungeeAudiences bungeeAudiences;
  private final MiniMessage miniMessage;

  public AdventureMessengerProvider(
    Plugin plugin,
    String sourceName,
    BungeeAudiences bungeeAudiences,
    MiniMessage miniMessage

  ) {
    this.plugin = plugin;
    this.sourceName = sourceName;
    this.bungeeAudiences = bungeeAudiences;
    this.miniMessage = miniMessage;
  }

  @Override
  public Messenger<Component> get() {
    MessengerConfig<Component> config = new MessengerConfig<>();
    config.setSource(new YamlSource(plugin, sourceName));

    config.addEntity(CommandSender.class)
      .setSender((entity, message) -> {
        Audience audience;
        if(entity instanceof ProxiedPlayer) {
          audience = bungeeAudiences.player((ProxiedPlayer) entity);
        } else {
          audience = bungeeAudiences.sender(entity);
        }
        audience.sendMessage(message);
      });
    config.addEntity(ProxiedPlayer.class)
      .setSender((entity, message) -> {
        Audience audience = bungeeAudiences.player(entity);
        audience.sendMessage(message);
      });
    config.addEntity(Audience.class)
      .setSender(Audience::sendMessage);

    return new AdventureMessenger(config, miniMessage);
  }
}
