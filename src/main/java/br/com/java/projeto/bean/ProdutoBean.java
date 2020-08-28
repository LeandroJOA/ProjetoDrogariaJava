package br.com.java.projeto.bean;

import br.com.java.projeto.dao.FabricanteDAO;
import br.com.java.projeto.dao.ProdutoDAO;
import br.com.java.projeto.domain.Fabricante;
import br.com.java.projeto.domain.Produto;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
//implements - Para evitar advertencias de serialização
public class ProdutoBean implements Serializable {

    //Objeto
    private Produto produto;
    //Lista com os dados para a tabela
    private List<Produto> produtos;
    //Lista com os dados para o select
    private List<Fabricante> fabricantes;

    //Gets e Sets
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Fabricante> getFabricantes() {
        return fabricantes;
    }

    public void setFabricantes(List<Fabricante> fabricantes) {
        this.fabricantes = fabricantes;
    }

    //Metodo para listar todos os dados da tabela ao carregar a tela
    //PostConstruct - Realizar a listagem dos dados logo após o metodo construtor desta classe
    //ser chamado
    @PostConstruct
    public void listar() {
        try {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtos = produtoDAO.listar();
        } catch (RuntimeException erro) {
            //Mensagem de erro
            Messages.addGlobalError("ERROR ao listar!");
            //Imprimir erro no log do console
            erro.printStackTrace();
        }
    }

    //Metodo para o botão "Novo"
    public void novo() {
        try {
            //instanciação do objeto por um metodo
            produto = new Produto();

            //Populando menu de seleção
            FabricanteDAO fabricanteDAO = new FabricanteDAO();
            fabricantes = fabricanteDAO.listar();
        } catch (RuntimeException erro) {
            //Mensagem de erro
            Messages.addGlobalError("ERROR ao listar seleção!");
            //Imprimir erro no log do console
            erro.printStackTrace();
        }
    }

    //Metodo para o botão "Salvar"
    public void salvar() {
        try {
            //Chamada do metodo merge
            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.merge(produto);

            //Limpeza de campos
            produto = new Produto();

            //Atualização da tabela
            produtos = produtoDAO.listar();

            //Atualização do select
            FabricanteDAO fabricanteDAO = new FabricanteDAO();
            fabricantes = fabricanteDAO.listar();

            //Mensagem de sucesso
            Messages.addGlobalInfo("Produto salvo com sucesso!");
        } catch (RuntimeException erro) {
            //Mensagem de erro
            Messages.addGlobalError("ERROR ao salvar!");
            //Imprimir erro no log do console
            erro.printStackTrace();
        }
    }

    //Metodo para o botão "Excluir"
    public void excluir(ActionEvent evento) {
        try {
            //Receber atributos da view
            produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

            //Chamada do metodo excluir
            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.excluir(produto);

            //Atualização dos registros
            produtos = produtoDAO.listar();

            //Mensagem de sucesso
            Messages.addGlobalInfo("Produto excluido com sucesso!");
        } catch (RuntimeException erro) {
            //Mensagem de erro
            Messages.addGlobalError("ERROR ao excluir!");
            //Imprimir erro no log do console
            erro.printStackTrace();
        }
    }

    //Metodo para preencher a janela de formulario com os dados da linha selecionada
    public void editar(ActionEvent evento) {
        try {
            //Receber atributos da view
            produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

            //Populando menu de seleção
            FabricanteDAO fabricanteDAO = new FabricanteDAO();
            fabricantes = fabricanteDAO.listar();
        } catch (RuntimeException erro) {
            //Mensagem de erro
            Messages.addGlobalError("ERROR ao listar seleção!");
            //Imprimir erro no log do console
            erro.printStackTrace();
        }
    }
}
