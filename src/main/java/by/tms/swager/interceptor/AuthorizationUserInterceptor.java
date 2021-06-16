package by.tms.swager.interceptor;

import by.tms.swager.entity.User;
import by.tms.swager.exception.UserNotFoundException;
import by.tms.swager.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class AuthorizationUserInterceptor implements HandlerInterceptor {
    private final UserService userService;

    public AuthorizationUserInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UUID token;

        try {
            token = UUID.fromString(request.getHeader("X-api-key"));
        } catch(IllegalArgumentException e) {
            response.setStatus(400);
            return false;
        }

        try {
            User user = userService.checkAuthorizationUser(token);
            request.setAttribute("user", user);
            return true;
        } catch(UserNotFoundException e) {
            response.setStatus(401);
            return false;
        }
    }
}
