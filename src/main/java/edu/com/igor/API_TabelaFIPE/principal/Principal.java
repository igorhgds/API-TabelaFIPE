package edu.com.igor.API_TabelaFIPE.principal;

import edu.com.igor.API_TabelaFIPE.service.ConsumoAPI;
import edu.com.igor.API_TabelaFIPE.service.ConverteDados;

import java.util.Scanner;

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

        String endereco;

        if (opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "/carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")){
            endereco = URL_BASE + "/motos/marcas";
        } else {
            endereco = URL_BASE + "/caminhoes/marcas";
        }

        var json = consumoAPI.obterDados(endereco);
        System.out.println(json);
    }
}
