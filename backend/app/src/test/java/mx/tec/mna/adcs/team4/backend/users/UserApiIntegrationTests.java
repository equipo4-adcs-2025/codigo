package mx.tec.mna.adcs.team4.backend.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringUserJpaRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUserAndPersistInDatabase() throws Exception {
        NewUserRequest request = new NewUserRequest();
        request.setEmail("test@example.com");
        request.setPassword("securePassword123");
        request.setFirstName("Alice");
        request.setLastName("Smith");
        request.setTitle("Engineer");
        request.setPractice("Software");
        request.setCountry("Mexico");
        request.setLanId("LAN123456");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        assertThat(userRepository.findAll())
                .anyMatch(user -> user.getEmail().equals("test@example.com"));
    }


@Test
    void shouldReturnUserById() throws Exception {
        // Create a new user
        NewUserRequest request = new NewUserRequest();
        request.setEmail("getbyid@example.com");
        request.setPassword("securePassword123");
        request.setFirstName("Bob");
        request.setLastName("Johnson");
        request.setTitle("Developer");
        request.setPractice("Engineering");
        request.setCountry("USA");
        request.setLanId("LAN987654");

        String responseBody = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the user ID from the response
        UserResponse createdUser = objectMapper.readValue(responseBody, UserResponse.class);
        String userId = createdUser.getId().getValue().toString();

        // Perform GET by ID
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("getbyid@example.com"))
                .andExpect(jsonPath("$.firstName").value("Bob"));
    }


}
