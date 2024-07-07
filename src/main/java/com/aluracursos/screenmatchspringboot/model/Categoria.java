package com.aluracursos.screenmatchspringboot.model;

public enum Categoria {
    ACCION("Action","Accion"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy","Comedia"),
    DRAMA("Drama","Drama"),
    CRIMEN("Crime","Crimen"),
    AVENTURA("Adventure","Aventuras");

    private String categoriaOmdb;
    private String cateriaEspanol;

    Categoria(String categoriaOmdb, String categoriaEspanol){
        this.categoriaOmdb= categoriaOmdb;
        this.cateriaEspanol=categoriaEspanol;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    public static Categoria fromEspanol(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.cateriaEspanol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
