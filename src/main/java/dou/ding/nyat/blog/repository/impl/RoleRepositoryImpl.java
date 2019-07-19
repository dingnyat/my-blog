package dou.ding.nyat.blog.repository.impl;

import dou.ding.nyat.blog.entity.RoleEntity;
import dou.ding.nyat.blog.repository.RepositoryAbstract;
import dou.ding.nyat.blog.repository.RoleRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleRepositoryImpl extends RepositoryAbstract<Integer, RoleEntity> implements RoleRepository {
}
