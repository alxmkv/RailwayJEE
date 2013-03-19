package js.web.cdi;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import js.service.CalculatorService;

@RequestScoped
@Named
public class CalculatorBean {
	
    @EJB
    private CalculatorService calculatorService;
    
    private double x;
    private double y;
    private double result;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String add() {
        result = calculatorService.add(x, y);
        return "success";
    }
}