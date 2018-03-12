package io.maxilog.dao;

import io.maxilog.entity.Role;
import org.springframework.data.repository.CrudRepository;
/**
 * Created by poss on 07/03/2018.
 */
public interface RoleDao extends CrudRepository<Role, Long> {
}
