package dev.henko.message.bukkit;

import dev.henko.message.api.Messenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.MessengerProvider;
import dev.henko.message.api.impl.MessengerConfigImpl;
import dev.henko.message.bukkit.messenger.AdventureMessenger;
import dev.henko.message.bukkit.source.YamlSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AdventureMessengerProvider
  implements MessengerProvider<TextComponent> {

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
  public Messenger<TextComponent> get() {
    MessengerConfig<TextComponent> config = new MessengerConfigImpl<>();
    config.setSource(new YamlSource(plugin, sourceName))
      .addEntity(CommandSender.class, (entity, message) -> {
        if(entity instanceof Player) {
          Audience audience = bukkitAudiences.player((Player) entity);
          audience.sendMessage(message);
        } else {
          StringBuilder legacyMessage = new StringBuilder(message.content());
          message.children().forEach(c -> {
            legacyMessage.append(((TextComponent) c).content());
          });
          entity.sendMessage(legacyMessage.toString());
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
