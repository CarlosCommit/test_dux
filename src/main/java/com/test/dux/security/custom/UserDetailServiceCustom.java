package com.test.dux.security.custom;

import com.test.dux.entity.UserEntity;
import com.test.dux.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceCustom implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailServiceCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user =  userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Usuario no existe"));

        List<GrantedAuthority> roles = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRol());

        roles.add(grantedAuthority);

        return new User(username, user.getPassword() ,roles);
    }
}