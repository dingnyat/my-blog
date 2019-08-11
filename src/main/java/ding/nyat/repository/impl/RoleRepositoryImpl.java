package ding.nyat.repository.impl;

import ding.nyat.entity.RoleEntity;
import ding.nyat.repository.RepositoryAbstract;
import ding.nyat.repository.RoleRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleRepositoryImpl extends RepositoryAbstract<Integer, RoleEntity> implements RoleRepository {
}
