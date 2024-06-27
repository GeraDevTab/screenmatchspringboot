package com.aluracursos.screenmatchspringboot.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json,Class<T> clase);

}
