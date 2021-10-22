package dev.henko.message.api.entity;

import dev.henko.message.api.entity.placeholder.EntityPlaceholderHolder;
import dev.henko.message.api.entity.placeholder.EntityPlaceholderProvider;
import dev.henko.message.api.error.EntitySenderNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Resolves a specific entity usages
 *
 * @param <E> The entity type
 * @param <T> The message type
 */
public final class EntityResolver<E, T> {

  private EntitySender<E, T> sender;
  private EntityPlaceholderHolder<E> placeholderHolder;

  /**
   * Set a sender for the entity
   *
   * @param sender EntitySender to set
   * @return This current resolver
   */
  public EntityResolver<E, T> setSender(EntitySender<E, T> sender) {
    this.sender = sender;
    return this;
  }

  /**
   * Set a placeholder provider for the entity
   *
   * @param placeholderPrefix  Placeholders prefix
   * @param placeholderProvider Placeholder provider to set
   * @return THis current resolver
   */
  public EntityResolver<E, T> setPlaceholderProvider(
    String placeholderPrefix,
    EntityPlaceholderProvider<E> placeholderProvider
  ) {
    this.placeholderHolder = new EntityPlaceholderHolder<>(
      placeholderPrefix, placeholderProvider
    );
    return this;
  }

  /**
   * Get the sender for this entity
   *
   * @return The entity sender
   */
  public @Nullable EntitySender<E, T> getSender() {
    return sender;
  }

  /**
   * Get the placeholder provider for this entity
   *
   * @return The placeholder provider
   */
  public @Nullable EntityPlaceholderHolder<E> getPlaceholderHolder() {
    return placeholderHolder;
  }
}
