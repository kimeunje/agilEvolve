package com.agilevolve.infrastructure.repository;

import jakarta.persistence.EntityManager;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserId;
import com.agilevolve.domain.model.user.UserRepository;

/**
 * 사용자 관련 인프라 서비스
 *
 * @UserRepository 하이버네이트 구현체
 */
@Repository
public class HibernateUserRepository extends HibernateSupport<User> implements UserRepository {

  public HibernateUserRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public User findByUsername(String username) {
    Query<User> query = getSession().createQuery("from User where username = :username", User.class);
    query.setParameter("username", username);
    return query.uniqueResult();
  }

  @Override
  public User findByEmailAddress(String emailAddress) {
    Query<User> query = getSession().createQuery("from User where emailAddress = :emailAddress", User.class);
    query.setParameter("emailAddress", emailAddress);
    return query.uniqueResult();
  }

  @Override
  public User findById(UserId userId) {
    Query<User> query = getSession().createQuery("from User where id = :id", User.class);
    query.setParameter("id", userId.value());
    return query.uniqueResult();
  }
}
