package dev.henko.message.api.entity.placeholder;

public final class StringPlaceholderObtainer {

  public static String from(
    String text,
    String prefix
  ) {
    return text
      .replace("%", "")
      .replace(prefix + "_", "");
  }

}
