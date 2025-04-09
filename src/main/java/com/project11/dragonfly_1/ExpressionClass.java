package com.project11.dragonfly_1;

import javafx.scene.chart.XYChart;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;

public class ExpressionClass {

    String dependentVariable, ode;
    XYChart.Series<Number, Number> chartSeries;
    Expression expression;
    List<String> freeVariables = new ArrayList<>();

    public ExpressionClass(String odeFunction, String... freeVariables) {

        ode = odeFunction;

        if (freeVariables.length < 1 || freeVariables.length > 10) {
            throw new IllegalArgumentException("Number of free variables must be between 1 and 10.");
        }

        this.freeVariables.addAll(List.of(freeVariables));
        expression = new ExpressionBuilder(odeFunction).variables(freeVariables).build();

        chartSeries = new XYChart.Series<>();
    }

    // GETTER & SETTER Methods

    public List<String> getFreeVariables() {
        return freeVariables;
    }

    public Expression getExpression() {
        return expression;
    }

    public XYChart.Series<Number, Number> getChartSeries() {
        return chartSeries;
    }

    public String getDependentVariable() {
        return dependentVariable;
    }

    public void setDependentVariable(String dependentVariable) {
        this.dependentVariable = dependentVariable;
    }

    // toString
    @Override
    public String toString(){
        String expToString;
        expToString = String.format("{ODE: %s'(%s) = %s}", dependentVariable, freeVariables, ode);
        return expToString;
    }
}
