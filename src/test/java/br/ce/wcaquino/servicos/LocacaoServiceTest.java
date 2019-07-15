package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	// com a anotação Rule e a instancia do obj ErrorCollector conseguimos dividir o
	// erro, no padrao o
	// junit quando acontece o primeiro erro ele para o processo, sendo assim voce
	// trataria um erro por vez
	// já assim conseguimos rodar todo o processo e ver onde está todos os erros
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

	// tratando a execao de forma elegante e enxuta
	@Test(expected = Exception.class)
	public void testLocacao_filmeSemEstoque() throws Exception {

		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		service.alugarFilme(usuario, filme);

	}

	// tratando exceção de forma mais robusta, porem tem maior controle 
	@Test
	public void testLocacao_filmeSemEstoque_2() {

		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		try {
			service.alugarFilme(usuario, filme);
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}

	}
	
	@Test
	public void testLocacao_filmeSemEstoque_3() throws Exception {

		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		service.alugarFilme(usuario, filme);

	}
}
