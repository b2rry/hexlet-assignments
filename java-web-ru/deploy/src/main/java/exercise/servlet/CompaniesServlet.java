package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    private List<String> companiesList = new ArrayList<>();
    private String valueOfSearchParam = null;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // BEGIN
        companiesList = getCompanies();
        PrintWriter out = response.getWriter();
        valueOfSearchParam = request.getParameter("search");

        if(valueOfSearchParam == null || valueOfSearchParam.equals("")){
            for(String buf : companiesList){
                out.println(buf);
            }
        }else{
            List<String> containParamStrings = new ArrayList<>();
            for(String buf : companiesList){
                if(buf.contains(valueOfSearchParam)) {
                    containParamStrings.add(buf);
                }
            }
            if(containParamStrings.isEmpty()){
                out.println("Companies not found");
            }else{
                for(String buf : containParamStrings){
                    out.println(buf);
                }
            }
        }
        // END
    }
}
