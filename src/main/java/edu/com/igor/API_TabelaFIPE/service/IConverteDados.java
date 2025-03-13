package edu.com.igor.API_TabelaFIPE.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
