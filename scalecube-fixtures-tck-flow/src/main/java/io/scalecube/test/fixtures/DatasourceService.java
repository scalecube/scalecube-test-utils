package io.scalecube.test.fixtures;

public interface DatasourceService {

  String get(String key);

  void delete(String key);

  void put(String key, String value);
}
