package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MultiReadQuery;
import io.vulpine.lib.query.util.ReadResult;

public abstract class ListReadQueryImpl<
  V,
  R extends ReadResult < List < V >, ? extends MultiReadQuery < ?, ?, ?, ? > >,
  S extends Statement >
extends MultiReadQueryImpl < V, List < V >, R, S >
implements MultiReadQuery < V, List < V >, R, S >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public ListReadQueryImpl(String sql, ConnectionProvider provider) {
    super(sql, provider);
    setCollectionProvider(ArrayList::new);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public ListReadQueryImpl(String sql, DataSource ds) {
    super(sql, ds);
    setCollectionProvider(ArrayList::new);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public ListReadQueryImpl(String sql, Connection cn) {
    super(sql, cn);
    setCollectionProvider(ArrayList::new);
  }
}
