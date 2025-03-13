package edu.com.igor.API_TabelaFIPE.principal;

import edu.com.igor.API_TabelaFIPE.model.Dados;
import edu.com.igor.API_TabelaFIPE.model.Modelos;
import edu.com.igor.API_TabelaFIPE.service.ConsumoAPI;
import edu.com.igor.API_TabelaFIPE.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";

    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();

    public void exibeMenu(){
        var menu = """
                *** OPÇÕES ***
                * Carro
                * Moto
                * Caminhão
                
                Digite uma das opções para consulta:
                """;
        System.out.println(menu);
        var opcao = scanner.nextLine();

            //Escolha da opcao de consulta
        String endereco;
        if (opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "/carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")){
            endereco = URL_BASE + "/motos/marcas";
        } else {
            endereco = URL_BASE + "/caminhoes/marcas";
        }
            //Retornando o Json do endereco de acordo com a escolha
        var json = consumoAPI.obterDados(endereco);
        System.out.println(json);

            //Exibindo a Lista de marcas da opcao escolhinda
        var marcas = converteDados.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

            //Recebendo o codigo da marca
        System.out.println("Informe o código da marca para consulta");
        var codigoMarca = scanner.nextLine();

            //Exibindo a lista dos modelos de acordo com a marca
        endereco += "/" + codigoMarca + "/modelos";
        json = consumoAPI.obterDados(endereco);
        var modeloLista = converteDados.obterDados(json, Modelos.class);
        System.out.println("\nModelos da Marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

            //Recebendo um trecho do nome do veiculo
        System.out.println("\nDigite um trecho do nome do carro:");
        var nomeVeiculo = scanner.nextLine();

            //Criando a lista do modelos filtrados
        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                        .collect(Collectors.toList());

            //Exibindo os modelos do veiculo filrados
        System.out.println("\n Modelos filtrados:");
        modelosFiltrados.stream()
                .forEach(System.out::println);

            //Recebendo codigo do modelo
        System.out.println("Digite por favor o código do modelo para buscas os valores de avaliação: ");
        var codigoModelo = scanner.nextLine();
        endereco += "/" + codigoModelo + "/anos";
        json = consumoAPI.obterDados(endereco);
    }
}
