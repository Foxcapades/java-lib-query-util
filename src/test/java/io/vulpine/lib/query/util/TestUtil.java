package io.vulpine.lib.query.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.mockito.ArgumentMatchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtil
{
  public static ResultSet mockResultSet() {
    return mock(ResultSet.class);
  }

  public static Statement mockStatement() {
    return mock(Statement.class);
  }

  public static Statement mockStatement(int updated) throws Exception {
    var out = mockStatement();
    when(out.getUpdateCount()).thenReturn(updated);
    return out;
  }

  public static Statement mockStatement(ResultSet rs) throws Exception {
    var out = mockStatement();
    when(out.getResultSet()).thenReturn(rs);
    return out;
  }

  public static PreparedStatement mockPreparedStatement() {
    return mock(PreparedStatement.class);
  }

  public static PreparedStatement mockPreparedStatement(int updated) throws Exception {
    var out = mockPreparedStatement();
    when(out.getUpdateCount()).thenReturn(updated);
    return out;
  }

  public static PreparedStatement mockPreparedStatement(ResultSet rs) throws Exception {
    var out = mockPreparedStatement();
    when(out.getResultSet()).thenReturn(rs);
    return out;
  }

  public static Connection mockConnection() {
    return mock(Connection.class);
  }

  public static Connection mockConnection(Statement stmt) throws Exception {
    var out = mockConnection();
    when(out.createStatement()).thenReturn(stmt);
    return out;
  }

  public static Connection mockConnection(PreparedStatement ps) throws Exception {
    var out = mockConnection();
    when(out.prepareStatement(ArgumentMatchers.anyString())).thenReturn(ps);
    return out;
  }

  public static DataSource mockDataSource() {
    return mock(DataSource.class);
  }

  public static DataSource mockDataSource(Connection cn) throws Exception {
    var out = mock(DataSource.class);
    when(out.getConnection()).thenReturn(cn);
    return out;
  }
}
