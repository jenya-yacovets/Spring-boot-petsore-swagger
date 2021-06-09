package by.tms.swager.interceptor;

import by.tms.swager.exception.UserNotFoundException;
import by.tms.swager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationUserInterceptor implements HandlerInterceptor {
    private final UserService userService;

    public AuthorizationUserInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-api-key");
        try {
            userService.checkAuthorizationUser(token);
            return true;
        } catch(UserNotFoundException e) {
            response.setStatus(401);
            return false;
        }
    }
}
