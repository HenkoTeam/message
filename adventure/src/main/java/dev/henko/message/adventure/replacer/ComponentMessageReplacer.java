package dev.henko.message.adventure.replacer;

import dev.henko.message.api.MessageReplacer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;

public class ComponentMessageReplacer
  implements MessageReplacer<Component> {

  @Override
  public @NotNull Component replace(Component message, Object... replacements) {
    if(replacements.length % 2 != 0) {
      return message;
    }

    for(int i = 0; i < replacements.length; i+=2) {
      Object o = replacements[i+1];
      TextReplacementConfig.Builder replacementConfig = TextReplacementConfig
        .builder()
        .match(replacements[i].toString());
      if(o instanceof Component) {
        replacementConfig.replacement((Component) o);
      } else {
        replacementConfig.replacement(o.toString());
      }
      message = message.replaceText(replacementConfig.build());
    }

    return message;
  }
}
