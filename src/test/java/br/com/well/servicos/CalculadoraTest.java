package br.com.well.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.well.exceptions.NaoPodeDividirPorZeroException;





public class CalculadoraTest {
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int resutado = calc.somar(a, b);
		
		//verificacao
		Assert.assertEquals(8, resutado);
		
	}
	
	@Test
	public void DeveSubtrairDoisValores() {
	
	int a = 8;
	int b= 5;
	//acao
	int resultado = calc.subtrair(a, b);
	
	Assert.assertEquals(3, resultado);
	
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		int a = 6;
		int b= 3;
		int resultado = calc.divide(a, b);
		
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoDividirPorZero() throws NaoPodeDividirPorZeroException {
		
		int a = 10;
		int b= 0;
  		
		calc.divide(a, b);
	
	}
}
