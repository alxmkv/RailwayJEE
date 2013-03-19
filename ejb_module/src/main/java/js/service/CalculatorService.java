package js.service;

import javax.ejb.Stateless;

@Stateless
public class CalculatorService {

	public double add(double x, double y) {
		return x + y;
	}
}