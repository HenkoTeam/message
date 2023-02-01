package dev.henko.message.api.entity;

import dev.henko.message.api.entity.placeholder.EntityPlaceholder;
import dev.henko.message.api.entity.placeholder.EntityPlaceholderProvider;
import dev.henko.message.api.entity.send.EntitySender;

public class EntityResolverBuilder<E> {

  private final Class<E> type;

  private EntitySender<E> sender;
  private EntityPlaceholder<E> placeholder;

  EntityResolverBuilder(Class<E> type) {
    this.type = type;
  }

  /**
   * Set a sender for the entity
   *
   * @param sender EntitySender to set
   * @return This current resolver
   */
  public EntityResolverBuilder<E> sender(EntitySender<E> sender) {
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
  public EntityResolverBuilder<E> placeholder(
      String placeholderPrefix,
      EntityPlaceholderProvider<E> placeholderProvider
  ) {
    this.placeholder = new EntityPlaceholder<>(
        placeholderPrefix, placeholderProvider
    );
    return this;
  }

  public EntityResolver<E> build() {
    return new EntityResolver<>(type, sender, placeholder);
  }
}