package br.com.well.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.junit.Assert;
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
	// com a anota��o Rule e a instancia do obj ErrorCollector conseguimos dividir o
	// erro, no padrao o
	// junit quando acontece o primeiro erro ele para o processo, sendo assim voce
	// trataria um erro por vez
	// j� assim conseguimos rodar todo o processo e ver onde est� todos os erros
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testeLocacao() throws Exception {
		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 1, 5.0);

		// acao
		Locacao locacao;

		locacao = service.alugarFilme(usuario, filme);
		// verificacao
		// verifique que o valor da alocao � 5.0
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		// verifique que o valor da alocao nao � 6.0
		// assertThat(locacao.getValor(), is(not(6.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));

		// trantando exececao no teste, como falha
		// Assert.fail("N�o deveria lancar execao");

	}

	// tratando a execao de forma elegante e enxuta
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {

		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		service.alugarFilme(usuario, filme);

	}
	
	//robusta
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		//cenario
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario vazio"));
		}
		
	}
	
	//nova
	@Test
	public void testLocacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		
		service.alugarFilme(usuario, null);

	}
	

}