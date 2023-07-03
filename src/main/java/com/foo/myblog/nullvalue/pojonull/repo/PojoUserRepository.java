package com.foo.myblog.nullvalue.pojonull.repo;

import com.foo.myblog.nullvalue.pojonull.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PojoUserRepository extends JpaRepository<User, Long> {
}
