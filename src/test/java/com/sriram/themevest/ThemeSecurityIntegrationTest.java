package com.sriram.themevest;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest( properties = "logging.level.org.springframework.security=TRACE")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ThemeSecurityIntegrationTest {
    @Autowired
    private MockMvc mockMvc;


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
    @Test
    void postThemesWithInvalidCredentialsReturns401() throws  Exception
    {
        mockMvc.perform(post("/themes")
                        .with(httpBasic("admin","wrong-pwd")))
                .andDo(print()).andExpect(status().isUnauthorized());

    }

    @Test
    void postThemesAsViewerReturns403() throws  Exception
    {
        mockMvc.perform(post("/themes")
                        .with(httpBasic("viewer","abc123")))
                .andDo(print()).andExpect(status().isForbidden());

    }

    @Test
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
                .andExpect(jsonPath("$.name").value("Artificial Intelligence"));
    }

    }
