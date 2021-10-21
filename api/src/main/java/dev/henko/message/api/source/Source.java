package dev.henko.message.api.source;

import java.util.List;

public interface Source {

  void reload();

  String getString(String path);

  List<String> getStringList(String path);

}
