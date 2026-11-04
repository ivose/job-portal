package com.zosh.job.config;

import com.zosh.job.domain.UserRole;
import com.zosh.job.jwt.JwtConstant;
import com.zosh.job.jwt.JwtUtil;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfig {

  private final JwtUtil jwtUtil;

  public RouteConfig(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Bean
  public RouterFunction<ServerResponse> wauthRoutes() {
    return GatewayRouterFunctions.route("auth-routes")
        .route(RequestPredicates.path("/auth/**"), HandlerFunctions.http())
        .filter(LoadBalancerFilterFunctions.lb("job-portal-user-service"))
        .build();
  }

  @Bean // admin-only routes (jwt + ROLE_ADMIN)
  public RouterFunction<ServerResponse> adminRoutes() {
    return GatewayRouterFunctions.route("admin-routes")
        .route(RequestPredicates.path("/api/admin/**"), HandlerFunctions.http())
        .filter(LoadBalancerFilterFunctions.lb("job-portal-user-service"))
        .before(this::jwtAuthFilter)
        .before(request -> requiredRole(request, UserRole.ROLE_ADMIN))
        .build();
  }

  @Bean // Protected Routes (JWT required)
  public RouterFunction<ServerResponse> userServiceRoutes() {
    return GatewayRouterFunctions.route("user-service-routes")
        .route(RequestPredicates.path("/api/users/**"), HandlerFunctions.http())
        .filter(LoadBalancerFilterFunctions.lb("job-portal-user-service"))
        .before(this::jwtAuthFilter)
        .build();
  }

  @Bean // Protected Routes (JWT required)
  public RouterFunction<ServerResponse> companyServiceRoutes() {
    return GatewayRouterFunctions.route("company-service-routes")
        .route(RequestPredicates.path("/api/company/**"), HandlerFunctions.http())
        .filter(LoadBalancerFilterFunctions.lb("job-portal-company-service"))
        .before(this::jwtAuthFilter)
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> jobServiceRoutes() {
    return GatewayRouterFunctions.route("job-service-routes")
        .route(
            RequestPredicates.path("/api/jobs/**")
                .or(RequestPredicates.path("/api/job-categories/**"))
                .or(RequestPredicates.path("/api/job-skills/**"))
                .or(RequestPredicates.path("/api/job-tags/**")),
            HandlerFunctions.http())
        .filter(LoadBalancerFilterFunctions.lb("job-portal-job-service"))
        .before(this::jwtAuthFilter)
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> applicationServiceRoutes() {
    return GatewayRouterFunctions.route("application-service-routes")
        .route(
            RequestPredicates.path("/api/applications/**")
                .or(RequestPredicates.path("/api/application-notes/**")),
            HandlerFunctions.http())
        .filter(LoadBalancerFilterFunctions.lb("job-portal-application-service"))
        .before(this::jwtAuthFilter)
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> resumeServiceRoutes() {
    return GatewayRouterFunctions.route("resume-service-routes")
        .route(RequestPredicates.path("/api/resumes/**"), HandlerFunctions.http())
        .filter(LoadBalancerFilterFunctions.lb("job-portal-resume-service"))
        .before(this::jwtAuthFilter)
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> preferenceServiceRoutes() {
    return GatewayRouterFunctions.route("preference-service-routes")
        .route(RequestPredicates.path("/api/preferences/**"), HandlerFunctions.http())
        .filter(LoadBalancerFilterFunctions.lb("job-portal-preferences"))
        .before(this::jwtAuthFilter)
        .build();
  }

  private ServerRequest jwtAuthFilter(ServerRequest request) {
    String authHeader = request.headers().firstHeader(JwtConstant.JWT_HEADER);
    if (authHeader == null || !authHeader.startsWith(JwtConstant.TOKEN_PREFIX)) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED, "missing or invalid authorization header");
    }
    // Bearer token
    String token = authHeader.substring(JwtConstant.TOKEN_PREFIX.length());
    if (!jwtUtil.isTokenValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "missing or expired jwt token");
    }
    String email = jwtUtil.extractEmail(token);
    String authorities = jwtUtil.extractAuthorities(token);
    Long userId = jwtUtil.extractUserId(token);
    return ServerRequest.from(request)
        // .header("X-User-Id", String.valueOf(userId))
        // .header("X-User-Email", email)
        // .header("X-User-Role", authorities)
        .headers(
            headers -> {
              headers.remove("X-User-Id");
              headers.remove("X-User-Email");
              headers.remove("X-User-Role");
              headers.set("X-User-Id", String.valueOf(userId));
              headers.set("X-User-Email", email);
              headers.set("X-User-Role", authorities);
            })
        .build();
  }

  private ServerRequest requiredRole(ServerRequest request, UserRole role) {
    String authorities = request.headers().firstHeader("X-User-Role");
    if (authorities == null || !authorities.contains(role.toString())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "missing required role");
    }
    return request;
  }
}
