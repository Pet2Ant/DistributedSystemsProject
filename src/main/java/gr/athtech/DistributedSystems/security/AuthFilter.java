package gr.athtech.DistributedSystems.security;

import jakarta.annotation.Priority;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    public AuthFilter() {
    }
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Basic")) {
            // Extract the Base64 encoded username and password from the header
            String encodedCredentials = authorizationHeader.substring("Basic".length()).trim();
            String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials));

            // The credentials are in the format "username:password"
            String[] credentials = decodedCredentials.split(":", 2);
            String user = credentials[0];
            String password = credentials[1];

            // Validate user credentials
            if (!validateUser(user, password)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            // Check user's role against allowed roles
            Method method = resourceInfo.getResourceMethod();
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAllowed.value()));

                // Get the user's role from the request context
                String role = getUserRole(user);

                if (!rolesSet.contains(role)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    return;
                }
            }

            requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, authorizationHeader);
        }
    }
    private boolean validateUser(String user,String password)
    {
        if(user.equals("admin") && password.equals ("1234")) return true;
        if(user.equals("physician") && password.equals ("1234")) return true;
        return false;
    }
    private String getUserRole(String user) {
        if(user.equals("admin")) return "ADMIN";
        if(user.equals("physician")) return "PHYSICIAN";
        return "user";
    }
}
