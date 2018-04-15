package com.mladen.cikara.javacro.parser;

import static org.assertj.core.api.Assertions.assertThat;

import com.mladen.cikara.javacro.entity.QRunTime;
import com.mladen.cikara.javacro.entity.QUser;
import com.querydsl.core.types.Predicate;

import java.time.LocalDateTime;

import org.junit.Test;

public class PredicateBuilderTest {

  @Test
  public void parsingTest01() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QUser> predicateBuilder = new PredicateBuilder<QUser>(QUser.user);

    final Predicate result = predicateBuilder.getFilter("username eq 'admin'");

    assertThat(result).isEqualTo(QUser.user.username.eq("admin"));
  }

  @Test
  public void parsingTest02() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QUser> predicateBuilder = new PredicateBuilder<QUser>(QUser.user);

    final Predicate result =
        predicateBuilder.getFilter("username eq 'admin' and firstName ne 'John'");

    assertThat(result)
        .isEqualTo(QUser.user.username.eq("admin").and(QUser.user.firstName.ne("John")));
  }

  @Test
  public void parsingTest03() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QUser> predicateBuilder = new PredicateBuilder<QUser>(QUser.user);

    final Predicate result =
        predicateBuilder
            .getFilter("username eq 'admin' and ( firstName ne 'John' or firstName ne 'Jack' )");

    assertThat(result)
        .isEqualTo(QUser.user.username.eq("admin")
            .and(QUser.user.firstName.ne("John").or(QUser.user.firstName.ne("Jack"))));
  }

  @Test
  public void parsingTest04() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QUser> predicateBuilder = new PredicateBuilder<QUser>(QUser.user);

    final Predicate result =
        predicateBuilder
            .getFilter("username eq 'admin' AND ( firstName Ne 'John' or firstName ne 'Jack' )");

    assertThat(result)
        .isEqualTo(QUser.user.username.eq("admin")
            .and(QUser.user.firstName.ne("John").or(QUser.user.firstName.ne("Jack"))));
  }

  @Test
  public void parsingTest05() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QUser> predicateBuilder = new PredicateBuilder<QUser>(QUser.user);

    final Predicate result =
        predicateBuilder
            .getFilter("username eq 'admin' and firstName ne 'John' and firstName ne 'Jack'");

    final Predicate expected = QUser.user.username.eq("admin").and(QUser.user.firstName.ne("John"))
        .and(QUser.user.firstName.ne("Jack"));

    assertThat(result.toString())
        .isEqualTo(expected.toString());
  }

  @Test
  public void parsingTest06() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QUser> predicateBuilder = new PredicateBuilder<QUser>(QUser.user);

    final Predicate result = predicateBuilder.getFilter("id gt 1");

    assertThat(result).isEqualTo(QUser.user.id.gt(1l));
  }

  @Test
  public void parsingTest07() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QRunTime> predicateBuilder =
        new PredicateBuilder<QRunTime>(QRunTime.runTime1);

    final Predicate result = predicateBuilder.getFilter("distance gt 1.1");

    assertThat(result).isEqualTo(QRunTime.runTime1.distance.gt(1.1));
  }

  @Test
  public void parsingTest08() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QRunTime> predicateBuilder =
        new PredicateBuilder<QRunTime>(QRunTime.runTime1);

    final Predicate result = predicateBuilder.getFilter("distance gt 1.");

    assertThat(result).isEqualTo(QRunTime.runTime1.distance.gt(1.));
  }

  @Test
  public void parsingTest09() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QRunTime> predicateBuilder =
        new PredicateBuilder<QRunTime>(QRunTime.runTime1);

    final LocalDateTime now = LocalDateTime.now();

    final Predicate result =
        predicateBuilder.getFilter("dateTime lt '" + now.toString() + "'");

    assertThat(result).isEqualTo(QRunTime.runTime1.dateTime.lt(now));
  }

  @Test
  public void parsingTest10() throws IllegalArgumentException, IllegalAccessException {
    final PredicateBuilder<QRunTime> predicateBuilder =
        new PredicateBuilder<QRunTime>(QRunTime.runTime1);

    final Predicate result =
        predicateBuilder
            .getFilter("user.username eq 'admin'");

    assertThat(result).isEqualTo(
        QRunTime.runTime1.user.username.eq("admin"));
  }
}
