package com.excilys.cdb.webapp.config.security;

import static com.excilys.cdb.core.User.MANAGER;
import static com.excilys.cdb.core.User.USER;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.persistence.dao.UserDAO;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserDAO userDAO;

    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<com.excilys.cdb.core.User> optUser = userDAO.findByLogin(username);

        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist.");
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(USER));

        if (optUser.get().getRole().equals(MANAGER)) {
            authorities.add(new SimpleGrantedAuthority(MANAGER));
        }
        return buildUserForAuthentication(optUser.get(), authorities);

    }

    private User buildUserForAuthentication(com.excilys.cdb.core.User user, List<GrantedAuthority> authorities) {
        return new User(user.getLogin(), user.getPassword(), true, true, true, true, authorities);
    }

}
