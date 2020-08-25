package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.ReadResult;
import io.vulpine.lib.query.util.SetReadQuery;

public abstract class SetReadQueryImpl <
  V,
  R extends ReadResult < Set < V >, ? extends SetReadQuery < ?, ?, ? > >,
  S extends Statement >
extends MultiReadQueryImpl < V, Set < V >, R, S >
implements SetReadQuery < V, R, S >
{
  private boolean doConflictChecks;

  private BinaryOperator < V > conflictHandler;

  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public SetReadQueryImpl(String sql, ConnectionProvider provider) {
    super(sql, provider);
    setCollectionProvider(HashSet::new);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public SetReadQueryImpl(String sql, DataSource ds) {
    super(sql, ds);
    setCollectionProvider(HashSet::new);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public SetReadQueryImpl(String sql, Connection cn) {
    super(sql, cn);
    setCollectionProvider(HashSet::new);
  }

  @Override
  public SetReadQuery < V, R, S > setCollectionProvider(
    final Supplier < Set < V > > con
  ) {
    return (SetReadQuery < V, R, S >) super.setCollectionProvider(con);
  }

  @Override
  public SetReadQuery < V, R, S > enableConflictHandling(boolean flag) {
    this.doConflictChecks = flag;
    return this;
  }

  @Override
  public SetReadQuery < V, R, S > setConflictResolver(BinaryOperator < V > fn) {
    this.conflictHandler = fn;
    return this;
  }

  @Override
  protected Set < V > parseResult(ResultSet rs) throws Exception {
    final var out = getCollectionProvider().get();

    if (doConflictChecks) {
      final var store = new HashMap < V, V >();

      while (rs.next()) {
        final var val = parseRow(rs);

        if (store.containsKey(val)) {
          var resolved = conflictHandler.apply(store.get(val), val);
          out.add(resolved);
          store.put(val, resolved);
          store.put(resolved, resolved);
        } else {
          out.add(val);
          store.put(val, val);
        }
      }
    } else {
      while (rs.next())
        out.add(parseRow(rs));
    }

    return out;
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
