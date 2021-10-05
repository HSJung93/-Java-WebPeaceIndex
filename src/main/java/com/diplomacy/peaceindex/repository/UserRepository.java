package com.diplomacy.peaceindex.repository;

import com.diplomacy.peaceindex.model.Board;
import com.diplomacy.peaceindex.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

// 빈 UserRepository를 설정한 뒤, AccountController의 register에서 이용한다.
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, CustomizedUserRepository {
    // `@EntityGraph(attributePaths = { "boards" })`로 LAZY 옵션을 준 boards를 입력하면, FetchType을 무시하고 join방식으로 데이터를 한꺼번에 불러와서 해결한다.
    // N+1의 쿼리가 만들어져 성능 상의 문제가 일어날 경우 이렇게 join 방식으로 쿼리를 짜주는 `@EntityGraph` 어노테이션으로 해결할 수 있다.
    @EntityGraph(attributePaths = { "boards" })
    List<User> findAll();

    // findBy{Columnname}의 형식
    User findByUsername(String username);

    // Spring Data JPA의 문서를 참고하여 JPQL 쿼리를 작성한다.
    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String username);

    @Query(value = "select * from User u where u.username like %?1%", nativeQuery = true)
    List<User> findByUsernameNativeQuery(String username);
}
