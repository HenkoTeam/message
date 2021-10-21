package dev.henko.message.bukkit.replacer;

import dev.henko.message.api.MessageReplacer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ComponentMessageReplacer
  implements MessageReplacer<TextComponent> {

  @Override
  public @NotNull TextComponent replace(TextComponent message, Object... replacements) {
    if(replacements.length % 2 != 0) {
      return message;
    }

    String legacyMessage = message.content();
    List<TextComponent> newChildren = new ArrayList<>();

    for(int i = 0; i < replacements.length; i+=2) {
      legacyMessage = legacyMessage.replace(
        replacements[i].toString(),
        replacements[i+1].toString()
      );
      for(Component children : message.children()) {
        newChildren.add(
          Component.text(
            ((TextComponent)children)
              .content()
              .replace(
                replacements[i].toString(),
                replacements[i+1].toString()
              )));
      }
    }

    return Component
      .text(legacyMessage)
      .children(newChildren);
  }
}
