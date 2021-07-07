package org.course.service;

import org.course.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService service;
    
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = service.select(login);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown login " + login);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), user.getAuthorities());
        }
    }
}
