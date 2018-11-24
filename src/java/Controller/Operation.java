package Controller;

import Structure.Structure;
import Structure.Symbol;
import static Structure.Symbol.Type.*;
import java.util.ArrayList;
import s188219_jsonparser.InvalidJSONException;
import s188219_jsonparser.Parser;

/**
 *
 * @author S188219
 */
public class Operation {

    //here, param is short for parameters. 
    //I have chosen this name, as I will be iterating through a key-value pair with an array as a value and a key called 'parameters'
    
    
    /**
     * Adds numbers in an array
     * @param params
     * @return
     * @throws InvalidJSONException 
     */
    public static Number add(ArrayList<Structure> params) throws InvalidJSONException {

        Parser p = new Parser();

        Symbol firstNumber = (Symbol) params.get(0);
        Number output = p.parseNumber(firstNumber);

        for (int i = 1; i < params.size(); i++) {
            Symbol paramSymbol = (Symbol) params.get(i);

            switch (paramSymbol.type) {
                case NUMBER: {
                    Number paramNumber = p.parseNumber(paramSymbol);
                    try {
                        output = +(int) output + (int) paramNumber;
                    } catch (NumberFormatException e) {
                        output = +(double) output + (double) paramNumber;
                    }
                    
                    break;
                }
                case STRING: {
                    String stringSymbolValue = paramSymbol.value;
                    Symbol newNumber = new Symbol(NUMBER, stringSymbolValue);
                    
                    Number paramNumber = p.parseNumber(newNumber);
                    

                    try {
                        output = +(int) output + (int) paramNumber;
                    } catch (NumberFormatException e) {
                        output = +(double) output + (double) paramNumber;
                    }
                    
                    break;
                }

                default: {
                    throw new InvalidJSONException("Add method error. Expected NUMBER or STRING. Got " + paramSymbol.type);
                }
            }

        }
        return output;
    }

    /**
     * Subtracts numbers in an array
     * @param params
     * @return
     * @throws InvalidJSONException 
     */
    public static Number subtract(ArrayList<Structure> params) throws InvalidJSONException {

        Parser p = new Parser();

        Symbol firstNumber = (Symbol) params.get(0);
        Number output = p.parseNumber(firstNumber);

        for (int i = 1; i < params.size(); i++) {
            Symbol paramSymbol = (Symbol) params.get(i);

            switch (paramSymbol.type) {
                case NUMBER: {
                    Number paramNumber = p.parseNumber(paramSymbol);
                    try {
                        output = +(int) output - (int) paramNumber;
                    } catch (NumberFormatException e) {
                        output = +(double) output - (double) paramNumber;
                    }
                    break;
                }
                case STRING: {
                    String stringSymbolValue = paramSymbol.value;
                    Symbol newNumber = new Symbol(NUMBER, stringSymbolValue);
                    Number paramNumber = p.parseNumber(newNumber);

                    try {
                        output = +(int) output - (int) paramNumber;
                    } catch (NumberFormatException e) {
                        output = +(double) output - (double) paramNumber;
                    }
                    break;
                }

                default: {
                    throw new InvalidJSONException("Subtract method error. Expected NUMBER or STRING. Got " + paramSymbol.type);
                }
            }
        }
        return output;
    }

    /**
     * Multiplies numbers in an array
     * @param params
     * @return
     * @throws InvalidJSONException 
     */
    public static Number multiply(ArrayList<Structure> params) throws InvalidJSONException {

        Parser p = new Parser();

        Symbol firstNumber = (Symbol) params.get(0);
        Number output = p.parseNumber(firstNumber);

        for (int i = 1; i < params.size(); i++) {
            Symbol paramSymbol = (Symbol) params.get(i);

            switch (paramSymbol.type) {
                case NUMBER: {
                    Number paramNumber = p.parseNumber(paramSymbol);
                    try {
                        output = +(int) output * (int) paramNumber;
                    } catch (NumberFormatException e) {
                        output = +(double) output * (double) paramNumber;
                    }
                    
                    break;
                }
                case STRING: {
                    String stringSymbolValue = paramSymbol.value;
                    Symbol newNumber = new Symbol(NUMBER, stringSymbolValue);
                    Number paramNumber = p.parseNumber(newNumber);

                    try {
                        output = +(int) output * (int) paramNumber;
                    } catch (NumberFormatException e) {
                        output = +(double) output * (double) paramNumber;
                    }
                    
                    break;
                }

                default: {
                    throw new InvalidJSONException("Multiply method error. Expected NUMBER or STRING. Got " + paramSymbol.type);
                }
            }
        }
        return output;
    }

    /**
     * Divides numbers in an array
     * @param params
     * @return
     * @throws InvalidJSONException 
     */
    public static Number divide(ArrayList<Structure> params) throws InvalidJSONException {

        Parser p = new Parser();

        Symbol firstNumber = (Symbol) params.get(0);
        Number output = p.parseNumber(firstNumber);

        for (int i = 1; i < params.size(); i++) {
            Symbol paramSymbol = (Symbol) params.get(i);

            switch (paramSymbol.type) {
                case NUMBER: {
                    Number paramNumber = p.parseNumber(paramSymbol);
                    try {
                        output = +(int) output + (int) paramNumber;
                    } catch (NumberFormatException e) {
                        output = +(double) output + (double) paramNumber;
                    }
                    
                    break;
                }
                case STRING: {
                    String stringSymbolValue = paramSymbol.value;
                    Symbol newNumber = new Symbol(NUMBER, stringSymbolValue);
                    Number paramNumber = p.parseNumber(newNumber);

                    try {
                        output = +(int) output + (int) paramNumber;
                    } catch (NumberFormatException e) {
                        output = +(double) output + (double) paramNumber;
                    }
                    
                    break;
                }

                default: {
                    throw new InvalidJSONException("Divide method error. Expected NUMBER or STRING. Got " + paramSymbol.type);
                }
            }
        }
        return output;
    }

    /**
     * Concatenates elements in an array together
     * @param params
     * @return
     * @throws InvalidJSONException 
     */
    public static String concatenate(ArrayList<Structure> params) throws InvalidJSONException {
        StringBuilder resultSb = new StringBuilder();
        Parser p = new Parser();

        for (Structure param : params) {
            Symbol paramSymbol = (Symbol) param;

            switch (paramSymbol.type) {
                case STRING: {
                    String paramString = p.parseString(paramSymbol);
                    resultSb.append(paramString);
                    break;
                }
                default: {
                    String symbolValue = paramSymbol.value;
                    Symbol newSymbol = new Symbol(STRING, symbolValue);
                    String paramString = p.parseString(newSymbol);
                    resultSb.append(paramString);
                    break;
                }
            }
            
        }

        String result = resultSb.toString();

        return result;
    }
}
