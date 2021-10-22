package dev.henko.message.api;

import dev.henko.message.api.entity.EntitySender;
import dev.henko.message.api.error.EntitySenderNotFoundException;
import dev.henko.message.api.replacer.EntityReplacer;
import dev.henko.message.api.replacer.MessageReplacer;
import dev.henko.message.api.replacer.Replacer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractMessenger<T>
  implements Messenger<T> {

  private final EntityReplacer<T> entityReplacer;
  private final MessageReplacer<T> messageReplacer;
  protected final MessengerConfig<T> config;

  protected AbstractMessenger(
    EntityReplacer<T> entityReplacer,
    MessageReplacer<T> messageReplacer,
    MessengerConfig<T> config
  ) {
    this.entityReplacer = entityReplacer;
    this.messageReplacer = messageReplacer;
    this.config = config;
  }

  @Override
  public @NotNull T getReplacing(String path, Object... replacements) {
    return internalGet(path, messageReplacer, replacements);
  }

  @Override
  public @NotNull T get(String path, Object... entities) {
    return internalGet(path, entityReplacer, entities);
  }

  protected abstract @NotNull T internalGet(
    String path, Replacer<T> replacer, Object... objects
  );

  @Override
  public @NotNull List<T> getManyReplacing(String path, Object... replacements) {
    return internalGetMany(path, messageReplacer, replacements);
  }

  @Override
  public @NotNull List<T> getMany(String path, Object... entities) {
    return internalGetMany(path, entityReplacer, entities);
  }

  protected abstract @NotNull List<T> internalGetMany(
    String path, Replacer<T> replacer, Object... objects
  );

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <E> void sendReplacing(E entity, String path, Object... replacements) {
    T result = getReplacing(path, replacements);
    internalSend(entity, result);
  }

  @Override
  public <E> void send(E entity, String path, Object... entities) {
    T result = get(path, entities);
    internalSend(entity, result);
  }

  private <E> void internalSend(E entity, T result) {
    EntitySender<E, T> sender = internalGetEntitySender(entity);
    sender.send(entity, result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> void sendManyReplacing(E entity, String path, Object... replacements) {
    List<T> result = getManyReplacing(path, replacements);
    internalSendMany(entity, result);
  }

  @Override
  public <E> void sendMany(E entity, String path, Object... entities) {
    List<T> result = getManyReplacing(path, entities);
    internalSendMany(entity, result);
  }

  private <E> void internalSendMany(E entity, List<T> result) {
    EntitySender<E, T> sender = internalGetEntitySender(entity);
    result.forEach(r -> {
      sender.send(entity, r);
    });
  }

  @SuppressWarnings("unchecked")
  private @NotNull <E> EntitySender<E,T> internalGetEntitySender(E entity) {
    Class<?> type = entity.getClass();
    EntitySender<E, T> sender = (EntitySender<E, T>) config
      .getNotNullResolver(type)
      .getSender();
    if(sender == null) {
      throw new EntitySenderNotFoundException(type);
    }
    return sender;
  }

  @Override
  public @NotNull MessengerConfig<T> getConfig() {
    return config;
  }


}
