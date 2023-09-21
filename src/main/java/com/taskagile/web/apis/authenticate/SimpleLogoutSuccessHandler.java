package com.taskagile.web.apis.authenticate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.taskagile.web.results.ApiResult;
import com.taskagile.utils.JsonUtils;

public class SimpleLogoutSuccessHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {

    response.setStatus(HttpStatus.OK.value());
    JsonUtils.write(response.getWriter(), ApiResult.message("로그아웃 되었습니다."));
  }
}
