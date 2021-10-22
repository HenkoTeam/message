package dev.henko.message.api.entity.placeholder;

public interface EntityPlaceholderProvider<E> {
  Object replace(E entity, String placeholder);
}
