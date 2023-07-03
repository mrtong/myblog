package com.foo.myblog.nullvalue.pojonull.repo;

import com.foo.myblog.nullvalue.pojonull.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}
