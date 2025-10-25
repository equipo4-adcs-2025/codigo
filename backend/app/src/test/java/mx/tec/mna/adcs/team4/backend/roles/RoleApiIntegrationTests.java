package mx.tec.mna.adcs.team4.backend.roles;

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
public class RoleApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringRoleJpaRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        roleRepository.deleteAll();
    }

    @Test
    void shouldCreateRoleAndPersistInDatabase() throws Exception {
        NewRoleRequest request = new NewRoleRequest();
        request.setRoleName("CREATE_USER");
        request.setDisplayName("Create User");
        request.setDescription("Allows creating new users");

        createRole(request)
                .andExpect(status().isCreated());

        assertThat(roleRepository.findAll())
                .anyMatch(role -> role.getRole_name().equals("CREATE_USER"));
    }

    @Test
    void shouldReturnRoleById() throws Exception {
        // Create a new role
        NewRoleRequest request = new NewRoleRequest();
        request.setRoleName("READ_USER");
        request.setDisplayName("Read User");
        request.setDescription("Allows reading user information");

        String responseBody = createRole(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the role ID from the response
        RoleResponse createdRole = getRoleResponse(responseBody);
        String roleId = createdRole.getId().getValue().toString();

        // Perform GET by ID
        mockMvc.perform(get("/api/roles/" + roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("READ_USER"))
                .andExpect(jsonPath("$.displayName").value("Read User"))
                .andExpect(jsonPath("$.type").value("SYSTEM"));
    }

    @Test
    void shouldReturnAllRoles() throws Exception {
        // Create first role
        NewRoleRequest role1 = new NewRoleRequest();
        role1.setRoleName("CREATE_USER");
        role1.setDisplayName("Create User");
        role1.setDescription("Allows creating new users");

        createRole(role1)
                .andExpect(status().isCreated());

        // Create second role
        NewRoleRequest role2 = new NewRoleRequest();
        role2.setRoleName("UPDATE_USER");
        role2.setDisplayName("Update User");
        role2.setDescription("Allows updating user information");

        createRole(role2)
                .andExpect(status().isCreated());

        // Perform GET all
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].roleName").value("CREATE_USER"))
                .andExpect(jsonPath("$[0].type").value("CUSTOM"))
                .andExpect(jsonPath("$[1].roleName").value("UPDATE_USER"))
                .andExpect(jsonPath("$[1].type").value("SYSTEM"));
    }

    @Test
    void shouldUpdateRoleInfoFully() throws Exception {
        // Create role
        NewRoleRequest request = new NewRoleRequest();
        request.setRoleName("DELETE_USER");
        request.setDisplayName("Delete User");
        request.setDescription("Allows deleting users");

        String response = createRole(request)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        RoleResponse created = getRoleResponse(response);
        String roleId = created.getId().getValue().toString();

        // Full update
        FullRoleUpdateRequest update = new FullRoleUpdateRequest();
        update.setRoleName("DELETE_USER_UPDATED");
        update.setDisplayName("Delete User Updated");
        update.setDescription("Updated description for deleting users");

        mockMvc.perform(put("/api/roles/" + roleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("DELETE_USER_UPDATED"))
                .andExpect(jsonPath("$.displayName").value("Delete User Updated"))
                .andExpect(jsonPath("$.type").value("SYSTEM"));
    }

    @Test
    void shouldPartiallyUpdateRoleInfo() throws Exception {
        // Create role
        NewRoleRequest request = new NewRoleRequest();
        request.setRoleName("ORIGINAL_ROLE");
        request.setDisplayName("Original Display Name");
        request.setDescription("Original description");

        String response = createRole(request)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        RoleResponse created = getRoleResponse(response);
        String roleId = created.getId().getValue().toString();

        // Partial update
        PartialRoleUpdateRequest update1 = new PartialRoleUpdateRequest();
        update1.setDisplayName("Updated Display Name");

        patchRole(roleId, update1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("Updated Display Name"))
                .andExpect(jsonPath("$.type").value("SYSTEM"))
                // unchanged
                .andExpect(jsonPath("$.roleName").value("ORIGINAL_ROLE"))
                .andExpect(jsonPath("$.description").value("Original description"));

        PartialRoleUpdateRequest update2 = new PartialRoleUpdateRequest();
        update2.setRoleName("UPDATED_ROLE");
        update2.setDescription("Updated description");

        patchRole(roleId, update2)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("UPDATED_ROLE"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                // unchanged
                .andExpect(jsonPath("$.displayName").value("Updated Display Name"))
                .andExpect(jsonPath("$.type").value("SYSTEM"));
    }

    @Test
    void shouldDeleteRoleById() throws Exception {
        // Create role
        NewRoleRequest request = new NewRoleRequest();
        request.setRoleName("TO_DELETE");
        request.setDisplayName("To Delete");
        request.setDescription("This role will be deleted");

        String response = createRole(request)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        RoleResponse created = getRoleResponse(response);
        String roleId = created.getId().getValue().toString();

        // Delete
        mockMvc.perform(delete("/api/roles/" + roleId))
                .andExpect(status().isOk());

        // Verify deletion
        mockMvc.perform(get("/api/roles/" + roleId))
                .andExpect(status().isNotFound());
    }

    private RoleResponse getRoleResponse(String response) throws JsonProcessingException, JsonMappingException {
        return objectMapper.readValue(response, RoleResponse.class);
    }

    private ResultActions createRole(NewRoleRequest request) throws Exception, JsonProcessingException {
        return mockMvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    private ResultActions patchRole(String roleId, PartialRoleUpdateRequest update)
            throws Exception, JsonProcessingException {

        ObjectMapper patchMapper = new ObjectMapper();
        patchMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mockMvc.perform(patch("/api/roles/" + roleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchMapper.writeValueAsString(update)));
    }

}



