package com.project11.dragonfly_1;

import javafx.scene.control.Alert;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Stack;

public class Solvers {


    /**
     * Static function to calculate ODE using Euler's method
     * @param x0 from x0 to x
     * @param y0 f(x0) = y0
     * @param x from x0 to x
     * @param stepSize step size (h) for each step
     * @param odeFunction ODE function in string
     * @return integrated value of y from x0 to x
     */
    public static double eulerSolve(double x0, double y0, double x, double stepSize, String odeFunction){

        double h = stepSize;
        double y = y0;

        if (!isBalanced(odeFunction)){
            showAlertBox("Invalid Expression");
        }

        Expression expression = new ExpressionBuilder(odeFunction)
                .variables("x", "y")
                .build();

        while (x0 < x) {

            expression.setVariable("x", x0);
            expression.setVariable("y", y);

            y = y + h * expression.evaluate();
            x0 += h;

        }

        return y;
    }


    /**
     *
     * @param x0 from x0 to x
     * @param y0 f(x0) = y0
     * @param x from x0 to x
     * @param stepSize step size (h) for each step
     * @param function
     * @return
     */
    public static double rungeKuttaSolve(double x0, double y0, double x, double stepSize, String function) {

        double y = y0;
        double h = stepSize;

        if (!isBalanced(function)){
            showAlertBox("Invalid Expression");
        }

        Expression expr = new ExpressionBuilder(function).variables("x", "y").build();

        while (x0 < x) {

            // Compute k1, k2, k3, k4
            double k1 = expr.setVariable("x", x0).setVariable("y", y).evaluate();
            double k2 = expr.setVariable("x", x0 + h / 2).setVariable("y", y + (h / 2) * k1).evaluate();
            double k3 = expr.setVariable("x", x0 + h / 2).setVariable("y", y + (h / 2) * k2).evaluate();
            double k4 = expr.setVariable("x", x0 + h).setVariable("y", y + h * k3).evaluate();

            // RK4 formula
            y = y + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
            x0 += h;
        }

        return y;
    }


    /**
     * checks if the String ODE function has balanced parenthesis
     * @param expression String ODE function
     * @return boolean
     */
    public static boolean isBalanced(String expression) {
        Stack<Character> stack = new Stack<>();

        for (char ch : expression.toCharArray()) {

            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            }

            else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) {
                    return false; // Unmatched closing bracket
                }
                char topElement = stack.pop(); // Get the most recent opening bracket

                if ((ch == ')' && topElement != '(') ||
                        (ch == ']' && topElement != '[') ||
                        (ch == '}' && topElement != '{')) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }


    /**
     * Create quick Alert pop-ups
     * @param message Warning message
     */
    public static void showAlertBox(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

/*
Simple ODEs:	RK4	(High accuracy, easy to implement)
Complex Dynamic Systems:	Adaptive RK - RK45 (Efficiency with automatic step-size control)
Physics-Based Motion (Newton’s Laws):	Verlet Integration (Conserves energy, very stable)
Orbital Mechanics, Astronomy:	Symplectic Integrators (Long-term stability, no energy drift)
Stiff Systems (Chemistry, Circuits):	Implicit Methods (Stable for stiff equations)
Fluid Dynamics, Weather Modeling:	Finite Difference/FEM (Solves PDEs efficiently)

order of error

Euler: erO(h)
RK4: erO(h^4)
 */
