package dev.henko.message.api.replacer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface EntityReplacer<T>
  extends Replacer<T> {

  /**
   * {@inheritDoc}
   */
  @NotNull
  default List<T> replace(List<T> message, Object... entities) {
    message.replaceAll(s -> replace(s, entities));
    return message;
  }

}
