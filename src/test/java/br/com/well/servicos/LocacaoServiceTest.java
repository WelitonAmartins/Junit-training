package br.com.well.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.well.entidades.Filme;
import br.com.well.entidades.Locacao;
import br.com.well.entidades.Usuario;
import br.com.well.exceptions.FilmeSemEstoqueException;
import br.com.well.exceptions.LocadoraException;
import br.com.well.utils.DataUtils;

public class LocacaoServiceTest {
	
	private static int contador = 0;
	
	private LocacaoService service;
	// com a anotação Rule e a instancia do obj ErrorCollector conseguimos dividir o
	// erro, no padrao o
	// junit quando acontece o primeiro erro ele para o processo, sendo assim voce
	// trataria um erro por vez
	// já assim conseguimos rodar todo o processo e ver onde está todos os erros
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	// ultilizar para algum contexto que seja generico, algo que precisa ser
	// iniciado no começo dos teste
	@Before
	public void setup() {
		System.out.println("Before");
		service = new LocacaoService();
		
		contador ++;
		System.out.println(contador);
	}

	// ultilizar para algum contexto que seja generico, algo que precisa ser no
	// final dos teste,finalizado, ser deburrado nos final dos teste
	@After
	public void tearDown() {
		System.out.println("After");
	}
	
	//executa antes de todos os teste
	@BeforeClass
	public static void setupClass() {
		System.out.println("Before Class");
	}
	
	// execute depois de todos os testes
	@AfterClass
	public static void tearDownClass() {
		System.out.println("After Class");
	}

	@Test
	public void testeLocacao() throws Exception {
		// cenario

		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 1, 5.0);

		// acao
		Locacao locacao;

		locacao = service.alugarFilme(usuario, filme);
		// verificacao
		// verifique que o valor da alocao é 5.0
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		// verifique que o valor da alocao nao é 6.0
		// assertThat(locacao.getValor(), is(not(6.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));

		// trantando exececao no teste, como falha
		// Assert.fail("Não deveria lancar execao");

	}

	// tratando a execao de forma elegante e enxuta, funciona muito bem, se garantir
	// se a execao é apenas aquele motivo
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {

		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		service.alugarFilme(usuario, filme);

	}

	// robusta
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// cenario
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario vazio"));
		}

	}

	// nova
	@Test
	public void testLocacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {

		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		service.alugarFilme(usuario, null);

	}

}
