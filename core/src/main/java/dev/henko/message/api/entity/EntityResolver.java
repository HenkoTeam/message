package dev.henko.message.api.entity;

import dev.henko.message.api.entity.placeholder.EntityPlaceholder;
import dev.henko.message.api.entity.send.EntitySender;
import org.jetbrains.annotations.Nullable;

/**
 * Resolves a specific entity usages
 *
 * @param <E> The entity type
 */
public final class EntityResolver<E> {

  private final Class<E> type;

  private final EntitySender<E> sender;
  private final EntityPlaceholder<E> placeholder;

  public EntityResolver(
      Class<E> type,
      EntitySender<E> sender,
      EntityPlaceholder<E> placeholder
  ) {
    this.type = type;
    this.sender = sender;
    this.placeholder = placeholder;
  }

  public static <E> EntityResolverBuilder<E> builder(Class<E> type) {
    return new EntityResolverBuilder<>(type);
  }

  public Class<E> getType() {
    return type;
  }

  /**
   * Get the sender for this entity
   *
   * @return The entity sender
   */
  public @Nullable EntitySender<E> getSender() {
    return sender;
  }

  /**
   * Get the placeholder provider for this entity
   *
   * @return The placeholder provider
   */
  public @Nullable EntityPlaceholder<E> getPlaceholder() {
    return placeholder;
  }
}