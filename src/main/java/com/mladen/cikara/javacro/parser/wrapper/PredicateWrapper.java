package com.mladen.cikara.javacro.parser.wrapper;

import com.querydsl.core.types.dsl.BooleanExpression;

public class PredicateWrapper implements OperationsInterface {

  private final BooleanExpression predicate;

  public PredicateWrapper(BooleanExpression predicate) {
    this.predicate = predicate;
  }

  @Override
  public BooleanExpression doAnd(BooleanExpression value) {
    return predicate.and(value);
  }

  @Override
  public BooleanExpression doEq(Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanExpression doGt(Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanExpression doLt(Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanExpression doNe(Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanExpression doOr(BooleanExpression value) {
    return predicate.or(value);
  }

}
