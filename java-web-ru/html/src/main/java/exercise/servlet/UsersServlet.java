package exercise.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.Users;
import org.apache.commons.lang3.ArrayUtils;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        String fileName = "src/main/resources/users.json";
        ObjectMapper mapper = new ObjectMapper();

        List<Users> userList = mapper.readValue(new File(fileName), new TypeReference<>() {
        });
        return userList;
        // END
    }

    private void showUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // BEGIN
        PrintWriter out = response.getWriter();
        List<Users> userList = getUsers();
        response.setContentType("text/html;charset=UTF-8");
        String body = "<table>";
        for (Users user : userList) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            body += "<tr><td>" + user.getId() + "</td><td><a href=\"/users/" + user.getId() + "\">" + fullName + "</a></td></tr>";
        }
        body += "</table>";
        out.println(body);
        // END
    }

    private void showUser(HttpServletRequest request, HttpServletResponse response, String id) throws IOException {
        // BEGIN
        PrintWriter out = response.getWriter();
        List<Users> userList = getUsers();
        boolean flag = true;
        for (Users user : userList) {
            if (id.equals(user.getId())) {
                out.println(user);
                flag = false;
                break;
            }
        }
        if(flag) response.sendError(404);
        // END
    }
}
