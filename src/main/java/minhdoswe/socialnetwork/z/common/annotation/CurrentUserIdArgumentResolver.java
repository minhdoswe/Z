package minhdoswe.socialnetwork.z.common.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // Only run this logic if the parameter is annotated with @CurrentUserId
        return parameter.hasParameterAnnotation(CurrentUserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        // 1. Get the Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            return null; // Or throw generic Unauthorized exception
        }

        // 2. Get the Claim
        Object rawId = jwt.getSubject(); // Change "userId" to whatever your key is

        // 3. Safe Conversion (Fixes the Integer/Long crash)
        if (rawId instanceof Number number) {
            return number.longValue();
        } else if (rawId instanceof String str) {
            return Long.valueOf(str);
        }

        return null;
    }
}