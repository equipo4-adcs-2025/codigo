package mx.tec.mna.adcs.team4.backend.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }

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

        createUser(request)
                .andExpect(status().isCreated());

        assertThat(userRepository.findAll())
                .anyMatch(user -> user.getEmail().equals("test@example.com"));
    }

    @Test
    @Disabled("not working yet")
    void shouldFailWhenEmailIsDuplicated() throws Exception {
        NewUserRequest user1 = new NewUserRequest();
        user1.setEmail("duplicate@example.com");
        user1.setPassword("password123");
        user1.setFirstName("Alice");
        user1.setLanId("LAN001");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isCreated());

        NewUserRequest user2 = new NewUserRequest();
        user2.setEmail("duplicate@example.com"); // same email
        user2.setPassword("password456");
        user2.setFirstName("Bob");
        user2.setLanId("LAN002");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldFailWhenLanIdIsDuplicated() throws Exception {
        NewUserRequest user1 = new NewUserRequest();
        user1.setEmail("user1@example.com");
        user1.setPassword("password123");
        user1.setFirstName("Alice");
        user1.setLanId("LAN123");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isCreated());

        NewUserRequest user2 = new NewUserRequest();
        user2.setEmail("user2@example.com");
        user2.setPassword("password456");
        user2.setFirstName("Bob");
        user2.setLanId("LAN123"); // same LAN ID

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isConflict());
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

        String responseBody = createUser(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the user ID from the response
        UserResponse createdUser = getUserResponse(responseBody);
        String userId = createdUser.getId().getValue().toString();

        // Perform GET by ID
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("getbyid@example.com"))
                .andExpect(jsonPath("$.firstName").value("Bob"));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        // Create first user
        NewUserRequest user1 = new NewUserRequest();
        user1.setEmail("user1@example.com");
        user1.setPassword("password123");
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        user1.setTitle("Engineer");
        user1.setLanId("LAN001");

        createUser(user1)
                .andExpect(status().isCreated());

        // Create second user
        NewUserRequest user2 = new NewUserRequest();
        user2.setEmail("user2@example.com");
        user2.setPassword("password456");
        user2.setFirstName("Bob");
        user2.setLastName("Johnson");
        user2.setTitle("Architect");
        user2.setPractice("Infrastructure");
        user2.setCountry("USA");
        user2.setLanId("LAN002");

        createUser(user2)
                .andExpect(status().isCreated());

        // Perform GET all
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));
    }

    @Test
    void shouldUpdateUserInfoFully() throws Exception {
        // Create user
        NewUserRequest request = new NewUserRequest();
        request.setEmail("update@example.com");
        request.setPassword("password123");
        request.setFirstName("Initial");
        request.setLanId("LAN999");

        String response = createUser(request)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        UserResponse created = getUserResponse(response);
        String userId = created.getId().getValue().toString();

        // Full update
        FullUserUpdateRequest update = new FullUserUpdateRequest();
        update.setFirstName("Updated");
        update.setLastName("User");
        update.setTitle("Lead");
        update.setPractice("AI");
        update.setCountry("Mexico");
        update.setLanId("LAN999");

        mockMvc.perform(put("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.title").value("Lead"));
    }

    @Test
    void shouldPartiallyUpdateUserInfo() throws Exception {
        // Create user
        NewUserRequest request = new NewUserRequest();
        request.setEmail("partial@example.com");
        request.setPassword("password123");
        request.setFirstName("OriginalName");
        request.setLastName("OriginalLastName");
        request.setLanId("LAN888");
        request.setTitle("Junior");
        request.setCountry("Original Country");
        request.setPractice("Original Practice");

        String response = createUser(request)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        UserResponse created = getUserResponse(response);
        String userId = created.getId().getValue().toString();

        // Partial update
        PartialUserUpdateRequest update1 = new PartialUserUpdateRequest();
        update1.setTitle("Senior");
        update1.setCountry("New Country");

        patchUser(userId, update1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Senior"))
                .andExpect(jsonPath("$.country").value("New Country"))
                // unchanged
                .andExpect(jsonPath("$.firstName").value("OriginalName"))
                .andExpect(jsonPath("$.lastName").value("OriginalLastName"))
                .andExpect(jsonPath("$.lanId").value("LAN888"))
                .andExpect(jsonPath("$.practice").value("Original Practice"));

        PartialUserUpdateRequest update2 = new PartialUserUpdateRequest();
        update2.setFirstName("Benjamin");
        update2.setLastName("Cisneros");

        patchUser(userId, update2)
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.lastName").value("Cisneros"))
                .andExpect(jsonPath("$.firstName").value("Benjamin"))
                // unchanged
                .andExpect(jsonPath("$.title").value("Senior"))
                .andExpect(jsonPath("$.country").value("New Country"))
                .andExpect(jsonPath("$.lanId").value("LAN888"))
                .andExpect(jsonPath("$.practice").value("Original Practice"));

        PartialUserUpdateRequest update3 = new PartialUserUpdateRequest();
        update3.setPractice("New Practice");
        update3.setLanId("NEW_LAN_ID");

        patchUser(userId, update3)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lanId").value("NEW_LAN_ID"))
                .andExpect(jsonPath("$.practice").value("New Practice"))
                // unchanged
                .andExpect(jsonPath("$.lastName").value("Cisneros"))
                .andExpect(jsonPath("$.firstName").value("Benjamin"))
                .andExpect(jsonPath("$.title").value("Senior"))
                .andExpect(jsonPath("$.country").value("New Country"));
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        // Create user
        NewUserRequest request = new NewUserRequest();
        request.setEmail("delete@example.com");
        request.setPassword("password123");
        request.setFirstName("ToDelete");
        request.setLanId("LAN777");

        String response = createUser(request)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        UserResponse created = getUserResponse(response);
        String userId = created.getId().getValue().toString();

        // Delete
        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isOk());

        // Verify deletion
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isNotFound());
    }

    private UserResponse getUserResponse(String response) throws JsonProcessingException, JsonMappingException {
        return objectMapper.readValue(response, UserResponse.class);
    }

    private ResultActions createUser(NewUserRequest request) throws Exception, JsonProcessingException {
        return mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    private ResultActions patchUser(String userId, PartialUserUpdateRequest update)
            throws Exception, JsonProcessingException {

        ObjectMapper patchMapper = new ObjectMapper();
        patchMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mockMvc.perform(patch("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchMapper.writeValueAsString(update)));
    }

}
