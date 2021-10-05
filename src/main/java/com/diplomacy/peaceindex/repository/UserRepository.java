package com.diplomacy.peaceindex.repository;

import com.diplomacy.peaceindex.model.Board;
import com.diplomacy.peaceindex.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, CustomizedUserRepository {
    @EntityGraph(attributePaths = { "boards" }) // fetchType이 무시된다. findAll을 호출할 때 한번에 left outer join을 해서 n+1(여러 쿼리) 해결한다.
    List<User> findAll();

    User findByUsername(String username); // findBy{Columnname}만 하면 알아서...

    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String username);

    @Query(value = "select * from User u where u.username like %?1%", nativeQuery = true)
    List<User> findByUsernameNativeQuery(String username);
}
