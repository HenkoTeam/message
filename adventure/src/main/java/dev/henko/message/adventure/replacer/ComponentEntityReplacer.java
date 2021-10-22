package dev.henko.message.adventure.replacer;

import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.entity.EntityResolver;
import dev.henko.message.api.entity.placeholder.EntityPlaceholderHolder;
import dev.henko.message.api.entity.placeholder.StringPlaceholderObtainer;
import dev.henko.message.api.replacer.EntityReplacer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class ComponentEntityReplacer
  implements EntityReplacer<Component> {

  private final LegacyComponentSerializer componentSerializer;
  private final MessengerConfig<Component> config;

  public ComponentEntityReplacer(
    MessengerConfig<Component> config
  ) {
    this.componentSerializer = LegacyComponentSerializer.builder().build();
    this.config = config;
  }

  @Override
  public @NotNull Component replace(Component message, Object... entities) {
    for(Object entity : entities) {
      Class<?> entityType = entity.getClass();
      EntityResolver<?, Component> resolver = config
        .getEntityResolver(entityType);
      if(resolver == null) {
        continue;
      }
      EntityPlaceholderHolder<?> placeholderHolder = resolver
        .getPlaceholderHolder();
      if(placeholderHolder == null) {
        continue;
      }

      Matcher matcher = placeholderHolder
        .getPattern()
        .matcher(componentSerializer.serialize(message));

      while(matcher.find()) {

        @RegExp String group = matcher.group();
        String placeholder = StringPlaceholderObtainer
          .from(group, placeholderHolder.getPrefix());

        Object o = placeholderHolder.replace(entity, placeholder);

        if(o != null) {
          TextReplacementConfig.Builder replacementConfig = TextReplacementConfig
            .builder()
            .match(group);
          if(o instanceof Component) {
            replacementConfig.replacement((Component) o);
          } else {
            replacementConfig.replacement(o.toString());
          }
          message = message.replaceText(replacementConfig.build());
        }
      }
    }
    return message;
  }
}
