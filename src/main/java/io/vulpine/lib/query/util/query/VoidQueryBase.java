package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.VoidQuery;

public abstract class VoidQueryBase< S extends Statement >
extends QueryBase < S >
implements VoidQuery < S >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public VoidQueryBase(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public VoidQueryBase(String sql, Connection con) {
    super(sql, con);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public VoidQueryBase(String sql, DataSource ds) {
    super(sql, ds);
  }

  @Override
  public VoidQueryBase < S > shareConnection(boolean flag) {
    return (VoidQueryBase <S>) super.shareConnection(flag);
  }

  @Override
  public void execute() throws Exception {
    if (isClosed())
      throw new IllegalStateException("Attempted to call #execute() on a closed VoidQuery instance");

    final var cn = getConnection();
    try (final var st = getStatement(cn)) {
      executeStatement(st);
    } finally {
      softCloseConnection();
    }
  }
}
