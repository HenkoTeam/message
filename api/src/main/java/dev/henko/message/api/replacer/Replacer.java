package dev.henko.message.api.replacer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Replacer<T> {

  @NotNull
  T replace(T message, Object... objects);

  @NotNull
  List<T> replace(List<T> message, Object... objects);

}
