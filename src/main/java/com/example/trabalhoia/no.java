package com.example.trabalhoia;

import javafx.scene.control.Button;

import java.util.List;

public class no {
    int[][] mat;
    List<Integer> listaI;

    int x,y;

    no filhos;

    public no(int[][] mat, List<Integer> listaI, no filhos,int x,int y) {
        this.mat = new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                this.mat[i][j] = mat[i][j];
            }
        }
        this.listaI = listaI;
        this.filhos = filhos;
        this.x = x;
        this.y = y;
    }

    public no() {
    }

    public int[][] getMat() {
        return mat;
    }

    public void setMat(int[][] mat) {
        this.mat = mat;
    }

    public List<Integer> getListaI() {
        return listaI;
    }

    public void setListaI(List<Integer> listaI) {
        this.listaI = listaI;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public no getFilhos() {
        return filhos;
    }

    public void setFilhos(no filhos) {
        this.filhos = filhos;
    }
}
