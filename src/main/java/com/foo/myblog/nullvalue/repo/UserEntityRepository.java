package com.foo.myblog.nullvalue.repo;

import com.foo.myblog.nullvalue.pojonull.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}
