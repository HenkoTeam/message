package dev.henko.message.api.source;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Source {

  /**
   * Gets a string from the source
   *
   * @param path Source path to find
   * @return String result
   */
  @Nullable
  String getString(String path);

  /**
   * Gets a string list from the source
   *
   * @param path Source path to find
   * @return String list result
   */
  @Nullable
  List<String> getStringList(String path);

}
