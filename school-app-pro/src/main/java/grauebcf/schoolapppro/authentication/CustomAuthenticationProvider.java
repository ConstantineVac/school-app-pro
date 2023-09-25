//package grauebcf.schoolapppro.authentication;
//
//import grauebcf.schoolapppro.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.support.MessageSourceAccessor;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.Collections;
//import java.util.Locale;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserRepository userRepository;
//    private final MessageSource messageSource;
//
//    @Autowired
//    public CustomAuthenticationProvider(UserRepository userRepository, MessageSource messageSource) {
//        this.userRepository = userRepository;
//        this.messageSource = messageSource;
//    }
//    private MessageSourceAccessor accessor;
//
//    @PostConstruct
//    private void init() {
//        accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String email = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        if (!userRepository.isUserValid(email,password)) {
//            throw new BadCredentialsException(accessor.getMessage("badCredentials"));
//        }
//        return new UsernamePasswordAuthenticationToken(email,password, Collections.<GrantedAuthority>emptyList());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
