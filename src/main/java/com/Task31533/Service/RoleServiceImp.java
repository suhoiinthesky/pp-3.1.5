package com.Task31533.Service;


import com.Task31533.model.Role;
import com.Task31533.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }
    @Transactional
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
    @Transactional
    public Set<Role> getSetOfRoles(String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(getRoleByName(role));
        }
        return roleSet;
    }
    @Transactional
    public List<Role> getRoleList() {
        return roleRepository.findAll();
    }
}
