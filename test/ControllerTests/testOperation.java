package ControllerTests;

import Structure.Structure;
import Structure.Symbol;
import static Structure.Symbol.Type.*;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import s188219_jsonparser.InvalidJSONException;
import Controller.Operation;

/**
 *
 * @author S188219
 */
public class testOperation {
    
    public ArrayList<Structure> numberTestArray;
    public ArrayList<Structure> wordTestArray;
    
    public testOperation() {
        
        numberTestArray = new ArrayList<>();
        wordTestArray = new ArrayList<>();
        
        numberTestArray.add(new Symbol(NUMBER, "3"));
        numberTestArray.add(new Symbol(NUMBER, "5"));
        numberTestArray.add(new Symbol(NUMBER, "8"));
        
        wordTestArray.add(new Symbol(STRING, "The "));
        wordTestArray.add(new Symbol(STRING, "number "));
        wordTestArray.add(new Symbol(STRING, "14"));
    }
    
    @Test
    public void testAdd() throws InvalidJSONException{
        Number output = Operation.add(numberTestArray);
        assertEquals(output,16);
    }
    
    @Test
    public void testSubtract() throws InvalidJSONException {
        Number output = Operation.subtract(numberTestArray);      
        assertEquals(output, -10);
    }
    
    @Test
    public void testMultiply() throws InvalidJSONException {
        Number output = Operation.multiply(numberTestArray);
        assertEquals(output, 120);
    }
    
//    @Test
//    public void testDivide() throws InvalidJSONException {
//        Number output = Operation.divide(numberTestArray);
//        
//        System.out.println("Expected result: 3 / 5 / 8 = 0.075");
//        System.out.println("Divide. Result: " + output);
//        
//        assertTrue(output.equals(0.075));
//    }
    
    @Test
    public void testConcatenate() throws InvalidJSONException {
        String output = Operation.concatenate(wordTestArray); 
        
        for(Structure element : wordTestArray) {
            Symbol symbol = (Symbol) element;
            System.out.println("This is a symbol with a type of " + symbol.type + " and a value of " + symbol.value);
        }
        assertTrue(output.equals("The number 14"));
    }
    
    @Test
    public void testMixedAdd0() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(0);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        
        
        try {
            Number output = Operation.add(inputArray);
            fail("Expected NumberFormatException");
        }
        catch(NumberFormatException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testMixedAdd1() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(1);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        
        try {
            Number output = Operation.add(inputArray);
            fail("Expected NumberFormatException");
        }
        catch(NumberFormatException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testMixedAdd2() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(2);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        Number output = Operation.add(inputArray);
        
        assertEquals(output, 17);
    }
    
    @Test
    public void testMixedMultiply0() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(0);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        
        
        try {
            Number output = Operation.multiply(inputArray);
            fail("Expected NumberFormatException");
        }
        catch(NumberFormatException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testMixedMultiply1() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(1);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        
        try {
            Number output = Operation.multiply(inputArray);
            fail("Expected NumberFormatException");
        }
        catch(NumberFormatException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testMixedMultiply2() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(2);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        Number output = Operation.multiply(inputArray);
        
        assertEquals(output, 42);
    }
    
    @Test
    public void testMixedSubtract0() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(0);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        
        
        try {
            Number output = Operation.subtract(inputArray);
            fail("Expected NumberFormatException");
        }
        catch(NumberFormatException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testMixedSubtract1() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(1);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        
        try {
            Number output = Operation.subtract(inputArray);
            fail("Expected NumberFormatException");
        }
        catch(NumberFormatException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testMixedSubtract2() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(2);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        Number output = Operation.subtract(inputArray);
        
        assertEquals(output, -11);
    }
    
    @Test
    public void testMixedConcatenation0() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(0);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);

        String output = Operation.concatenate(inputArray);
        
        assertEquals(output, "3The ");
    }
    
    @Test
    public void testMixedConcatenation1() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(1);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        String output = Operation.concatenate(inputArray);
        
        assertEquals(output, "3number ");
    }
    
    @Test
    public void testMixedConcatenation2() throws InvalidJSONException {
        Symbol firstNumber = (Symbol)numberTestArray.get(0);
        Symbol secondNumber = (Symbol)wordTestArray.get(2);
        
        ArrayList<Structure> inputArray = new ArrayList<>();
        inputArray.add(firstNumber);
        inputArray.add(secondNumber);
        
        String output = Operation.concatenate(inputArray);
        
        assertEquals(output, "314");
    }
    
    @Test
    public void testConcatTrue() throws InvalidJSONException {
        ArrayList<Structure> inputArray = new ArrayList<>();
        
        inputArray.add(new Symbol(STRING, "this is "));
        inputArray.add(new Symbol(BOOLEAN, "true"));
        
        String output = Operation.concatenate(inputArray);
        
        assertEquals(output, "this is true");
    }
    
    @Test
    public void testConcatFalse() throws InvalidJSONException {
        ArrayList<Structure> inputArray = new ArrayList<>();
        
        inputArray.add(new Symbol(STRING, "this is "));
        inputArray.add(new Symbol(BOOLEAN, "false"));
        
        String output = Operation.concatenate(inputArray);
        
        assertEquals(output, "this is false");
    }
    
    @Test
    public void testConcatNull() throws InvalidJSONException {
        ArrayList<Structure> inputArray = new ArrayList<>();
        
        inputArray.add(new Symbol(STRING, "this is "));
        inputArray.add(new Symbol(NULL, "null"));
        
        String output = Operation.concatenate(inputArray);
        
        assertEquals(output, "this is null");
    }
}
