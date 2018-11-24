/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.JSONValidator.Validity.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import s188219_jsonparser.InvalidJSONException;
import s188219_jsonparser.Parser;

/**
 *
 * @author 611213417
 */
@WebServlet("/jsonvalidator")
public class JSONValidator extends HttpServlet {
    
    protected String message;
    protected String input;
    
    public enum Validity {
      VALID,
      INVALID,
      EMPTY
    };
    
    
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Entered validator function");

        //input_text
        input = request.getParameter("input_json");
        
        if(request == null){
            createHtmlResponse(EMPTY);
        }

        String htmlResponse = null;

        Parser p = new Parser();
        message = p.getMessage(input);

        switch (message) {
            case "Valid JSON": {
                htmlResponse = createHtmlResponse(VALID);
                break;
            }
            
            default: {
                try {
                htmlResponse = createHtmlResponse(INVALID);
                break;
                }
                catch (NullPointerException e) {
                    htmlResponse = createHtmlResponse(EMPTY);
                }
            
            }
            
        }

        PrintWriter writer = response.getWriter();
        writer.println(htmlResponse);

    }
    
    protected String createHtmlResponse(Validity validOrInvalid) {
        String alertType = null;
        
        switch(validOrInvalid) {
            case VALID: {
                alertType = "<div class=\"alert alert-success\" role=\"alert\">" + message + "</div>\n";
                break;
            }
            case INVALID: {
                alertType = "<div class=\"alert alert-danger\" role=\"alert\">" + message + "</div>\n";
                break;
            }
            case EMPTY: {
                alertType = "<div class=\"alert alert-danger\" role=\"alert\">" + message + "</div>\n";
            }
        }
        return "<html>\n" +
"    <head>\n" +
"\n" +
"        <title>S188219 - JSON Parser Server Application</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"\n" +
"        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
"        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
"        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
"\n" +
"        <style>\n" +
"            body {\n" +
"                background-color: rgb(244, 244, 244);\n" +
"            }\n" +
"        </style>\n" +
"\n" +
"    </head>\n" +
"    <body>\n" +
"\n" +
"\n" +
"\n" +
"        <div class=\"container\">\n" +
"\n" +
"            <!--Application can either: validate JSON or process results from a server -->\n" +
"            <ul class=\"nav nav-tabs\">\n" +
"                <li><a data-toggle=\"tab\" href=\"#home\">S188219 - JSON Parser Server Application</a></li>\n" +
"                <li class=\"active\"><a data-toggle=\"tab\" href=\"#menu1\">JSON Validator</a></li>\n" +
"                <li><a data-toggle=\"tab\" href=\"#menu2\">Server Parser</a></li>\n" +
"\n" +
"            </ul>\n" +
"\n" +
"            <div class=\"tab-content\">\n" +
"\n" +
"\n" +
"                <div id=\"home\" class=\"tab-pane fade\">\n" +
"\n" +
"                    <div class=\"container\">\n" +
"                        <h2>S188219 - JSON Parser Server Application</h2>\n" +
"\n" +
"                        <p>This application is intended to handle all things JSON. \n" +
"                            The first tab allows users to input a string and determine whether that string is valid. \n" +
"                            The second allows students to complete their respective tasks on the assignment server</p>\n" +
"                        \n" +
"                        <strong>S188219</strong>\n" +
"\n" +
"\n" +
"                    </div>\n" +
"\n" +
"\n" +
"                </div>\n" +
"\n" +
"\n" +
"\n" +
"                <div id=\"menu1\" class=\"tab-pane fade in active\">\n" +
"                    <h3>JSON Validator</h3>\n" +
"                    <p>This tool takes an inputted string and determines whether it is valid JSON or not.</p>\n" +
"                    <form method=\"post\" action=\"jsonvalidator\">\n" +
"                        <div class=\"form-group\">\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                            <label for=\"comment\">Input text:</label>\n" +
"                            <textarea class=\"form-control\" rows=\"5\" id=\"comment\" name=\"input_json\">"+ input +"</textarea>\n" +
"                        </div>\n" +
"\n" +
"                        <button type=\"submit\" class=\"btn btn-default\">Parse</button>\n" +
"                        \n" +
alertType+
"                    </form>\n" +
"\n" +
"\n" +
"                </div>\n" +
"\n" +
"                <div id=\"menu2\" class=\"tab-pane fade\">\n" +
"                    <h3>Server Parser</h3>\n" +
"                    <p>This tool queries an assignment server and complete the tasks on that server.</p>\n" +
"                    <form method=\"post\" action=\"serverapp\">\n" +
"                        <div class=\"form-group\">\n" +
"                            <label>Student id:</label>\n" +
"                            <input type=\"text\" class=\"form-control\" id=\"student_id\" placeholder=\"Enter student id\" name=\"email\">\n" +
"                        </div>\n" +
"\n" +
"                        <button style=\"font-size:24px\"><a href=\"taskOutput.txt\">Generate File</a> </button>\n" +
"                        \n" +
"                    </form>\n" +
"\n" +
"                </div>\n" +
"\n" +
"            </div>\n" +
"        </div>\n" +
"\n" +
"\n" +
"    </body>\n" +
"</html>";
    }
}
