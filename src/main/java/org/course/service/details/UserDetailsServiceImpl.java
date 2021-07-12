package org.course.service.details;

import org.course.model.user.User;
import org.course.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService service;

    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = service.selectForAutorize(login);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown login " + login);
        } else {
            return new org.springframework.security.core.userdetails.
                    User(user.getLogin(), user.getPassword(), user.getAuthorities());
        }
    }
}
