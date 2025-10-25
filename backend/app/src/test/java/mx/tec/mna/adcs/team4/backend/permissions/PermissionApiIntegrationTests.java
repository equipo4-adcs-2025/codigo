package mx.tec.mna.adcs.team4.backend.permissions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
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
public class PermissionApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringPermissionJpaRepository permissionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        permissionRepository.deleteAll();
    }

    @Test
    void shouldCreatePermissionAndPersistInDatabase() throws Exception {
        NewPermissionRequest request = new NewPermissionRequest();
        request.setName("CREATE_USER");
        request.setDisplayName("Create User");
        request.setDescription("Allows creating new users");

        createPermission(request)
                .andExpect(status().isCreated());

        assertThat(permissionRepository.findAll())
                .anyMatch(permission -> permission.getName().equals("CREATE_USER"));
    }

    @Test
    void shouldReturnPermissionById() throws Exception {
        // Create a new permission
        NewPermissionRequest request = new NewPermissionRequest();
        request.setName("READ_USER");
        request.setDisplayName("Read User");
        request.setDescription("Allows reading user information");

        String responseBody = createPermission(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the permission ID from the response
        PermissionResponse createdPermission = getPermissionResponse(responseBody);
        String permissionId = createdPermission.getId().getValue().toString();

        // Perform GET by ID
        mockMvc.perform(get("/api/permissions/" + permissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("READ_USER"))
                .andExpect(jsonPath("$.displayName").value("Read User"));
    }

    @Test
    void shouldReturnAllPermissions() throws Exception {
        // Create first permission
        NewPermissionRequest permission1 = new NewPermissionRequest();
        permission1.setName("CREATE_USER");
        permission1.setDisplayName("Create User");
        permission1.setDescription("Allows creating new users");

        createPermission(permission1)
                .andExpect(status().isCreated());

        // Create second permission
        NewPermissionRequest permission2 = new NewPermissionRequest();
        permission2.setName("UPDATE_USER");
        permission2.setDisplayName("Update User");
        permission2.setDescription("Allows updating user information");

        createPermission(permission2)
                .andExpect(status().isCreated());

        // Perform GET all
        mockMvc.perform(get("/api/permissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("CREATE_USER"))
                .andExpect(jsonPath("$[1].name").value("UPDATE_USER"));
    }

    @Test
    void shouldUpdatePermissionInfoFully() throws Exception {
        // Create permission
        NewPermissionRequest request = new NewPermissionRequest();
        request.setName("DELETE_USER");
        request.setDisplayName("Delete User");
        request.setDescription("Allows deleting users");

        String response = createPermission(request)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        PermissionResponse created = getPermissionResponse(response);
        String permissionId = created.getId().getValue().toString();

        // Full update
        FullPermissionUpdateRequest update = new FullPermissionUpdateRequest();
        update.setName("DELETE_USER_UPDATED");
        update.setDisplayName("Delete User Updated");
        update.setDescription("Updated description for deleting users");
        update.setEnabled(false);

        mockMvc.perform(put("/api/permissions/" + permissionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("DELETE_USER_UPDATED"))
                .andExpect(jsonPath("$.displayName").value("Delete User Updated"));
    }

    @Test
    void shouldPartiallyUpdatePermissionInfo() throws Exception {
        // Create permission
        NewPermissionRequest request = new NewPermissionRequest();
        request.setName("ORIGINAL_PERMISSION");
        request.setDisplayName("Original Display Name");
        request.setDescription("Original description");

        String response = createPermission(request)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        PermissionResponse created = getPermissionResponse(response);
        String permissionId = created.getId().getValue().toString();

        // Partial update
        PartialPermissionUpdateRequest update1 = new PartialPermissionUpdateRequest();
        update1.setDisplayName("Updated Display Name");
        update1.setEnabled(false);

        patchPermission(permissionId, update1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("Updated Display Name"))
                .andExpect(jsonPath("$.enabled").value(false))
                // unchanged
                .andExpect(jsonPath("$.name").value("ORIGINAL_PERMISSION"))
                .andExpect(jsonPath("$.description").value("Original description"));

        PartialPermissionUpdateRequest update2 = new PartialPermissionUpdateRequest();
        update2.setName("UPDATED_PERMISSION");
        update2.setDescription("Updated description");

        patchPermission(permissionId, update2)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UPDATED_PERMISSION"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                // unchanged
                .andExpect(jsonPath("$.displayName").value("Updated Display Name"))
                .andExpect(jsonPath("$.enabled").value(false));
    }

    @Test
    void shouldDeletePermissionById() throws Exception {
        // Create permission
        NewPermissionRequest request = new NewPermissionRequest();
        request.setName("TO_DELETE");
        request.setDisplayName("To Delete");
        request.setDescription("This permission will be deleted");

        String response = createPermission(request)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        PermissionResponse created = getPermissionResponse(response);
        String permissionId = created.getId().getValue().toString();

        // Delete
        mockMvc.perform(delete("/api/permissions/" + permissionId))
                .andExpect(status().isOk());

        // Verify deletion
        mockMvc.perform(get("/api/permissions/" + permissionId))
                .andExpect(status().isNotFound());
    }

    private PermissionResponse getPermissionResponse(String response) throws JsonProcessingException, JsonMappingException {
        return objectMapper.readValue(response, PermissionResponse.class);
    }

    private ResultActions createPermission(NewPermissionRequest request) throws Exception, JsonProcessingException {
        return mockMvc.perform(post("/api/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    private ResultActions patchPermission(String permissionId, PartialPermissionUpdateRequest update)
            throws Exception, JsonProcessingException {

        ObjectMapper patchMapper = new ObjectMapper();
        patchMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mockMvc.perform(patch("/api/permissions/" + permissionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchMapper.writeValueAsString(update)));
    }

}



