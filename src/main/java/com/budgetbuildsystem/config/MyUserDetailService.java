package com.budgetbuildsystem.config;

import com.budgetbuildsystem.repository.IUserRepository;
import com.budgetbuildsystem.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final IUserService userService;
    private final IUserRepository repository;

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         Optional<MyUser> user = repository.findByUsername(username);

        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        } */
    /*
        Optional<User> user = userService.findByUser(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(User user) {
        if (user.getRoles() == null) {
            return new String[]{"USER"};
        }
        return user.getRoles().toString().split(",");
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("This username is not found: " + username));
        /*return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );*/
    }
}
