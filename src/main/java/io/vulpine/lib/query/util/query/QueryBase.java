package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.Query;
import io.vulpine.lib.query.util.VoidQuery;

/**
 * Internal indirect implementation of common methods between the {@link Query}
 * and {@link VoidQuery} inheritance trees.
 *
 * @param <S> Specific type of JDBC <code>Statement</code> that will be used
 *            by this query.
 */
public abstract class QueryBase< S extends Statement > implements AutoCloseable
{
  private final String sql;

  private final ConnectionProvider provider;

  private final boolean canCloseConnection;

  private boolean shareConnection;

  private boolean closed;

  private Connection con;

  /**
   * Creates a new instance with the given SQL string and a function to use to
   * retrieve new connections.
   * <p>
   * When using this constructor the default behavior is for each query
   * execution to utilize a new {@link Connection} per execution, closing the
   * <code>Connection</code> immediately after use.  To alter this behavior
   * See {@link #shareConnection(boolean)}.
   *
   * @param sql      SQL query that will be used by this instance.
   * @param provider Function that will be used when a new {@link Connection}
   *                 instance is needed.
   */
  public QueryBase(String sql, ConnectionProvider provider) {
    this.sql                = Objects.requireNonNull(sql);
    this.provider           = Objects.requireNonNull(provider);
    this.canCloseConnection = true;
  }

  /**
   * Creates a new instance with the given SQL string and a single connection to
   * use.
   * <p>
   * When using a single connection, the {@link #shareConnection(boolean)}
   * becomes a no-op as connection sharing cannot be disabled when using a
   * single connection.
   *
   * @param sql SQL query to run on {@link VoidQuery#execute()}
   * @param con Single connection to use for all calls to
   *            {@link VoidQuery#execute()}
   *
   * @see VoidQuery#shareConnection(boolean)
   */
  public QueryBase(String sql, Connection con) {
    this.sql                = Objects.requireNonNull(sql);
    Objects.requireNonNull(con);
    this.provider           = () -> con;
    this.canCloseConnection = false;
    this.shareConnection    = true;
  }

  /**
   * Creates a new instance with the given SQL string and a {@link DataSource}
   * to use to retrieve new connections.
   * <p>
   * When using this constructor the default behavior is for each query
   * execution to utilize a new {@link Connection} per execution, closing the
   * <code>Connection</code> immediately after use.  To alter this behavior
   * See {@link #shareConnection(boolean)}.
   * <p>
   * This method is simply shorthand for calling:
   * <pre>
   *   new QueryBase(sql, dataSource::getConnection);
   * </pre>
   *
   * @param sql SQL query that will be used by this instance.
   * @param ds  DataSource instance from which to fetch new {@link Connection}
   *            instances when needed.
   */
  public QueryBase(String sql, DataSource ds) {
    this(sql, ds::getConnection);
  }

  /**
   * Indirect implementation of {@link VoidQuery#getSql()} and
   * {@link Query#getSql()}.
   *
   * @return the SQL query value set on this instance.
   */
  public String getSql() {
    return sql;
  }

  /**
   * Indirect implementation of {@link VoidQuery#shareConnection(boolean)} and
   * {@link Query#shareConnection(boolean)}.
   * <p>
   * Direct implementers of these interfaces will need to override this method
   * to provide a compatible return type.
   */
  public QueryBase < S > shareConnection(boolean flag) {
    // If we are in single connection mode, this value cannot be changed.
    if (canCloseConnection)
      this.shareConnection = flag;

    return this;
  }

  /**
   * {@inheritDoc}
   */
  public void close() throws Exception {
    this.closed = true;
    if (con != null)
      con.close();
  }

  /**
   * Indirect implementation of {@link VoidQuery#isClosed()} and
   * {@link Query#isClosed()}.
   */
  public boolean isClosed() {
    return closed;
  }

  /**
   * Executes the given statement.
   *
   * @param stmt statement to execute.
   */
  protected abstract void executeStatement(S stmt) throws Exception;

  /**
   * Creates a new {@link S} instance from the given connection.
   *
   * @param cn Connection instance provided from {@link #getConnection()}
   *
   * @return A new {@link S} instance.
   */
  protected abstract S getStatement(Connection cn) throws Exception;

  /**
   * Returns a new connection if connection sharing is off, otherwise returns
   * the shared <code>Connection</code> instance.
   *
   * @return a <code>Connection</code> instance.
   */
  protected Connection getConnection() throws Exception {
    if (con == null)
      return con = Objects.requireNonNull(provider.get());

    if (!shareConnection)
      throw new IllegalStateException("Attempted to create a new connection without first closing the previous one.");

    return con;
  }

  /**
   * Closes a connection only if {@link #shareConnection} is set to false.
   */
  protected void softCloseConnection() throws Exception {
    if (shareConnection)
      return;

    con.close();
    con = null;
  }
}
