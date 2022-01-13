package ua.com.alevel.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.com.alevel.facade.TokenFacade;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AuthFilter extends OncePerRequestFilter {

    private final TokenFacade tokenFacade;

    public AuthFilter(TokenFacade tokenFacade) {
        this.tokenFacade = tokenFacade;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenContent = tokenFacade.findNewToken().getContent();

        String token = request.getHeader("x_auth_token");
        if (StringUtils.isBlank(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid credentials");
            return;
        }
//        if (token.equals("accepted")) {
        if (token.equals(tokenContent)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid credentials");
        }
    }
}
