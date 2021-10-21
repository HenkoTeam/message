package dev.henko.message.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MessageReplacer<T> {

  @NotNull
  T replace(T message, Object... replacements);

  @NotNull
  default List<T> replace(List<T> message, Object... replacements) {
    if(replacements.length % 2 != 0) {
      return message;
    }
    message.replaceAll(s -> {
      replace(s, replacements);
      return s;
    });

    return message;
  }
}
