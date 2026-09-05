package com.sriram.themevest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sriram.themevest.dto.LoginRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest( properties = "logging.level.org.springframework.security=TRACE")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ThemeSecurityIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getThemesWithoutCredentialsReturns200() throws Exception {

        mockMvc.perform(get("/themes"))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    void postThemesWithoutCredentialsReturns401() throws Exception
    {
        mockMvc.perform(post("/themes"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


 /*   @Test
    void postThemesWithInvalidCredentialsReturns401() throws  Exception
    {
        mockMvc.perform(post("/themes")
                        .with(httpBasic("admin","wrong-pwd")))
                .andDo(print()).andExpect(status().isUnauthorized());

    }*/

 /*   @Test
    void postThemesAsViewerReturns403() throws  Exception
    {
        mockMvc.perform(post("/themes")
                        .with(httpBasic("viewer","abc123")))
                .andDo(print()).andExpect(status().isForbidden());

    }*/

    /*@Test
    void postThemesAsAdminReturns200()  throws  Exception
    {
        mockMvc.perform(post("/themes")
                        .with(httpBasic("admin","abc124"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                "name":"Artificial Intelligence",
                                "description": "Companies focused on artificial intelligence",
                                 "riskLevel": "HIGH"
                                }
                                """
                        )
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Artificial Intelligence"))
                .andExpect(cookie().doesNotExist("JSESSIONID"));
    }*/

    @Test
    void postTokenWithValidAdminCredentialsReturnsJwt() throws  Exception
    {
        mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                        {"username":"admin","password":"abc124"}
                        """
                )
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").value(1800));

    }

    @Test
    void postTokenWithInvalidCredentialsReturns401() throws Exception
    {

        mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {"username":"admin","password":"wrong-password"}
                                """
                        )
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.accessToken").doesNotExist());
    }

    private String obtainToken(String username, String password)
            throws Exception {

        LoginRequest loginRequest =
                new LoginRequest(username, password);

        String loginJson =
                objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper
                .readTree(result.getResponse().getContentAsString())
                .get("accessToken")
                .asText();
    }

    @Test
    void postThemesWithAdminJwtReturns200() throws Exception {
        String token = obtainToken("admin", "abc124");

        mockMvc.perform(post("/themes")
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Cloud Security",
                              "description": "Companies focused on cloud security",
                              "riskLevel": "HIGH"
                            }
                            """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Cloud Security"))
                .andExpect(cookie().doesNotExist("JSESSIONID"));
    }

    @Test
    void postThemesWithViewerJwtReturns403() throws Exception
    {
        String token = obtainToken("viewer", "abc123");

        mockMvc.perform(post("/themes")
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(cookie().doesNotExist("JSESSIONID"));

    }

    @Test
    void postThemesWithMalformedJwtReturns401() throws  Exception
    {
        mockMvc.perform(post("/themes")
                .header(HttpHeaders.AUTHORIZATION,"Bearer not-a-valid-jwt"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(cookie().doesNotExist("JSESSIONID"));
    }

    @Test
    void postThemesWithBasicCredentialsReturns401() throws  Exception
    {
        mockMvc.perform(post("/themes")
                .with(httpBasic("admin","abc124")))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }
    }
