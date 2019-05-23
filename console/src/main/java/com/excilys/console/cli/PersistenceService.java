package com.excilys.console.cli;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.core.Feedback;

@Component
public class PersistenceService {

    private WebTarget target;

    private String token;

    private static final String API_URL = "http://localhost:8080/api/";

    public PersistenceService() {

        target = ClientBuilder.newClient().target(API_URL);
    }

    public boolean login(String login, String password) {

        JSONObject credentials = new JSONObject().put("login", login).put("password", password);
        Response response = target.path("authenticate").request()
                .post(Entity.entity(credentials.toString(), MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200) {
            return false;
        }

        token = new JSONObject(response.readEntity(String.class)).getString("token");
        response.close();
        return true;
    }

    public ComputerDTO get(Long id) {

        return target.path("computers/{id}").resolveTemplate("id", id).request()
                .header("Authorization", "Bearer " + token).get(ComputerDTO.class);
    }

    public boolean create(ComputerDTO dto) {
        Response response = target.path("computers").request().header("Authorization", "Bearer " + token)
                .post(Entity.json(dto));
        if (response.getStatus() != 200) {
            return false;
        }
        Feedback fb = response.readEntity(Feedback.class);
        response.close();
        return fb.getStatus().equals("success");
    }

    public boolean update(ComputerDTO dto) {
        System.out.println(dto);
        Response response = target.path("computers/{id}").resolveTemplate("id", dto.getId()).request()
                .header("Authorization", "Bearer " + token).put(Entity.json(dto));
        if (response.getStatus() != 200) {
            return false;
        }
        Feedback fb = response.readEntity(Feedback.class);
        response.close();
        return fb.getStatus().equals("success");
    }

    public boolean delete(Class<?> clazz, Long id) {
        Response response = target.path((clazz == ComputerDTO.class ? "computers" : "companies") + "/{id}")
                .resolveTemplate("id", id).request().header("Authorization", "Bearer " + token).delete();

        if (response.getStatus() != 200) {
            return false;
        }
        Feedback fb = response.readEntity(Feedback.class);
        response.close();
        return fb.getStatus().equals("success");
    }

    public List<?> list(Class<?> clazz, int page, int itemPerPage) {
        String resource = clazz == ComputerDTO.class ? "computers" : "companies";
        return target.path(resource).queryParam("page", page).queryParam("itemPerPage", itemPerPage).request()
                .header("Authorization", "Bearer " + token).get(List.class);
    }
}
