package br.com.well.servicos;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import br.com.well.entidades.Usuario;


public class AssertTest {
	
	@Test
	public void test() {
		// checar se o valor � verdadeiro
		Assert.assertTrue(true);
		// checar se o valor �
		Assert.assertFalse(false);
		
		// checa se o primeiro valor � igual com o segundo
		Assert.assertEquals(1, 1);
		
		//verifica se os obj s�o diferentes
		Assert.assertNotEquals(1, 2);
		
		//ao tentar checar valores boleandos, sempre ser� necesserio informar um delta de comparacao
		Assert.assertEquals(0.51, 0.51, 0.01); 
		
		int i = 5;
		Integer i2 = 5;
		// por  mais que os valores acima s�o tipos inteiro, o java nao deixa por um ser tipo primitivo e o outro um obj
		// convertendo tipo primitivo para objeto na comparacao
		Assert.assertEquals(Integer.valueOf(i), i2);
		// convertendo o obj para tivo primitivo 
		Assert.assertEquals(i, i2.intValue());
		
		
		Assert.assertEquals("bola", "bola");	
		
		// testando case sensitive 
		Assert.assertTrue("bola".equalsIgnoreCase( "Bola"));
		// testando o radical
		Assert.assertTrue("bola".startsWith("bo"));
		
		
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = null;
		
		Assert.assertEquals(u1, u2);
		
		//conferindo se � da mesma instancia 
		Assert.assertSame(u1, u1);
		
		//conferindo se eles s�o de instancia diferentes
		Assert.assertNotSame(u1, u2);
		
		//verificando se o obj � nulo 
		Assert.assertTrue(u3 == null);
		Assert.assertNull(u3);
		
		//verificar se os obj nao est� vazio
		Assert.assertNotNull(u2);
		
		//AssertThat -> verifique que 
		
	}

}
