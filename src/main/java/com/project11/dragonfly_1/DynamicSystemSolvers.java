package com.project11.dragonfly_1;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class DynamicSystemSolvers extends Solvers{

    public static int addCounter = 0; // keeps track of #systems
    ArrayList<String> listSolvers = new ArrayList<>(Arrays.asList("Euler", "Runge-Kutta"));

    // System
    String odeFunction;
    List<String> functionList = new ArrayList<>();
    List<ExpressionClass> expressionList = new ArrayList<>();
    HashMap<String, Double> variableStateMap = new HashMap<>(); // ( key = variable , value = currentState )
    HashMap<String, Double> originalResetMap = new HashMap<>();

    // System Graph
    List<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();

    // Inputs
    String solver;
    double t0, t, stepSize;

    // FXML Inputs
    @FXML
    TextField odeInput, freeVariableInput, dependentVariableInput, y0Input, tStart, tEnd, stepField;

    @FXML
    TextArea textArea;

    @FXML
    ComboBox<String> solverBox = new ComboBox<>();

    @FXML
    NumberAxis xAxis = new NumberAxis();

    @FXML
    NumberAxis yAxis = new NumberAxis();

    @FXML
    LineChart<Number, Number> solutionGraph = new LineChart<>(xAxis, yAxis);

    // FXML Initialize
    @FXML
    public void initialize(){

        solverBox.getItems().addAll(listSolvers);
        solutionGraph.setLegendVisible(false);

        // Restrict input length for dependentVariable input
        UnaryOperator<TextFormatter.Change> filter = change ->
                (change.getControlNewText().length() > 1) ? null : change;
        dependentVariableInput.setTextFormatter(new TextFormatter<>(filter));

    }

    // FXML AddSystem Button
    @FXML
    public void onClickAdd(){

        if (addCounter > 10) // check if more than 10 dimension-expression added
            showAlertBox("Cannot add more expressions!");

        if (!isBalanced(odeInput.getText())){ // check if parenthesis balanced
            showAlertBox("Invalid Expression");
        }

        functionList.add(odeInput.getText());
        expressionList.add(new ExpressionClass(odeInput.getText(), extractVariables(freeVariableInput.getText()).toArray(new String[0])));
        expressionList.get(addCounter).setDependentVariable(dependentVariableInput.getText());
        variableStateMap.put(dependentVariableInput.getText(), Double.parseDouble(y0Input.getText()));

        // clear all the input boxes
        odeInput.clear();
        freeVariableInput.clear();
        dependentVariableInput.clear();
        y0Input.clear();

        // add counter increment
        addCounter++;

    }

    // FXML Solve Button
    @FXML
    public void onCLickSolve(){

        // get solver, stepSize, and timeRange
        solver = solverBox.getValue();
        stepSize = Double.parseDouble(stepField.getText());
        t0 = Double.parseDouble(tStart.getText());
        t = Double.parseDouble(tEnd.getText());

        // in case of Reset
        originalResetMap = new HashMap<>(variableStateMap);

        // solve and populate graph
        solveSystem(solver);

    }

    // FXML Clear Button
    @FXML
    public void onClickClear(){

        functionList.clear();
        expressionList.clear();
        solutionGraph.getData().clear();
        variableStateMap.clear();
        addCounter = 0;

        textArea.clear();

    }

    // FXML ResetValue Button
    @FXML
    public void onClickResetValues(){

        variableStateMap = new HashMap<>(originalResetMap);
        for (ExpressionClass exp : expressionList){
            exp.getChartSeries().getData().clear();
        }
        solutionGraph.getData().clear();

        textArea.clear();

        tStart.clear();
        tEnd.clear();
        stepField.clear();

    }

    // OverWrite Solvers for solving Systems (returns HashMap of every variable state after solution)
    public static void eulerSolve(List<ExpressionClass> expressionList, HashMap<String, Double> variableStateMap, double t0, double t, double stepSize){

        BigDecimal Big_t0 = BigDecimal.valueOf(t0);
        BigDecimal Big_t = BigDecimal.valueOf(t);
        BigDecimal Big_step = BigDecimal.valueOf(stepSize);

        while (Big_t0.compareTo(Big_t) < 0){

            // for each step, update all the states
            for (int i = 0; i < expressionList.size(); i++){

                // currently updating dimension
                String dimension = expressionList.get(i).getDependentVariable();

                double y = variableStateMap.get(dimension) + stepSize*expressionList.get(i).getExpression().setVariables(variableStateMap).evaluate();
                //BigDecimal y = BigDecimal.valueOf(variableStateMap.get(dimension)).add(BigDecimal.valueOf(stepSize*expressionList.get(i).getExpression().setVariables(variableStateMap).evaluate()));

                // update hashMap state
                variableStateMap.put(dimension, y);

                // update chartSeries
                XYChart.Data<Number, Number> data = new XYChart.Data<>(t0, y);
                data.setNode(null);
                expressionList.get(i).getChartSeries().getData().add(data);
            }

            Big_t0 = Big_t0.add(Big_step);

        }

    }

    public static void rungeKuttaSolve(List<ExpressionClass> expressionList, HashMap<String, Double> variableStateMap, double t0, double t, double stepSize) {

        BigDecimal Big_t0 = BigDecimal.valueOf(t0);
        BigDecimal Big_t = BigDecimal.valueOf(t);
        BigDecimal Big_step = BigDecimal.valueOf(stepSize);

        while (Big_t0.compareTo(Big_t) < 0){
            // Create temporary storage for k-values
            HashMap<String, Double> k1 = new HashMap<>();
            HashMap<String, Double> k2 = new HashMap<>();
            HashMap<String, Double> k3 = new HashMap<>();
            HashMap<String, Double> k4 = new HashMap<>();

            // Compute k1
            for (ExpressionClass expr : expressionList) {
                String var = expr.getDependentVariable();
                k1.put(var, stepSize * expr.getExpression().setVariables(variableStateMap).evaluate());
            }

            // Compute k2 using variableStateMap updated with k1/2
            for (String var : variableStateMap.keySet()) {
                variableStateMap.put(var, variableStateMap.get(var) + k1.get(var) / 2);
            }
            for (ExpressionClass expr : expressionList) {
                String var = expr.getDependentVariable();
                k2.put(var, stepSize * expr.getExpression().setVariables(variableStateMap).evaluate());
            }

            // Compute k3 using variableStateMap updated with k2/2
            for (String var : variableStateMap.keySet()) {
                variableStateMap.put(var, variableStateMap.get(var) + k2.get(var) / 2);
            }
            for (ExpressionClass expr : expressionList) {
                String var = expr.getDependentVariable();
                k3.put(var, stepSize * expr.getExpression().setVariables(variableStateMap).evaluate());
            }

            // Compute k4 using variableStateMap updated with k3
            for (String var : variableStateMap.keySet()) {
                variableStateMap.put(var, variableStateMap.get(var) + k3.get(var));
            }
            for (ExpressionClass expr : expressionList) {
                String var = expr.getDependentVariable();
                k4.put(var, stepSize * expr.getExpression().setVariables(variableStateMap).evaluate());
            }

            // Update variableStateMap using RK4 formula
            for (String var : variableStateMap.keySet()) {
                double newValue = variableStateMap.get(var) + (k1.get(var) + 2 * k2.get(var) + 2 * k3.get(var) + k4.get(var)) / 6;
                variableStateMap.put(var, newValue);
            }

            // Update chartSeries
            for (ExpressionClass expr : expressionList) {
                String var = expr.getDependentVariable();
                XYChart.Data<Number, Number> data = new XYChart.Data<>(t0, variableStateMap.get(var));
                data.setNode(null);
                expr.getChartSeries().getData().add(data);
            }

            Big_t0 = Big_t0.add(Big_step);
        }
    }




    // Helper Method: Solve and Populate Graph
    public void solveSystem(String solver){

        // BUG TESTING
        for (ExpressionClass exp : expressionList){
            System.out.println(exp.toString());
        }
        System.out.println(variableStateMap);

        // clear graph
        solutionGraph.getData().clear();

        // switch case for solver
        switch(solver){
            case "Euler":
                eulerSolve(expressionList, variableStateMap, t0, t,stepSize);
                break;

            case "Runge-Kutta":
                rungeKuttaSolve(expressionList, variableStateMap, t0, t,stepSize);
                break;
            default:
                System.out.println("dud");
                break;
        }

        // BUG TESTING
        for (ExpressionClass exp : expressionList){
            System.out.println(exp.toString());
        }
        System.out.println(variableStateMap);

        // populate solution graph with all the chartSeries from each expressionClass
        for (ExpressionClass expression: expressionList){
            solutionGraph.getData().add(expression.getChartSeries());
        }
        solutionGraph.setCreateSymbols(false);
        // populate Text Area
        textArea.appendText(variableStateMap.toString());
        System.out.println(variableStateMap);

    }

    // Helper Method: Extract Free Variables
    public static List<String> extractVariables(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)  // Remove spaces
                .collect(Collectors.toList());
    }

}
