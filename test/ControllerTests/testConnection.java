package ControllerTests;


import static Lexical_Analyser.S188219_Lexer.lexer;
import Structure.JSONArray;
import Structure.JSONObject;
import Structure.Structure;
import Structure.Symbol;
import static Structure.Symbol.Type.NUMBER;
import static Structure.Symbol.Type.STRING;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import s188219_jsonparser.InvalidJSONException;
import s188219_jsonparser.Parser;
import static Controller.HTTP.sendGET;
import static Controller.HTTP.sendPOST;
import Controller.ServerApplication;

/**
 * These classes are intended to test all connections which need to be made
 *
 * @author S188219
 */
public class testConnection {

    /**
     * validTasks - an array which is validated against
     */
    ArrayList<Structure> validTasks;

    /**
     * Initialises contents of validTasks array
     */
    public testConnection() {

        validTasks = new ArrayList<>();

        validTasks.add(new Symbol(STRING, "/task/4017"));
        validTasks.add(new Symbol(STRING, "/task/3597"));
        validTasks.add(new Symbol(STRING, "/task/2024"));
        validTasks.add(new Symbol(STRING, "/task/7365"));
        validTasks.add(new Symbol(STRING, "/task/1287"));
        validTasks.add(new Symbol(STRING, "/task/5125"));
        validTasks.add(new Symbol(STRING, "/task/6754"));
        validTasks.add(new Symbol(STRING, "/task/293"));
        validTasks.add(new Symbol(STRING, "/task/4508"));
        validTasks.add(new Symbol(STRING, "/task/855"));
    }

    @Test
    public void testInitialConnection() throws IOException {
        String serverURL = "http://i2j.openode.io/student?id=s188219";

        //System.out.println(serverURL);
        String response = sendGET(serverURL);

        //System.out.println("URL:" + serverURL);
        //System.out.println("Response: " + response);
        assertTrue(response != null);

    }

    @Test
    public void testFirstTaskConnection() throws IOException {
        String serverURL = "http://i2j.openode.io/task/4017";

//        System.out.println(serverURL);
        String response = sendGET(serverURL);

//        System.out.println("URL:" + serverURL);
//        
//        System.out.println("Response: " + response);
        assertTrue(response != null);

    }

    /**
     * stub - verifies whether tasks can be fetched from the server
     *
     * @throws IOException
     * @throws InvalidJSONException
     */
    @Test
    public void stubIDFromServer() throws IOException, InvalidJSONException {
        String baseURL = "http://i2j.openode.io/";
        String studentId = "s188219";

        String initialURL = baseURL + "student?id=" + studentId;
//        System.out.println(initialURL);

        String response = sendGET(initialURL);
        ArrayList<Symbol> lexerOutput = lexer(response);

        Parser p = new Parser();
        JSONObject initialOutput = p.parseObject(lexerOutput);
        Symbol id = (Symbol) initialOutput.getValueFromKey("id");
        String idValue = id.value;

        assertTrue(idValue.equals("s188219"));

    }

    /**
     * stub - verifies that tasks array can be accessed
     *
     * @throws IOException
     * @throws InvalidJSONException
     */
    @Test
    public void stubRetrieveTasksFromServer() throws IOException, InvalidJSONException {
        String baseURL = "http://i2j.openode.io/";
        String studentId = "s188219";

        String initialURL = baseURL + "student?id=" + studentId;
        //System.out.println(initialURL);

        String response = sendGET(initialURL);
        ArrayList<Symbol> lexerOutput = lexer(response);

        //System.out.println("subRetrieveTasksFromServer(): " + response);
        Parser p = new Parser();
        JSONObject taskOutput = p.parseObject(lexerOutput);
        JSONArray taskArray = (JSONArray) taskOutput.getValueFromKey("tasks");
        ArrayList<Structure> tasks = taskArray.getContents();

        for (int i = 0; i < tasks.size(); i++) {
            Symbol task = (Symbol) tasks.get(i);
            Symbol.Type taskType = task.type;
            String taskValue = task.value;

            Symbol valid = (Symbol) validTasks.get(i);
            Symbol.Type validType = valid.type;
            String validValue = valid.value;

            assertTrue(taskType.equals(validType));
            assertTrue(taskValue.equals(validValue));

        }

    }

    /**
     * stub - checks that "instruction" key can be read from the first task
     *
     * @throws IOException
     * @throws InvalidJSONException
     */
    @Test
    public void stubReadInstructionFromFirstTask() throws IOException, InvalidJSONException {
        String baseURL = "http://i2j.openode.io/";
        String taskId = "4017";

        String taskURL = baseURL + "task/" + taskId;

        String response = sendGET(taskURL);
        ArrayList<Symbol> lexerOutput = lexer(response);

//        System.out.println(response);
        Parser p = new Parser();
        JSONObject taskOutput = p.parseObject(lexerOutput);
        Symbol addSymbol = (Symbol) taskOutput.getValueFromKey("instruction");

        assertEquals(addSymbol.type, STRING);
        assertEquals(addSymbol.value, "add");

    }

    /**
     * stub - checks that "parameters" key can be read from the first task
     *
     * @throws IOException
     * @throws InvalidJSONException
     */
    @Test
    public void stubReadParametersFromFirstTask() throws IOException, InvalidJSONException {
        String baseURL = "http://i2j.openode.io/";
        String taskId = "4017";

        String taskURL = baseURL + "task/" + taskId;

        String response = sendGET(taskURL);
        ArrayList<Symbol> lexerOutput = lexer(response);

//        System.out.println(response);
        Parser p = new Parser();
        JSONObject taskOutput = p.parseObject(lexerOutput);
        JSONArray parametersArray = (JSONArray) taskOutput.getValueFromKey("parameters");
        ArrayList<Structure> parameters = parametersArray.getContents();

        Symbol firstSymbol = (Symbol) parameters.get(0);
        Symbol secondSymbol = (Symbol) parameters.get(1);

        Symbol.Type firstSymbolType = firstSymbol.type;
        Symbol.Type secondSymbolType = secondSymbol.type;

        String firstSymbolValue = firstSymbol.value;
        String secondSymbolValue = secondSymbol.value;

        assertEquals(firstSymbolType, NUMBER);
        assertEquals(secondSymbolType, NUMBER);

        assertEquals(firstSymbolValue, "6156");
        assertEquals(secondSymbolValue, "520");

    }

    /**
     * stub - checks that the "response URL" key can be read from the first task
     *
     * @throws IOException
     * @throws InvalidJSONException
     */
    @Test
    public void stubReadResponseURLFromFirstTask() throws IOException, InvalidJSONException {
        String baseURL = "http://i2j.openode.io/";
        String taskId = "4017";

        String taskURL = baseURL + "task/" + taskId;

        String response = sendGET(taskURL);
        ArrayList<Symbol> lexerOutput = lexer(response);

//        System.out.println(response);
        Parser p = new Parser();
        JSONObject taskOutput = p.parseObject(lexerOutput);
        Symbol responseURL = (Symbol) taskOutput.getValueFromKey("response URL");

        Symbol.Type responseURLType = responseURL.type;
        String responseURLValue = responseURL.value;

        assertEquals(responseURLType, STRING);
        assertEquals(responseURLValue, "/answer/4017");
    }

    /**
     * Checks that the response URL exists
     *
     * @throws IOException
     */
//    @Test
//    public void testCheckResponseURLExists() throws IOException {
//        String baseURL = "http://i2j.openode.io/";
//        String taskId = "4017";
//
//        String answerURL = baseURL + "answer/" + taskId;
//
//        int responseCode = sendPOST("", answerURL);
//
//        assertTrue(responseCode == HttpURLConnection.HTTP_OK);
//
//    }

    /**
     * Tests completeTasks part of application
     * 
     * @throws InvalidJSONException
     * @throws IOException 
     */
    @Test
    public void stubTaskIterator() throws InvalidJSONException, IOException {

        JSONArray tasksArray = new JSONArray(validTasks);
        ServerApplication.taskURLIterator(tasksArray);
    }
    
    @Test
    public void stubTaskOperation() throws IOException, InvalidJSONException {
        
        Symbol firstTask = (Symbol)validTasks.get(0);
        
        String firstTaskURL = firstTask.value;
        
        ServerApplication.taskOperation(firstTaskURL);
        
        
    }

}
