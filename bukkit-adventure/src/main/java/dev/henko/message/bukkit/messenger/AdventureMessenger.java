package dev.henko.message.bukkit.messenger;

import dev.henko.message.api.AbstractMessenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.source.Source;
import dev.henko.message.bukkit.replacer.ComponentMessageReplacer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdventureMessenger
  extends AbstractMessenger<TextComponent> {

  private final MiniMessage miniMessage;

  public AdventureMessenger(
    MessengerConfig<TextComponent> config,
    MiniMessage miniMessage
  ) {
    super(new ComponentMessageReplacer(), config);
    this.miniMessage = miniMessage;
  }


  @Override
  public @NotNull TextComponent get(String path, Object... replacements) {
    Source source = config.getSource();
    String legacyResult = source.getString(path);
    if(legacyResult == null) {
      return Component.text(path);
    }
    TextComponent result = (TextComponent) miniMessage.parse(path);
    TextComponent finalResult = replacer.replace(result, replacements);
    config.getInterceptors()
      .forEach(interceptor -> interceptor.intercept(finalResult));
    return finalResult;
  }

  @Override
  public @NotNull List<TextComponent> getMany(String path, Object... replacements) {
    Source source = config.getSource();
    List<String> legacyResult = source.getStringList(path);
    if(legacyResult == null || legacyResult.isEmpty()) {
      return Collections.singletonList(Component.text(path));
    }

    List<TextComponent> result = new ArrayList<>();
    legacyResult.forEach(r -> {
      result.add((TextComponent) miniMessage.parse(r));
    });
    config.getInterceptors()
      .forEach(interceptor -> interceptor.intercept(result));
    replacer.replace(result, replacements);
    return result;
  }
}
