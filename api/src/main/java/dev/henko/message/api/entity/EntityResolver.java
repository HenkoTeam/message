package dev.henko.message.api.entity;

import dev.henko.message.api.entity.placeholder.EntityPlaceholderHolder;
import dev.henko.message.api.entity.placeholder.EntityPlaceholderProvider;
import org.jetbrains.annotations.Nullable;

public final class EntityResolver<E, T> {

  private EntitySender<E, T> sender;
  private EntityPlaceholderHolder<E> placeholderHolder;

  public EntityResolver<E, T> setSender(EntitySender<E, T> sender) {
    this.sender = sender;
    return this;
  }

  public EntityResolver<E, T> setPlaceholderProvider(
    String placeholderPrefix,
    EntityPlaceholderProvider<E> placeholderProvider
  ) {
    this.placeholderHolder = new EntityPlaceholderHolder<>(
      placeholderPrefix, placeholderProvider
    );
    return this;
  }

  public @Nullable EntitySender<E, T> getSender() {
    return sender;
  }

  public @Nullable EntityPlaceholderHolder<E> getPlaceholderHolder() {
    return placeholderHolder;
  }
}
