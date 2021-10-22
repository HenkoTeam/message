package dev.henko.message.bungee;

import dev.henko.message.api.Messenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.MessengerProvider;
import dev.henko.message.api.impl.DefaultMessenger;
import dev.henko.message.api.impl.MessengerConfigImpl;
import dev.henko.message.bungee.source.YamlSource;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMessengerProvider
  implements MessengerProvider<String> {

  private final Plugin plugin;
  private final String sourceName;

  public BungeeMessengerProvider(
    Plugin plugin,
    String sourceName
  ) {
    this.plugin = plugin;
    this.sourceName = sourceName;
  }

  @Override
  public Messenger<String> get() {
    MessengerConfig<String> config = new MessengerConfigImpl<>();
    config.setSource(new YamlSource(plugin, sourceName))
      .addInterceptor(message -> ChatColor.translateAlternateColorCodes('&', message))
      .addEntity(CommandSender.class, (entity, message) ->
        entity.sendMessage(TextComponent.fromLegacyText(message))
      )
      .addEntity(ProxiedPlayer.class, (entity, message) ->
          entity.sendMessage(TextComponent.fromLegacyText(message))
      );
    return new DefaultMessenger(config);
  }
}
