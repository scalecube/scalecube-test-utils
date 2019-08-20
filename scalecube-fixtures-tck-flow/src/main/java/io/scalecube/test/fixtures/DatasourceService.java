package io.scalecube.test.fixtures;

import java.util.Collection;

public interface DatasourceService {

  String get(String key);

  void delete(String key);

  void put(String key, String value);

  Collection<String> keys();
}
