package auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import model.beans.org.Group;
import model.beans.org.User;
import model.dao.UserDAO;
import model.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static final String ROLE_PREFIX = "ROLE_";
	@Autowired
	private UserDAO userDao;
	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		User user = userDao.findUserByLogin(username);
		if (user == null)
			throw new BadCredentialsException("Error: incorrect login or password!");

		String saltedPassword = password + user.getSalt();
		if (!userService.matchPassword(saltedPassword, user.getPassword()))
			throw new BadCredentialsException("Error: passord is incorrect!");

		List<Group> groups = userDao.getUserGroups(user);
		
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		for(Group group: groups)
			grantedAuths.add(new SimpleGrantedAuthority(ROLE_PREFIX + group.getName()));
		
		return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
