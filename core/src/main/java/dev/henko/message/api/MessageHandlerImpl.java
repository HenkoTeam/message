package dev.henko.message.api;

import dev.henko.message.api.config.ConfigurationHandle;
import dev.henko.message.api.entity.EntityResolver;
import dev.henko.message.api.entity.send.EntitySender;
import dev.henko.message.api.format.MessageInterceptor;
import dev.henko.message.api.format.replacer.EntityReplacer;
import dev.henko.message.api.format.replacer.Replacer;
import dev.henko.message.api.format.replacer.StringReplacer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MessageHandlerImpl
    implements MessageHandler {

  private final EntityReplacer entityReplacer;
  private final StringReplacer stringReplacer;
  private final ConfigurationHandle config;

  MessageHandlerImpl(ConfigurationHandle config) {
    this.entityReplacer = new EntityReplacer(config);
    this.stringReplacer = new StringReplacer();
    this.config = config;
  }

  @Override
  public <E> void sendReplacing(E entity, String path, Object... replacements) {
    String result = getReplacing(path, replacements);
    internalSend(entity, result);
  }

  @Override
  public <E> void send(E entity, String path, Object... entities) {
    String result = get(path, entities);
    internalSend(entity, result);
  }

  @Override
  public <E> void sendManyReplacing(E entity, String path, Object... replacements) {
    List<String> result = getManyReplacing(path, replacements);
    internalSendMany(entity, result);
  }

  @Override
  public <E> void sendMany(E entity, String path, Object... entities) {
    List<String> result = getMany(path, entities);
    internalSendMany(entity, result);
  }

  @Override
  public @NotNull String getReplacing(String path, Object... replacements) {
    return internalGet(path, stringReplacer, replacements);
  }

  @Override
  public @NotNull String get(String path, Object... entities) {
    return internalGet(path, entityReplacer, entities);
  }

  @Override
  public @NotNull List<String> getManyReplacing(String path, Object... replacements) {
    return internalGetMany(path, stringReplacer, replacements);
  }

  @Override
  public @NotNull List<String> getMany(String path, Object... entities) {
    return internalGetMany(path, entityReplacer, entities);
  }


  private <E> void internalSend(E entity, String result) {
    EntitySender<E> sender = getEntitySender(entity);
    sender.send(entity, result);
  }

  private <E> void internalSendMany(E entity, List<String> result) {
    EntitySender<E> sender = getEntitySender(entity);
    result.forEach(r -> sender.send(entity, r));
  }

  /**
   * Internal method to get a not null entity sender
   */
  @SuppressWarnings("unchecked")
  private @NotNull <E> EntitySender<E> getEntitySender(E entity) {
    Class<?> type = entity.getClass();

    EntityResolver<E> resolver = (EntityResolver<E>) config
        .getEntityResolver(type);

    if(resolver == null) {
      throw new IllegalArgumentException(
          "Not found entity resolver for entity " + type.getTypeName()
      );
    }

    EntitySender<E> sender = resolver.getSender();

    if(sender == null) {
      throw new IllegalArgumentException(
          "Not found entity sender for entity " + type.getTypeName()
      );
    }

    return sender;
  }

  private @NotNull String internalGet(
      String path,
      Replacer replacer,
      Object... objects
  ) {
    YamlConfiguration source = config.getSource();
    String result = source.getString(path);

    if(result == null ) {
      return path;
    }

    result = replacer.replace(result, objects);
    for (MessageInterceptor interceptor : config.getInterceptors()) {
      result = interceptor.intercept(result);
    }
    return result;
  }

  private @NotNull List<String> internalGetMany(
      String path,
      Replacer replacer,
      Object... objects
  ) {
    YamlConfiguration source = config.getSource();
    List<String> result = source.getStringList(path);

    if (result.isEmpty()) {
      return Collections.singletonList(path);
    }

    for (MessageInterceptor interceptor : config.getInterceptors()) {
      interceptor.intercept(result);
    }

    replacer.replace(result, objects);
    return result;
  }

  @Override
  public @NotNull ConfigurationHandle getConfig() {
    return config;
  }
}
