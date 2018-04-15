package com.mladen.cikara.javacro.parser.wrapper;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;

@SuppressWarnings("unchecked")
public class NumberPathWrapper extends AbstractPathWrapper<NumberPath<?>> {

  public NumberPathWrapper(NumberPath<?> path) {
    this.path = path;
  }

  @Override
  public BooleanExpression doEq(Object value) {
    if (value instanceof Long) {
      return ((NumberPath<Long>) path).eq((Long) value);
    } else {
      return ((NumberPath<Double>) path).eq((Double) value);
    }
  }

  @Override
  public BooleanExpression doGt(Object value) {
    if (value instanceof Long) {
      return ((NumberPath<Long>) path).gt((Long) value);
    } else {
      return ((NumberPath<Double>) path).gt((Double) value);
    }
  }

  @Override
  public BooleanExpression doLt(Object value) {
    if (value instanceof Long) {
      return ((NumberPath<Long>) path).lt((Long) value);
    } else {
      return ((NumberPath<Double>) path).lt((Double) value);
    }
  }

  @Override
  public BooleanExpression doNe(Object value) {
    if (value instanceof Long) {
      return ((NumberPath<Long>) path).ne((Long) value);
    } else {
      return ((NumberPath<Double>) path).ne((Double) value);
    }
  }
}
