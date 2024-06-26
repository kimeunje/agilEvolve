package com.agilevolve.web.apis.authenticate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.agilevolve.utils.JsonUtils;
import com.agilevolve.web.results.ApiResult;

public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    response.setStatus(HttpStatus.OK.value());
    JsonUtils.write(response.getWriter(), ApiResult.message("authenticated"));
  }
}
