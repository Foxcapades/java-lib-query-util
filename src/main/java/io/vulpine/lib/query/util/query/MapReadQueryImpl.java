package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MapReadQuery;
import io.vulpine.lib.query.util.ReadResult;
import io.vulpine.lib.query.util.RowParser;

public abstract class MapReadQueryImpl<
  K, V,
  R extends ReadResult < Map < K, V >, ? extends MapReadQuery < ?, ?, ?, ? > >,
  S extends Statement >
extends ReadQueryImpl < Map < K, V >, R, S >
implements MapReadQuery < K, V, R, S >
{
  private ConflictHandler < K, V > conflictHandler;

  private Supplier < Map < K, V > > mapSupplier;

  private RowParser < K > keyParser;

  private RowParser < V > valParser;

  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public MapReadQueryImpl(String sql, ConnectionProvider provider) {
    super(sql, provider);
    conflictHandler = MapReadQueryImpl::defaultConflictHandler;
    mapSupplier = HashMap::new;
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public MapReadQueryImpl(String sql, DataSource ds) {
    super(sql, ds);
    conflictHandler = MapReadQueryImpl::defaultConflictHandler;
    mapSupplier = HashMap::new;
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public MapReadQueryImpl(String sql, Connection cn) {
    super(sql, cn);
    conflictHandler = MapReadQueryImpl::defaultConflictHandler;
    mapSupplier = HashMap::new;
  }

  @Override
  public MapReadQuery < K, V, R, S > setMapProvider(Supplier < Map < K, V > > fn) {
    this.mapSupplier = Objects.requireNonNull(fn);
    return this;
  }

  @Override
  public MapReadQuery < K, V, R, S > setConflictHandler(ConflictHandler < K, V > fn) {
    this.conflictHandler = Objects.requireNonNull(fn);
    return this;
  }

  @Override
  public MapReadQuery < K, V, R, S > setKeyParser(RowParser < K > fn) {
    this.keyParser = Objects.requireNonNull(fn);
    return this;
  }

  @Override
  public MapReadQuery < K, V, R, S > setValueParser(RowParser < V > fn) {
    this.valParser = Objects.requireNonNull(fn);
    return this;
  }

  public static < K, V > V defaultConflictHandler(K key, V v1, V v2) {
    return v2;
  }

  @Override
  protected Map < K, V > parseResult(ResultSet rs) throws Exception {
    final var out = mapSupplier.get();

    while (rs.next()) {
      final var key = keyParser.parse(rs);
      final var val = valParser.parse(rs);

      if (out.containsKey(key))
        out.put(key, conflictHandler.handle(key, out.get(key), val));
      else
        out.put(key, val);
    }

    return out;
  }
}
