package util;

import java.util.Stack;

/**
 * The main part of the calculator doing the calculations.
 * 
 * @author Alan Marsh
 */
public class CalcEngine
{
    char operator, prevOperator;
    int displayValue, operand1;
    private static Boolean DEBUG = true;
    Stack<String> operands; 
    Stack<Character> operators;

    /**
     * Create a CalcEngine instance. Initialise its state so that it is ready 
     * for use.
     */
    public CalcEngine()
    {
    	operands = new Stack<String>();
    	if (DEBUG)System.out.println("/t** Create stack 'operands'");
    	operators = new Stack<Character>();
    	if (DEBUG)System.out.println("/t** Create stack 'operators");
        operator =' ';
        displayValue=0;
	}

    /**
     * Return the value that should currently be displayed on the calculator
     * display.
     */
    public int getDisplayValue()
    {
        return(displayValue);
    }

    /**
     * A number button was pressed. Do whatever you have to do to handle it.
     * The number value of the button is given as a parameter.
     */
    public void numberPressed(int number)
    {
        displayValue = displayValue *10 + number;
    }

    /**
     * The 'plus' button was pressed. 
     */
    public void plus()
    {
       operand1 = displayValue;
       displayValue = 0;
       operator = '+';
    }
    
    /**
     * The 'minus' button was pressed.
     */
    public void minus()
    {
        operand1 = displayValue;
	   displayValue = 0;
       operator = '-'; 
    }

    public void multiply()
    {
        operand1 = displayValue;
	   displayValue = 0;
       operator = '*'; 
    }

    public void divide(int operand1, int operand2)
    {
       operands.push(String.valueOf(operand1/operand2));
     }

    /**
     * The '=' button was pressed.
     */
    public void equals()
    {
        if (operator == '+') {
			displayValue += operand1;
			operand1 = 0;
		}
	    else if (operator == '-') {
			displayValue = operand1-displayValue;
			operand1 = 0;
		}
		else if (operator == '*') {
			displayValue = operand1*displayValue;
			operand1 = 0;
		}
		else if (operator == '/') {
			displayValue = operand1/displayValue;
			operand1 = 0;
		}

    }
    
    public void operator(char setOperator){
    	if(setOperator != '('){
    		if (prevOperator != ')'){
    			if (DEBUG)System.out.println("\t** Push " + displayValue + " to 'operands' stack");
    			operands.push(String.valueOf(displayValue));
    			displayValue = 0;
    		}
    	
    	
	 	   	if(!operators.isEmpty()){
		 	   	if(setOperator == '='){
		 	   	if (DEBUG)System.out.println("\t** operator '='");
		 	   		while(!operators.isEmpty()){
		 	   			performCalculation(operators.pop());
		 	   		if (DEBUG)System.out.println("\t** pop operator from 'operators' stack and call calc method");
		 	   		}
		 	   		displayValue = Integer.valueOf(operands.pop());
		 	   		if (DEBUG)System.out.println("\t** pop from 'operands' stack and display");
		 	   	} 	   	
		 	   	else if(setOperator == ')'){
		 	   		while(operators.peek() != '('){
		 	   			performCalculation(operators.pop());
		 	   			if (DEBUG)System.out.println("\t** pop operator from 'operators' stack and call calc method");
		 	   		}
		 	   		operators.pop();
		 	   		if (DEBUG)System.out.println("\t** pop '(' fom operands");
		 	   	 }  	
		 	   	  	
		 	   	else if(setPrecedence(operators.peek()) == 2){
		 	   		performCalculation(operators.pop());
		 	   		if (DEBUG)System.out.println("\t** pop operator from 'operators' stack and call calc method");
		 	   	}
	 	   	}
    	}
 	   	
 	   	if(!(setOperator == '=' || setOperator == ')')){
 	   		operators.push(setOperator);
 	   		if (DEBUG)System.out.println("\t** Push " + setOperator + " to 'operators' stack");
 	   	}
 	   	
 	   	prevOperator = setOperator;
    }
    
    public boolean checkPrecedence(char setOperator){
    	if(setPrecedence(setOperator) > setPrecedence(operators.peek()))
    		return true;
    	
    	return false;
    }
    
    public int setPrecedence(char operator){
    	if(operator == '+' || operator == '+')
    		return 1;
    	else if(operator == '*' || operator == '/')
    		return 2;
    	
		return 0;
		
    }
    
    public void performCalculation(char operator){
    	if (DEBUG)System.out.println("\t** pop operand "+ operands.peek() + " from 'operands' stack and set to operand2");
    	int operand2 = Integer.valueOf(operands.pop());
    	if (DEBUG)System.out.println("\t** pop operand "+ operands.peek() + " from 'operands' stack and set to operand1");
    	int operand1 = Integer.valueOf(operands.pop());
    	
    	switch(operator){
    	case '*':
    		operands.push(String.valueOf(operand1*operand2));
    		if (DEBUG)System.out.println("\t** push operand1*operand2 " + (operand1*operand2) + " to operands stack");
    		break;
    	case '/':
    		operands.push(String.valueOf(operand1/operand2));
    		if (DEBUG)System.out.println("\t** push operand1*operand2 " + (operand1/operand2) + " to operands stack");
    		break;
    	case '+':
    		operands.push(String.valueOf(operand1+operand2));
    		if (DEBUG)System.out.println("\t** push operand1*operand2 " + (operand1+operand2) + " to operands stack");
    		break;
    	case '-':
    		operands.push(String.valueOf(operand1-operand2));
    		if (DEBUG)System.out.println("\t** push operand1-operand2 " + (operand1-operand2) + " to operands stack");
    		break;    		
    	}
    	
    }

    /**
     * The 'C' (clear) button was pressed.
     */
    public void clear()
    {
        displayValue = 0;
		operand1 = 0;

    }

    /**
     * Return the title of this calculation engine.
     */
    public String getTitle()
    {
        return("My Calculator");
    }

    /**
     * Return the author of this engine. This string is displayed as it is,
     * so it should say something like "Written by H. Simpson".
     */
    public String getAuthor()
    {
        return("Alan Marsh");
    }

    /**
     * Return the version number of this engine. This string is displayed as 
     * it is, so it should say something like "Version 1.1".
     */
    public String getVersion()
    {
        return("Ver. 1.3");
    }

}