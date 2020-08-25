package io.vulpine.lib.query.util.result;

import java.sql.Statement;

import io.vulpine.lib.query.util.Query;
import io.vulpine.lib.query.util.QueryResult;

class QueryResultImpl<
  Q extends Query < ? extends QueryResult < ? >, ? >,
  S extends Statement >
implements QueryResult < Q >
{
  private final Q query;

  private final S stmt;

  private boolean closeParentOnClose;

  public QueryResultImpl(Q query, S stmt) {
    this.query = query;
    this.stmt  = stmt;
  }

  @Override
  public Q getQuery() {
    return query;
  }

  @Override
  public void close() throws Exception {
    stmt.close();

    if (closeParentOnClose)
      query.close();
  }

  @Override
  public QueryResult < Q > closeParentOnClose(boolean flag) {
    this.closeParentOnClose = flag;
    return this;
  }

  /**
   * Internal access to the specific {@link Statement} object that was directly
   * involved in the creation of this <code>QueryResult</code>.
   *
   * @return this result's backing <code>Statement</code>.
   */
  protected S getStatement() {
    return stmt;
  }
}
