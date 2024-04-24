package com.example.demologinregproj.service.impl;

import com.example.demologinregproj.dto.UserDto;
import com.example.demologinregproj.entity.Role;
import com.example.demologinregproj.entity.User;
import com.example.demologinregproj.repo.RoleRepo;
import com.example.demologinregproj.repo.UserRepo;
import com.example.demologinregproj.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private ModelMapper mapper;
    private PasswordEncoder passwordEncoder;


    @Override
    public void saveMyUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));


        Role role = roleRepo.findByRoleName("ROLE_ADMIN");
        if(role == null){
            role = createRole("ROLE_ADMIN");
        }
        user.setRoles(Arrays.asList(role));
        userRepo.save(user);
    }

    @Override
    public User findUserByEmailId(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> list = userRepo.findAll();
        List<UserDto> listdto= list.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
        return listdto;
    }


    public Role createRole(String roleName){
        Role role = new Role();
        role.setRoleName(roleName);
        return roleRepo.save(role);
    }


}
