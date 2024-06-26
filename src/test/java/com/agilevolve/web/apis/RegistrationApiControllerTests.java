package com.agilevolve.web.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.agilevolve.config.SecurityConfiguration;
import com.agilevolve.domain.application.UserService;
import com.agilevolve.domain.model.user.EmailAddressExistsException;
import com.agilevolve.domain.model.user.UsernameExistsException;
import com.agilevolve.utils.JsonUtils;
import com.agilevolve.web.apis.RegistrationApiController;
import com.agilevolve.web.payload.RegistrationPayload;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

/**
 * 회원가입 api 작동 테스트
 *
 * 테스트 메소드는 [작업 단위_테스트 중인 상태_ 예상되는 행동] 명명 규약을 따른다.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = { SecurityConfiguration.class, RegistrationApiController.class })
@WebMvcTest
public class RegistrationApiControllerTests {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService serviceMock;

  @Test
  public void register_blankPayload_shouldFailAndReturn400() throws Exception {
    mvc.perform(post("/api/registrations"))
        .andExpect(status().is(400));
  }

  @Test
  public void register_existedUsername_shouldFailAndReturn400() throws Exception {
    RegistrationPayload payload = new RegistrationPayload();
    payload.setUsername("exist");
    payload.setEmailAddress("test@agilevolve.com");
    payload.setPassword("MyPassword!");

    doThrow(UsernameExistsException.class)
        .when(serviceMock)
        .register(payload.toCommand());

    mvc.perform(
        post("/api/registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(payload)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.message").value("이미 사용중인 사용자명입니다."));
  }

  @Test
  public void register_existedEmailAddress_shouldFailAndReturn400() throws Exception {
    RegistrationPayload payload = new RegistrationPayload();
    payload.setUsername("test");
    payload.setEmailAddress("exist@agilevolve.com");
    payload.setPassword("MyPassword!");

    doThrow(EmailAddressExistsException.class)
        .when(serviceMock)
        .register(payload.toCommand());

    mvc.perform(
        post("/api/registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(payload)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.message").value("이미 사용중인 이메일입니다."));
  }

  @Test
  public void register_validPayload_shouldSucceedAndReturn201() throws Exception {
    RegistrationPayload payload = new RegistrationPayload();
    payload.setUsername("sunny");
    payload.setEmailAddress("sunny@agilevolve.com");
    payload.setPassword("MyPassword!");

    doNothing().when(serviceMock)
        .register(payload.toCommand());

    mvc.perform(
        post("/api/registrations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(payload)))
        .andExpect(status().is(201));
  }

}
