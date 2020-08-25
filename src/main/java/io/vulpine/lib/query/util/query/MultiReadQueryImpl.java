package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MultiReadQuery;
import io.vulpine.lib.query.util.ReadResult;

abstract class MultiReadQueryImpl <
  V,
  C extends Collection < V >,
  R extends ReadResult < C, ? extends MultiReadQuery < ?, ?, ?, ? > >,
  S extends Statement >
extends ReadQueryImpl < C, R, S >
implements MultiReadQuery < V, C, R, S >
{
  private Supplier < ? extends C > collectionProvider;

  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public MultiReadQueryImpl(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public MultiReadQueryImpl(String sql, DataSource ds) {
    super(sql, ds);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public MultiReadQueryImpl(String sql, Connection cn) {
    super(sql, cn);
  }

  @Override
  public MultiReadQuery < V, C, R, S > setCollectionProvider(Supplier < C > con) {
    collectionProvider = Objects.requireNonNull(con);
    return this;
  }

  @Override
  protected C parseResult(ResultSet rs) throws Exception {
    final var out = getCollectionProvider().get();

    while (rs.next())
      out.add(parseRow(rs));

    return out;
  }

  protected Supplier < ? extends C > getCollectionProvider() {
    return Objects.requireNonNull(collectionProvider);
  }

  /**
   * Parses a single row of of the given <code>ResultSet</code> into an instance
   * of {@link V}.
   *
   * @param rs <code>ResultSet</code> from which to pull data.
   *
   * @return a new instance of {@link V}.
   */
  protected abstract V parseRow(ResultSet rs) throws Exception;
}
