package com.project11.dragonfly_1;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Accuracy {

    double Y0Exact = 100.0;
    double YExact = 74.08182206817179;

    public double solve(double intialValue, double decayRate, double time){

        double initialVal = intialValue;
        double k = decayRate;
        double t = time;

        double y = initialVal * Math.exp(-k*t);

        return y;
    }

    public double absErr(double exactValue, double yDash){

        return Math.abs(exactValue - yDash);

    }

    public double getYDash(String solver, double time, double step){
        double y = 0;

        switch(solver){
            case "Euler":
                y = Solvers.eulerSolve(0, Y0Exact, time, step, "-0.1*y");
                break;

            case "Runge-Kutta":
                y = Solvers.rungeKuttaSolve(0, Y0Exact, time, step, "-0.1*y");
                break;
            default:
                System.out.println("dud");
                break;
        }

        return y;
    }

    @FXML
    LineChart<Number, Number> graph = new LineChart<>(new NumberAxis(), new NumberAxis());

    public XYChart.Series<Number, Number> series = new XYChart.Series<>();
    public XYChart.Series<Number, Number> mseTrend = new XYChart.Series<>();

    @FXML
    public void initialize(){

        double step = 0.01;  // Initial step value
        double maxStep = 1.0; // Maximum step value
        int N = 100; // Number of iterations

        double sumErrorSquare = 0;

        double growthFactor = Math.pow(10, (Math.log10(maxStep) - Math.log10(step)) / N);

        double prevError = 0;
        while (step < maxStep) {

            double yDash = getYDash("Runge-Kutta", 3, step);
            double err = absErr(YExact, yDash);

            sumErrorSquare += err*err;

            series.getData().add(new XYChart.Data<>(step, err));
            step *= growthFactor; // Increase step exponentially

        }

        mseTrend.getData().add(new XYChart.Data<>(0.01, 0.0));
        mseTrend.getData().add(new XYChart.Data<>(1.0, sumErrorSquare/N));

        graph.getData().add(series);
        graph.getData().add(mseTrend);
        graph.setCreateSymbols(false);
        graph.setLegendVisible(false);
    }



}


// "Euler", "Runge-Kutta"