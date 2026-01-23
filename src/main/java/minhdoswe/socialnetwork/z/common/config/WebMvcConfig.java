package minhdoswe.socialnetwork.z.common.config;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.common.annotation.CurrentUserIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // This is the missing link!
        resolvers.add(currentUserIdArgumentResolver);
    }
}