package io.vulpine.lib.query.util;

import java.sql.ResultSet;
import java.util.Optional;
import java.util.function.Supplier;

@FunctionalInterface
public interface RowParser<V>
{
  V parse(ResultSet rs) throws Exception;

  /**
   * Attempts to retrieve a single byte value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method returns
   * an empty option.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected byte,
   * this method returns an empty option.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return An option that will contain a byte value if the given
   * {@code ResultSet} has at least 1 row containing a non-null byte value.
   */
  static Optional<Byte> optionalByte(ResultSet rs) throws Exception {
    if (!rs.next())
      return Optional.empty();

    var out = rs.getByte(1);

    return rs.wasNull() ? Optional.empty() : Optional.of(out);
  }

  /**
   * Attempts to retrieve a single short value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method returns
   * an empty option.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected short,
   * this method returns an empty option.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return An option that will contain a short value if the given
   * {@code ResultSet} has at least 1 row containing a non-null short value.
   */
  static Optional<Short> optionalShort(ResultSet rs) throws Exception {
    if (!rs.next())
      return Optional.empty();

    var out = rs.getShort(1);

    return rs.wasNull() ? Optional.empty() : Optional.of(out);
  }

  /**
   * Attempts to retrieve a single int value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method returns
   * an empty option.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected int,
   * this method returns an empty option.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return An option that will contain a int value if the given
   * {@code ResultSet} has at least 1 row containing a non-null int value.
   */
  static Optional<Integer> optionalInt(ResultSet rs) throws Exception {
    if (!rs.next())
      return Optional.empty();

    var out = rs.getInt(1);

    return rs.wasNull() ? Optional.empty() : Optional.of(out);
  }

  /**
   * Attempts to retrieve a single long value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method returns
   * an empty option.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected long,
   * this method returns an empty option.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return An option that will contain a long value if the given
   * {@code ResultSet} has at least 1 row containing a non-null long value.
   */
  static Optional<Long> optionalLong(ResultSet rs) throws Exception {
    if (!rs.next())
      return Optional.empty();

    var out = rs.getLong(1);

    return rs.wasNull() ? Optional.empty() : Optional.of(out);
  }

  /**
   * Attempts to retrieve a single String value from the given
   * {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method returns
   * an empty option.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected
   * String, this method returns an empty option.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return An option that will contain a String value if the given
   * {@code ResultSet} has at least 1 row containing a non-null String value.
   */
  static Optional<String> optionalString(ResultSet rs) throws Exception {
    if (!rs.next())
      return Optional.empty();

    var out = rs.getString(1);

    return rs.wasNull() ? Optional.empty() : Optional.of(out);
  }

  /**
   * Attempts to retrieve a single float value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method returns
   * an empty option.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected float,
   * this method returns an empty option.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return An option that will contain a float value if the given
   * {@code ResultSet} has at least 1 row containing a non-null float value.
   */
  static Optional<Float> optionalFloat(ResultSet rs) throws Exception {
    if (!rs.next())
      return Optional.empty();

    var out = rs.getFloat(1);

    return rs.wasNull() ? Optional.empty() : Optional.of(out);
  }

  /**
   * Attempts to retrieve a single double value from the given
   * {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method returns
   * an empty option.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected
   * double, this method returns an empty option.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return An option that will contain a double value if the given
   * {@code ResultSet} has at least 1 row containing a non-null double value.
   */
  static Optional<Double> optionalDouble(ResultSet rs) throws Exception {
    if (!rs.next())
      return Optional.empty();

    var out = rs.getDouble(1);

    return rs.wasNull() ? Optional.empty() : Optional.of(out);
  }

  /**
   * Attempts to retrieve a single boolean value from the given
   * {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method returns
   * an empty option.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected
   * boolean, this method returns an empty option.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return An option that will contain a boolean value if the given
   * {@code ResultSet} has at least 1 row containing a non-null boolean value.
   */
  static Optional<Boolean> optionalBool(ResultSet rs) throws Exception {
    if (!rs.next())
      return Optional.empty();

    var out = rs.getBoolean(1);

    return rs.wasNull() ? Optional.empty() : Optional.of(out);
  }

  /**
   * Attempts to retrieve a single byte value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method throws
   * a {@code NoSuchElementException}.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected byte,
   * this method throws a {@code NoSuchElementException}.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return the byte value contained in the first column of the first row of
   * the given result set.
   */
  static byte requireByte(ResultSet rs) throws Exception {
    return optionalByte(rs).orElseThrow();
  }

  /**
   * Attempts to retrieve a single short value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method throws
   * a {@code NoSuchElementException}.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected short,
   * this method throws a {@code NoSuchElementException}.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return the short value contained in the first column of the first row of
   * the given result set.
   */
  static short requireShort(ResultSet rs) throws Exception {
    return optionalShort(rs).orElseThrow();
  }

  /**
   * Attempts to retrieve a single int value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method throws
   * a {@code NoSuchElementException}.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected int,
   * this method throws a {@code NoSuchElementException}.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return the int value contained in the first column of the first row of
   * the given result set.
   */
  static int requireInt(ResultSet rs) throws Exception {
    return optionalInt(rs).orElseThrow();
  }

  /**
   * Attempts to retrieve a single long value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method throws
   * a {@code NoSuchElementException}.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected long,
   * this method throws a {@code NoSuchElementException}.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return the long value contained in the first column of the first row of
   * the given result set.
   */
  static long requireLong(ResultSet rs) throws Exception {
    return optionalLong(rs).orElseThrow();
  }

  /**
   * Attempts to retrieve a single String value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method throws
   * a {@code NoSuchElementException}.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected String,
   * this method throws a {@code NoSuchElementException}.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return the String value contained in the first column of the first row of
   * the given result set.
   */
  static String requireString(ResultSet rs) throws Exception {
    return optionalString(rs).orElseThrow();
  }

  /**
   * Attempts to retrieve a single float value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method throws
   * a {@code NoSuchElementException}.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected float,
   * this method throws a {@code NoSuchElementException}.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return the float value contained in the first column of the first row of
   * the given result set.
   */
  static float requireFloat(ResultSet rs) throws Exception {
    return optionalFloat(rs).orElseThrow();
  }

  /**
   * Attempts to retrieve a single double value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method throws
   * a {@code NoSuchElementException}.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected double,
   * this method throws a {@code NoSuchElementException}.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return the double value contained in the first column of the first row of
   * the given result set.
   */
  static double requireDouble(ResultSet rs) throws Exception {
    return optionalDouble(rs).orElseThrow();
  }

  /**
   * Attempts to retrieve a single boolean value from the given {@code ResultSet}.
   * <p>
   * If the given {@code ResultSet} has no rows remaining, this method throws
   * a {@code NoSuchElementException}.
   * <p>
   * If the given {@code ResultSet} contains a null value for the expected boolean,
   * this method throws a {@code NoSuchElementException}.
   *
   * @param rs {@code ResultSet} to read.
   *
   * @return the boolean value contained in the first column of the first row of
   * the given result set.
   */
  static boolean requireBool(ResultSet rs) throws Exception {
    return optionalBool(rs).orElseThrow();
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireByte(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the user provided exception.
   *
   * @param ex Exception to throw if no byte value could be retrieved from the
   *           {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the byte value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Byte> requireByte(Exception ex) {
    return rs -> optionalByte(rs).orElseThrow(() -> ex);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireShort(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the user provided exception.
   *
   * @param ex Exception to throw if no short value could be retrieved from the
   *           {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the short value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Short> requireShort(Exception ex) {
    return rs -> optionalShort(rs).orElseThrow(() -> ex);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireInt(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the user provided exception.
   *
   * @param ex Exception to throw if no int value could be retrieved from the
   *           {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the int value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Integer> requireInt(Exception ex) {
    return rs -> optionalInt(rs).orElseThrow(() -> ex);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireLong(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the user provided exception.
   *
   * @param ex Exception to throw if no long value could be retrieved from the
   *           {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the long value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Long> requireLong(Exception ex) {
    return rs -> optionalLong(rs).orElseThrow(() -> ex);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireString(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the user provided exception.
   *
   * @param ex Exception to throw if no String value could be retrieved from the
   *           {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the String value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<String> requireString(Exception ex) {
    return rs -> optionalString(rs).orElseThrow(() -> ex);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireFloat(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the user provided exception.
   *
   * @param ex Exception to throw if no float value could be retrieved from the
   *           {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the float value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Float> requireFloat(Exception ex) {
    return rs -> optionalFloat(rs).orElseThrow(() -> ex);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireDouble(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the user provided exception.
   *
   * @param ex Exception to throw if no double value could be retrieved from the
   *           {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the double value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Double> requireDouble(Exception ex) {
    return rs -> optionalDouble(rs).orElseThrow(() -> ex);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireBool(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the user provided exception.
   *
   * @param ex Exception to throw if no boolean value could be retrieved from the
   *           {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the boolean value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Boolean> requireBool(Exception ex) {
    return rs -> optionalBool(rs).orElseThrow(() -> ex);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireByte(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the exception returned by the user provided {@code Supplier}.
   *
   * @param fn {@code Supplier} that returns the Exception to throw if no byte
   *           value could be retrieved from the {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the byte value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Byte> requireByte(Supplier<? extends Exception> fn) {
    return rs -> optionalByte(rs).orElseThrow(fn);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireShort(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the exception returned by the user provided {@code Supplier}.
   *
   * @param fn {@code Supplier} that returns the Exception to throw if no short
   *           value could be retrieved from the {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the short value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Short> requireShort(Supplier<? extends Exception> fn) {
    return rs -> optionalShort(rs).orElseThrow(fn);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireInt(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the exception returned by the user provided {@code Supplier}.
   *
   * @param fn {@code Supplier} that returns the Exception to throw if no int
   *           value could be retrieved from the {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the int value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Integer> requireInt(Supplier<? extends Exception> fn) {
    return rs -> optionalInt(rs).orElseThrow(fn);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireLong(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the exception returned by the user provided {@code Supplier}.
   *
   * @param fn {@code Supplier} that returns the Exception to throw if no long
   *           value could be retrieved from the {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the long value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Long> requireLong(Supplier<? extends Exception> fn) {
    return rs -> optionalLong(rs).orElseThrow(fn);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireString(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the exception returned by the user provided {@code Supplier}.
   *
   * @param fn {@code Supplier} that returns the Exception to throw if no String
   *           value could be retrieved from the {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the String value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<String> requireString(Supplier<? extends Exception> fn) {
    return rs -> optionalString(rs).orElseThrow(fn);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireFloat(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the exception returned by the user provided {@code Supplier}.
   *
   * @param fn {@code Supplier} that returns the Exception to throw if no float
   *           value could be retrieved from the {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the float value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Float> requireFloat(Supplier<? extends Exception> fn) {
    return rs -> optionalFloat(rs).orElseThrow(fn);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireDouble(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the exception returned by the user provided {@code Supplier}.
   *
   * @param fn {@code Supplier} that returns the Exception to throw if no double
   *           value could be retrieved from the {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the double value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Double> requireDouble(Supplier<? extends Exception> fn) {
    return rs -> optionalDouble(rs).orElseThrow(fn);
  }

  /**
   * Creates a new {@code RowParser} that behaves like
   * {@link #requireBool(ResultSet)} with the exception that instead of a
   * {@code NoSuchElement} exception, the returned {@code RowParser} will throw
   * the exception returned by the user provided {@code Supplier}.
   *
   * @param fn {@code Supplier} that returns the Exception to throw if no boolean
   *           value could be retrieved from the {@code ResultSet}.
   *
   * @return A {@code RowParser} that attempts to return the boolean value
   * contained in the first column of the first row of its given result set.
   */
  static RowParser<Boolean> requireBool(Supplier<? extends Exception> fn) {
    return rs -> optionalBool(rs).orElseThrow(fn);
  }

  /**
   * Creates a new {@code RowParser} that is a convenience shortcut over
   * <pre>
   *   RowParser.optionalByte(rs).orElse(value);
   * </pre>
   *
   * @param value alternate byte value to return if the {@code ResultSet} does
   *              not contain a non-null byte value in the first column of the
   *              first row.
   *
   * @return A {@code RowParser} that attempts to return the byte value
   * contained in the first column of the first row of its given result set, or
   * alternatively returns the given {@code value} param.
   */
  static RowParser<Byte> requireByte(byte value) {
    return rs -> optionalByte(rs).orElse(value);
  }

  /**
   * Creates a new {@code RowParser} that is a convenience shortcut over
   * <pre>
   *   RowParser.optionalShort(rs).orElse(value);
   * </pre>
   *
   * @param value alternate short value to return if the {@code ResultSet} does
   *              not contain a non-null short value in the first column of the
   *              first row.
   *
   * @return A {@code RowParser} that attempts to return the short value
   * contained in the first column of the first row of its given result set, or
   * alternatively returns the given {@code value} param.
   */
  static RowParser<Short> requireShort(short value) {
    return rs -> optionalShort(rs).orElse(value);
  }

  /**
   * Creates a new {@code RowParser} that is a convenience shortcut over
   * <pre>
   *   RowParser.optionalInt(rs).orElse(value);
   * </pre>
   *
   * @param value alternate int value to return if the {@code ResultSet} does
   *              not contain a non-null int value in the first column of the
   *              first row.
   *
   * @return A {@code RowParser} that attempts to return the int value
   * contained in the first column of the first row of its given result set, or
   * alternatively returns the given {@code value} param.
   */
  static RowParser<Integer> requireInt(int value) {
    return rs -> optionalInt(rs).orElse(value);
  }

  /**
   * Creates a new {@code RowParser} that is a convenience shortcut over
   * <pre>
   *   RowParser.optionalLong(rs).orElse(value);
   * </pre>
   *
   * @param value alternate long value to return if the {@code ResultSet} does
   *              not contain a non-null long value in the first column of the
   *              first row.
   *
   * @return A {@code RowParser} that attempts to return the long value
   * contained in the first column of the first row of its given result set, or
   * alternatively returns the given {@code value} param.
   */
  static RowParser<Long> requireLong(long value) {
    return rs -> optionalLong(rs).orElse(value);
  }

  /**
   * Creates a new {@code RowParser} that is a convenience shortcut over
   * <pre>
   *   RowParser.optionalString(rs).orElse(value);
   * </pre>
   *
   * @param value alternate String value to return if the {@code ResultSet} does
   *              not contain a non-null String value in the first column of the
   *              first row.
   *
   * @return A {@code RowParser} that attempts to return the String value
   * contained in the first column of the first row of its given result set, or
   * alternatively returns the given {@code value} param.
   */
  static RowParser<String> requireString(String value) {
    return rs -> optionalString(rs).orElse(value);
  }

  /**
   * Creates a new {@code RowParser} that is a convenience shortcut over
   * <pre>
   *   RowParser.optionalFloat(rs).orElse(value);
   * </pre>
   *
   * @param value alternate float value to return if the {@code ResultSet} does
   *              not contain a non-null float value in the first column of the
   *              first row.
   *
   * @return A {@code RowParser} that attempts to return the float value
   * contained in the first column of the first row of its given result set, or
   * alternatively returns the given {@code value} param.
   */
  static RowParser<Float> requireFloat(float value) {
    return rs -> optionalFloat(rs).orElse(value);
  }

  /**
   * Creates a new {@code RowParser} that is a convenience shortcut over
   * <pre>
   *   RowParser.optionalDouble(rs).orElse(value);
   * </pre>
   *
   * @param value alternate double value to return if the {@code ResultSet} does
   *              not contain a non-null double value in the first column of the
   *              first row.
   *
   * @return A {@code RowParser} that attempts to return the double value
   * contained in the first column of the first row of its given result set, or
   * alternatively returns the given {@code value} param.
   */
  static RowParser<Double> requireDouble(double value) {
    return rs -> optionalDouble(rs).orElse(value);
  }

  /**
   * Creates a new {@code RowParser} that is a convenience shortcut over
   * <pre>
   *   RowParser.optionalBool(rs).orElse(value);
   * </pre>
   *
   * @param value alternate boolean value to return if the {@code ResultSet} does
   *              not contain a non-null boolean value in the first column of the
   *              first row.
   *
   * @return A {@code RowParser} that attempts to return the boolean value
   * contained in the first column of the first row of its given result set, or
   * alternatively returns the given {@code value} param.
   */
  static RowParser<Boolean> requireBool(boolean value) {
    return rs -> optionalBool(rs).orElse(value);
  }
}
