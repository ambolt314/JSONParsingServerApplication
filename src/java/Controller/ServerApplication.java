package Controller;

import static Lexical_Analyser.S188219_Lexer.lexer;
import Structure.JSONArray;
import Structure.JSONObject;
import Structure.Structure;
import Structure.Symbol;
import static Structure.Symbol.Type.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import s188219_jsonparser.InvalidJSONException;
import s188219_jsonparser.Parser;
import static Controller.HTTP.*;
import static Controller.Operation.*;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.util.Arrays;
import javax.servlet.ServletException;

/**
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:</p>
 *
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.</p>
 *
 * <p>
 * The Software shall be used for Good, not Evil.</p>
 *
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE. </p>
 *
 * @author s188219
 */
@WebServlet("/serverapp")
public class ServerApplication extends HttpServlet {

    public static String baseURL = "http://i2j.openode.io";
    public static Parser jsonParser = new Parser();
    public static String result;
    public static String studentID;

    private static StringBuilder fileBuilder; //used across all methods of this class

    public ServerApplication() {
        fileBuilder = new StringBuilder();
    }

    /**
     * This method takes an input from a student id and creates a file
     *
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("You have entered the function");

        studentID = request.getParameter("student_id");
        //valid student id format
        Pattern idPattern = Pattern.compile("(S|s)(188)[0-9]{3}");
        Matcher idMatcher = idPattern.matcher(studentID);
        boolean idIsValid = idMatcher.matches();

        PrintWriter writer = response.getWriter();

        //checks that student id is valid
        if (idIsValid) {
            try {
                String initialURL = baseURL + "/student?id=" + studentID;
                System.out.println("Initial URL: " + initialURL);

                String outputFromInitialURL = sendGET(initialURL);
                System.out.println("Initial GET request sent. Output: " + outputFromInitialURL);

                //converts output into a set of symbols. Used to extract array
                ArrayList<Symbol> lexedInitialOutput = lexer(outputFromInitialURL);
                JSONObject initialObject = jsonParser.parseObject(lexedInitialOutput);
                JSONArray tasksArray = (JSONArray) initialObject.getValueFromKey("tasks");

                taskURLIterator(tasksArray);

                String validResponse = createResponseString("<div class=\"alert alert-info\" role=\"alert\"> Your file has been created. </div>");
                writer.println(validResponse);

                String fileContents = fileBuilder.toString();

                File newFile = TxtFileWriter.createTxtFile(fileContents);

                
                //opens newly created file
                Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler",
                    newFile.getAbsolutePath()});

                Desktop desktop = Desktop.getDesktop();
                if (newFile.exists()) {
                    desktop.open(newFile);
                }

            } catch (InvalidJSONException | HeadlessException e) {
                StackTraceElement[] error = e.getStackTrace();
                String message = Arrays.toString(error);
                System.err.println(message);
            }

        } else {
            String invalidResponse = createResponseString("<div class=\"alert alert-danger\" role=\"alert\">Please enter a valid student id</div>");
            writer.println(invalidResponse);

        }

    }

    /**
     * Iterates through a parsed array and completes operation
     * 
     * @param inputArray task array to be iterated over
     * @throws InvalidJSONException
     * @throws IOException 
     */
    public static void taskURLIterator(JSONArray inputArray) throws InvalidJSONException, IOException {
        ArrayList<Structure> elements = inputArray.getContents();

        //an array of URLs is expected, so iterate through it
        for (Structure element : elements) {
            Symbol urlSymbol = (Symbol) element;

            if (urlSymbol.type == STRING) {
                String urlSymbolValue = urlSymbol.value;

                String taskURL = baseURL + urlSymbolValue;
                fileBuilder.append("Task url: " + taskURL + System.getProperty("line.separator"));

                try {

                    String answerURL = taskOperation(taskURL);
                    

                    System.out.println("Sending post...");
                    int responseCodeFromSendingAnswer = sendPOST(result, answerURL);
                    fileBuilder.append("Response code: " + responseCodeFromSendingAnswer + System.getProperty("line.separator"));
                    fileBuilder.append(System.getProperty("line.separator"));
                } //sends a message back to server for an invalid task
                catch (InvalidJSONException | UnknownHostException e) {
                    sendPOST("Error: " + e.getMessage(), taskURL);
                    fileBuilder.append("Error: " + e.getMessage());

                    fileBuilder.append(System.getProperty("line.separator"));
                    fileBuilder.append(System.getProperty("line.separator"));
                    continue;
                }

            } else {
                String invalidTask = "Invalid task: Expected URL as a STRING. Got " + urlSymbol.type;
                fileBuilder.append(invalidTask);

            }

        }
    }

    /**
     * Performs task depending on instruction
     * 
     * @param taskURL
     * @return
     * @throws IOException
     * @throws InvalidJSONException 
     */
    public static String taskOperation(String taskURL) throws IOException, InvalidJSONException {

        String outputFromTaskURL = sendGET(taskURL);

        fileBuilder.append("Task output: " + outputFromTaskURL + System.getProperty("line.separator"));

        ArrayList<Symbol> lexedTaskOutput = lexer(outputFromTaskURL);

        JSONObject taskObject = jsonParser.parseObject(lexedTaskOutput);

        //expects three key-value pairs
        Symbol instructionValue = (Symbol) taskObject.getValueFromKey("instruction");

        JSONArray valueFromParametersKey = (JSONArray) taskObject.getValueFromKey("parameters");
        ArrayList<Structure> parameters = valueFromParametersKey.getContents();

        Symbol responseURL = (Symbol) taskObject.getValueFromKey("response URL");

        switch (instructionValue.value) {
            case "add": {
                Number addResult = add(parameters);
                result = String.valueOf(addResult);
                fileBuilder.append("Result: " + result + System.getProperty("line.separator"));

                break;
            }
            case "multiply": {
                Number multiplyResult = multiply(parameters);
                result = String.valueOf(multiplyResult);
                fileBuilder.append("Result: " + result + System.getProperty("line.separator"));
                break;
            }
            case "concat": {
                result = concatenate(parameters);
                fileBuilder.append("Result: " + result + System.getProperty("line.separator"));
                break;
            }
            case "subtract": {
                Number subtractResult = subtract(parameters);
                result = String.valueOf(subtractResult);
                fileBuilder.append("Result: " + result + System.getProperty("line.separator"));
                break;
            }
            case "divide": {
                Number divideResult = divide(parameters);
                result = String.valueOf(divideResult);
                fileBuilder.append("Result: " + result + System.getProperty("line.separator"));
                break;
            }
        }

        String answerURL = baseURL + responseURL.value;
        return answerURL;
    }

    /**
     * Creates an HTML response. Used for Bootstrap
     * 
     * @param htmlAlertTag
     * @return HTML page
     */
    public static String createResponseString(String htmlAlertTag) {
        return "<html>\n"
                + "    <head>\n"
                + "\n"
                + "        <title>S188219 - JSON Parser Server Application</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "\n"
                + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n"
                + "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n"
                + "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n"
                + "\n"
                + "        <style>\n"
                + "            body {\n"
                + "                background-color: rgb(244, 244, 244);\n"
                + "            }\n"
                + "        </style>\n"
                + "\n"
                + "    </head>\n"
                + "    <body>\n"
                + "\n"
                + "\n"
                + "\n"
                + "        <div class=\"container\">\n"
                + "\n"
                + "            <!--Application can either: validate JSON or process results from a server -->\n"
                + "            <ul class=\"nav nav-tabs\">\n"
                + "                <li><a data-toggle=\"tab\" href=\"#home\">S188219 - JSON Parser Server Application</a></li>\n"
                + "                <li><a data-toggle=\"tab\" href=\"#menu1\">JSON Validator</a></li>\n"
                + "                <li class=\"active\"><a data-toggle=\"tab\" href=\"#menu2\">Server Parser</a></li>\n"
                + "\n"
                + "            </ul>\n"
                + "\n"
                + "            <div class=\"tab-content\">\n"
                + "\n"
                + "\n"
                + "                <div id=\"home\" class=\"tab-pane fade\">\n"
                + "\n"
                + "                    <div class=\"container\">\n"
                + "                        <h2>S188219 - JSON Parser Server Application</h2>\n"
                + "\n"
                + "                        <p>This application is intended to handle all things JSON. \n"
                + "                            The first tab allows users to input a string and determine whether that string is valid. \n"
                + "                            The second allows students to complete their respective tasks on the assignment server</p>\n"
                + "                        \n"
                + "                        <strong>S188219</strong>\n"
                + "\n"
                + "\n"
                + "                    </div>\n"
                + "\n"
                + "\n"
                + "                </div>\n"
                + "\n"
                + "\n"
                + "\n"
                + "                <div id=\"menu1\" class=\"tab-pane fade\">\n"
                + "                    <h3>JSON Validator</h3>\n"
                + "                    <p>This tool takes an inputted string and determines whether it is valid JSON or not.</p>\n"
                + "                    <form method=\"post\" action=\"jsonvalidator\">\n"
                + "                        <div class=\"form-group\">\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "                            <label for=\"comment\">Input text:</label>\n"
                + "                            <textarea class=\"form-control\" rows=\"5\" id=\"comment\" name=\"input_json\"></textarea>\n"
                + "                        </div>\n"
                + "\n"
                + "                        <button type=\"submit\" class=\"btn btn-default\">Parse</button>\n"
                + "                        \n"
                + "                        \n"
                + "                    </form>\n"
                + "\n"
                + "\n"
                + "                </div>\n"
                + "\n"
                + "                <div id=\"menu2\" class=\"tab-pane fade in active\">\n"
                + "                    <h3>Server Parser</h3>\n"
                + "                    <p>This tool queries an assignment server and complete the tasks on that server.</p>\n"
                + "                    <form method=\"post\" action=\"serverapp\">\n"
                + "                        <div class=\"form-group\">\n"
                + "                            <label>Student id:</label>\n"
                + "                            <input type=\"text\" class=\"form-control\" name=\"student_id\" placeholder=\"Enter student id\" name=\"email\">\n"
                + htmlAlertTag
                + "                        <button style=\"font-size:24px\">Generate File</button>\n"
                + "                        \n"
                + "                    </form>\n"
                + "\n"
                + "                </div>\n"
                + "\n"
                + "            </div>\n"
                + "        </div>\n"
                + "\n"
                + "\n"
                + "    </body>\n"
                + "</html>";
    }

}
