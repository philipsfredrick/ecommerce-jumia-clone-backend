package com.nonso.ecommercejumiaclone.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonso.ecommercejumiaclone.dto.ApiError;
import com.nonso.ecommercejumiaclone.dto.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static com.nonso.ecommercejumiaclone.dto.ErrorCode.INVALID_TOKEN;
import static com.nonso.ecommercejumiaclone.dto.ErrorCode.MISSING_TOKEN;
import static com.nonso.ecommercejumiaclone.utils.GeneralConstants.HEADER_STRING;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    public JwtAuthenticationEntryPoint(@Qualifier("jumiaCloneObjectMapper") ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);

        ErrorCode errorCode = null;
        boolean expiredToken = false;

        response.addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        ApiError error = buildApiError(request, request.getHeader(HEADER_STRING));
        OutputStream outputStream = response.getOutputStream();
        mapper.writeValue(outputStream, error);
        outputStream.flush();
    }

    @NotNull
    private static ApiError buildApiError(HttpServletRequest request, String token) {
        if (StringUtils.isBlank(token)) {
            return new ApiError(UNAUTHORIZED.value(), "Full authentication is required to access this resource",
                    request.getRequestURI(), MISSING_TOKEN);
        } else {
            return new ApiError(UNAUTHORIZED.value(), "Invalid token", request.getRequestURI(), INVALID_TOKEN);
        }
    }
}
