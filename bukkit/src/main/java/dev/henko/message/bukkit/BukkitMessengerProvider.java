package dev.henko.message.bukkit;

import dev.henko.message.api.Messenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.MessengerProvider;
import dev.henko.message.api.impl.DefaultMessenger;
import dev.henko.message.bukkit.source.YamlSource;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BukkitMessengerProvider
  implements MessengerProvider<String> {

  private final Plugin plugin;
  private final String sourceName;

  public BukkitMessengerProvider(
    Plugin plugin,
    String sourceName
  ) {
    this.plugin = plugin;
    this.sourceName = sourceName;
  }

  @Override
  public Messenger<String> get() {
    MessengerConfig<String> config = new MessengerConfig<>();
    config.setSource(new YamlSource(plugin, sourceName))
      .addInterceptor(message -> ChatColor.translateAlternateColorCodes('&', message))
      .addEntity(CommandSender.class, CommandSender::sendMessage)
      .addEntity(Player.class, Player::sendMessage);
    return new DefaultMessenger(config);
  }
}
