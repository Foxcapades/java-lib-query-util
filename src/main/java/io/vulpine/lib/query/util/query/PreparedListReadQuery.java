package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MultiReadQuery;
import io.vulpine.lib.query.util.ReadResult;

public abstract class PreparedListReadQuery <
  V,
  R extends ReadResult < List < V >, MultiReadQuery < ?, ?, ?, ? > > >
extends PreparedMultiReadQuery < V, List < V >, R >
implements MultiReadQuery < V, List < V >, R, PreparedStatement >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public PreparedListReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
    setCollectionProvider(ArrayList::new);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public PreparedListReadQuery(String sql, DataSource ds) {
    super(sql, ds);
    setCollectionProvider(ArrayList::new);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public PreparedListReadQuery(String sql, Connection cn) {
    super(sql, cn);
    setCollectionProvider(ArrayList::new);
  }
}
