package io.vulpine.lib.query.util;

import java.sql.PreparedStatement;

@FunctionalInterface
public interface StatementPreparer
{
  void prepare(PreparedStatement ps) throws Exception;

  /**
   * Creates a StatementPreparer instance that sets a single byte value to
   * query parameter {@code 1}.
   * <p>
   * This is a convenience method for preparing queries that only take one
   * parameter.
   *
   * @param value byte value to set in the prepared statement.
   *
   * @return a StatementPreparer wrapping the given value
   */
  static StatementPreparer singleByte(byte value) {
    return ps -> ps.setByte(1, value);
  }

  /**
   * Creates a StatementPreparer instance that sets a single short value to
   * query parameter {@code 1}.
   * <p>
   * This is a convenience method for preparing queries that only take one
   * parameter.
   *
   * @param value short value to set in the prepared statement.
   *
   * @return a StatementPreparer wrapping the given value
   */
  static StatementPreparer singleShort(short value) {
    return ps -> ps.setShort(1, value);
  }

  /**
   * Creates a StatementPreparer instance that sets a single int value to
   * query parameter {@code 1}.
   * <p>
   * This is a convenience method for preparing queries that only take one
   * parameter.
   *
   * @param value int value to set in the prepared statement.
   *
   * @return a StatementPreparer wrapping the given value
   */
  static StatementPreparer singleInt(int value) {
    return ps -> ps.setInt(1, value);
  }

  /**
   * Creates a StatementPreparer instance that sets a single long value to
   * query parameter {@code 1}.
   * <p>
   * This is a convenience method for preparing queries that only take one
   * parameter.
   *
   * @param value long value to set in the prepared statement.
   *
   * @return a StatementPreparer wrapping the given value
   */
  static StatementPreparer singleLong(long value) {
    return ps -> ps.setLong(1, value);
  }

  /**
   * Creates a StatementPreparer instance that sets a single String value to
   * query parameter {@code 1}.
   * <p>
   * This is a convenience method for preparing queries that only take one
   * parameter.
   *
   * @param value String value to set in the prepared statement.
   *
   * @return a StatementPreparer wrapping the given value
   */
  static StatementPreparer singleString(String value) {
    return ps -> ps.setString(1, value);
  }

  /**
   * Creates a StatementPreparer instance that sets a single float value to
   * query parameter {@code 1}.
   * <p>
   * This is a convenience method for preparing queries that only take one
   * parameter.
   *
   * @param value float value to set in the prepared statement.
   *
   * @return a StatementPreparer wrapping the given value
   */
  static StatementPreparer singleFloat(float value) {
    return ps -> ps.setFloat(1, value);
  }

  /**
   * Creates a StatementPreparer instance that sets a single double value to
   * query parameter {@code 1}.
   * <p>
   * This is a convenience method for preparing queries that only take one
   * parameter.
   *
   * @param value double value to set in the prepared statement.
   *
   * @return a StatementPreparer wrapping the given value
   */
  static StatementPreparer singleDouble(double value) {
    return ps -> ps.setDouble(1, value);
  }

  /**
   * Creates a StatementPreparer instance that sets a single boolean value to
   * query parameter {@code 1}.
   * <p>
   * This is a convenience method for preparing queries that only take one
   * parameter.
   *
   * @param value boolean value to set in the prepared statement.
   *
   * @return a StatementPreparer wrapping the given value
   */
  static StatementPreparer singleBool(boolean value) {
    return ps -> ps.setBoolean(1, value);
  }
}
