package dev.henko.message.api;

import dev.henko.message.api.entity.EntitySender;
import dev.henko.message.api.error.EntitySenderNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractMessenger<T>
  implements Messenger<T> {

  protected final MessageReplacer<T> replacer;
  protected final MessengerConfig<T> config;

  protected AbstractMessenger(
    MessageReplacer<T> replacer,
    MessengerConfig<T> config)
  {
    this.replacer = replacer;
    this.config = config;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <E> void send(E entity, String path, Object... replacements) {
    Class<?> type = entity.getClass();
    EntitySender<E, T> sender = (EntitySender<E, T>) config.getEntitySender(type);
    if(sender == null) {
      throw new EntitySenderNotFoundException(type);
    }
    T result = get(path, replacements);
    sender.send(entity, result);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <E> void sendMany(E entity, String path, Object... replacements) {
    Class<?> type = entity.getClass();
    EntitySender<E, T> sender = (EntitySender<E, T>) config.getEntitySender(type);
    if(sender == null) {
      throw new EntitySenderNotFoundException(type);
    }
    List<T> result = getMany(path, replacements);
    result.forEach(r -> {
      sender.send(entity, r);
    });
  }

  @Override
  public @NotNull MessengerConfig<T> getConfig() {
    return config;
  }
}
