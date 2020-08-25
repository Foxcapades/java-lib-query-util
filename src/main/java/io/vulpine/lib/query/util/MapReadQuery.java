package io.vulpine.lib.query.util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public interface MapReadQuery <
  K, V,
  R extends ReadResult < Map < K, V >, ? extends MapReadQuery < ?, ?, ?, ? > >,
  S extends Statement >
extends ReadQuery < Map < K, V >, R, S >
{
  @FunctionalInterface
  interface ConflictHandler < K, V > {
    V handle(K key, V previous, V current);
  }

  MapReadQuery < K, V, R, S > setMapProvider(Supplier < Map < K, V > > fn);

  MapReadQuery < K, V, R, S > setConflictHandler(ConflictHandler < K, V > fn);

  MapReadQuery < K, V, R, S > setKeyParser(RowParser < K > fn);

  MapReadQuery < K, V, R, S > setValueParser(RowParser < V > fn);
}
